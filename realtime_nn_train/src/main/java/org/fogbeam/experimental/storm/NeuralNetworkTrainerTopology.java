/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.fogbeam.experimental.storm;


import java.io.File;
import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileLock;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.jms.Session;

import org.apache.log4j.BasicConfigurator;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.jms.JmsProvider;
import org.apache.storm.jms.JmsTupleProducer;
import org.apache.storm.jms.spout.JmsSpout;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseWindowedBolt;
import org.apache.storm.topology.base.BaseWindowedBolt.Count;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.utils.Utils;
import org.apache.storm.windowing.TupleWindow;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.collection.CollectionRecordReader;
import org.datavec.api.writable.BooleanWritable;
import org.datavec.api.writable.FloatWritable;
import org.datavec.api.writable.IntWritable;
import org.datavec.api.writable.Writable;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.fogbeam.presentation.realtimeml.nntrain.NNModelTrainer;
import org.fogbeam.presentation.trijugml.model.OfferProductCategory;
import org.fogbeam.presentation.trijugml.model.OfferType;
import org.fogbeam.presentation.trijugml.model.Order;
import org.fogbeam.presentation.trijugml.model.Promotion;
import org.fogbeam.presentation.trijugml.model.State;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A sample topology that demonstrates the usage of
 * {@link org.apache.storm.topology.IWindowedBolt} to calculate sliding window
 * sum.
 */
public class NeuralNetworkTrainerTopology
{
	
	private static final Logger LOG = LoggerFactory.getLogger( NeuralNetworkTrainerTopology.class );

	public static final String JMS_QUEUE_SPOUT = "JMS_QUEUE_SPOUT";
	
	private static class TumblingWindowAvgBolt extends BaseWindowedBolt
	{
		
		static int count = 0;
		OutputCollector collector;
		
		@Override
		public void prepare( Map stormConf, TopologyContext context, OutputCollector collector )
		{
			this.collector = collector;
		}

		@Override
		public void execute( TupleWindow inputWindow )
		{
			List<Tuple> tuplesInWindow = inputWindow.get();
			System.out.println( "Tuples in current window: " + tuplesInWindow.size() );
			
			FileLock lock = null;
			
			
			int batchSize = 50;
			int numEpochs = 5;
			File locationToSave = new File("/opt/ml/models/PromotionsModel.zip");  // Where to save the network. Note: the file is in .zip format - can be opened externally

			try
			{				
				/* get the file lock that protects the model from simultaneous access */
				Path lockFilePath = FileSystems.getDefault().getPath("/opt/ml/models/", "modelFile.lock");
				AsynchronousFileChannel lockFileChannel = AsynchronousFileChannel.open( lockFilePath, StandardOpenOption.CREATE, StandardOpenOption.WRITE );	
				
				Long start = System.currentTimeMillis();
				Long maxWaitMillis = 60000L;
				
				while( true )
				{
					try
					{
						lock = lockFileChannel.tryLock();

						if( lock == null ) 
						{
							Long now = System.currentTimeMillis();
							if( now > (start + maxWaitMillis ))
							{
								throw new TimeoutException( "Timed out trying to acquire lock file" );
							}
							else
							{
								Thread.sleep( 500 );
								continue;
							}
							
						}
						else 
						{
							break;
						}
					
					}
					catch( IOException ioe )
					{
						ioe.printStackTrace();
						throw new RuntimeException( ioe );
					}
				}

				
				// if we made it here, we successfully acquired our lock...
				
				System.out.println(  "NeuralNetworkTrainerTopology - Acquired lock..." );
			
				/* load our saved NN model */
							
				MultiLayerNetwork model = ModelSerializer.restoreMultiLayerNetwork(locationToSave);
			
				List<List<Writable>> trainingData = new ArrayList<List<Writable>>();
 
				for( Tuple tuple : tuplesInWindow )
				{
					// System.out.println( tuple.getValue( 0 ) );
					List<Writable> aRecord = makeRecord( tuple );
					trainingData.add( aRecord );
					collector.ack(tuple);				
				
				}

				
				RecordReader trainReader = new CollectionRecordReader(trainingData);
				DataSetIterator trainIterator = new RecordReaderDataSetIterator(trainReader, batchSize,0,2);
				
				System.out.println(  "Training network!" );
				for( int n = 0; n < numEpochs; n++ )
				{
					model.fit(trainIterator);
					
				}	
				
				// reserialize the model to disk again
				// in real life, we might want to archive the old model file before doing this
				// in case we need to roll back for some reason
				locationToSave.delete();
				
				System.out.println(  "Reserializing updated model to disk" );
				// serialize this...
				ModelSerializer.writeModel( model, locationToSave, true );
				
			}
			catch( Exception e ) 
			{
				e.printStackTrace();
				throw new RuntimeException( e );
			}
			finally
			{
				if( lock != null ) 
				{
					System.out.println(  "Releasing lock..." );
					try
					{
						lock.release();
					}
					catch( IOException e )
					{
						e.printStackTrace();
					}
				}
			}
			
			
			System.out.println( "--------------------------------------");
					
		}

		private List<Writable> makeRecord( Tuple tuple )
		{
			
			// fill in the fields from the tuple...
			Order order = (Order)tuple.getValue( 0 );
			
			List<Writable> aRecord = new ArrayList<Writable>();
			
			BooleanWritable fieldZero = new BooleanWritable(order.getPromotionAdded());
			aRecord.add( fieldZero );
			
			Promotion promotion = order.getPromotion();
			String primaryColor = promotion.getPrimaryColor();
			
			
			IntWritable fieldOne = 		new IntWritable( Integer.parseInt( primaryColor.substring( 0, 2 ), 16 ) );
			aRecord.add(  fieldOne );
			
			IntWritable fieldTwo = 		new IntWritable( Integer.parseInt( primaryColor.substring( 2, 4 ), 16 ) );
			aRecord.add(  fieldTwo );
			
			IntWritable fieldThree = 	new IntWritable( Integer.parseInt( primaryColor.substring( 4, 6 ), 16 ) );
			aRecord.add(  fieldThree );

			
			String secondaryColor = promotion.getSecondaryColor();
			
			IntWritable fieldFour = 		new IntWritable( Integer.parseInt( secondaryColor.substring( 0, 2 ), 16 ) );
			aRecord.add(  fieldFour );
			
			IntWritable fieldFive = 		new IntWritable( Integer.parseInt( secondaryColor.substring( 2, 4 ), 16 ) );
			aRecord.add(  fieldFive );
			
			IntWritable fieldSix = 			new IntWritable( Integer.parseInt( secondaryColor.substring( 4, 6 ), 16 ) );
			aRecord.add(  fieldSix );
			
			
			OfferProductCategory productCategory = promotion.getOfferProductCategory();
			
			
			/* 
			 	"one-hot" encoding where we turn the list of categories into a sequence of binary fields
			 	where for each possible category we send either TRUE or FALSE depending on which value the
			 	categorical feature actually holds.  Only one will be TRUE (which is why it's called "one hot")
			 */
			
			
			/* 
			 	In real-life, don't use database keys for this, as you probably can't guarantee that they won't ever change.
			 	Also, if you add a new category, you will have to change the network topology and retrain the model
			 */
			List<Long> opcIds = Arrays.asList( 1L, 2L, 3L, 4L, 5L, 6L, 7L );
			
			for( Long opcId : opcIds ) 
			{
				if( opcId.equals( productCategory.getId() ) )
				{
					BooleanWritable opcField = new BooleanWritable( true );
					aRecord.add( opcField );
				}
				else 
				{
					BooleanWritable opcField = new BooleanWritable( false );
					aRecord.add(  opcField );
				}
			}
			
			
			/* do the same kind of thing on OfferType which is also a categorical feature */
			
			OfferType offerType = promotion.getOfferType();
			List<Long> otIds = Arrays.asList( 1L, 2L );
			
			for( Long otId : otIds ) 
			{
				if( otId.equals( offerType.getId() ))
				{
					BooleanWritable otField = new BooleanWritable( true );
					aRecord.add(  otField );
				}
				else
				{
					BooleanWritable otField = new BooleanWritable( false );
					aRecord.add(  otField );
					
				}
			}
			
			
			Long totalAmount = order.getTotalAmount();
			FloatWritable totalAmountField = new FloatWritable( totalAmount / 100 );
			aRecord.add( totalAmountField );
			
			State userState = order.getUser().getState();
			
			List<Long> stateIds = Arrays.asList( 
					1L, 2L, 4L, 5L, 6L, 8L, 9L, 10L, 11L, 12L, 13L,
					15L, 16L, 17L, 18L, 19L, 20L,	21L, 22L, 23L,
					24L, 25L, 26L, 27L, 28L, 29L, 30L, 31L, 32L,
					33L, 34L, 35L, 36L, 37L, 38L, 39L, 40L, 41L,
					42L, 44L, 45L, 46L, 47L, 48L, 49L, 50L, 51L,
					53L, 54L, 55L, 56L );
			
			for( Long stateId: stateIds )
			{
				if( stateId.equals( userState.getId() ))
				{
					BooleanWritable stateField = new BooleanWritable( true );
					aRecord.add(  stateField );				
				}
				else
				{
					BooleanWritable stateField = new BooleanWritable( false );
					aRecord.add(  stateField );							
				}
			}
			
			return aRecord;
			
		}

		@Override
		public void declareOutputFields( OutputFieldsDeclarer declarer )
		{
		}
	}

	
	public static void main( String[] args ) throws Exception
	{
		
		// BasicConfigurator.configure();
		
		TopologyBuilder builder = new TopologyBuilder();
		
		
		// JMS Queue Provider
		JmsProvider jmsQueueProvider = new SpringJmsProvider(
				"jms-activemq.xml", "jmsConnectionFactory",
				"notificationQueue" );
		
		
		// JMS Producer
        // JmsTupleProducer producer = new JsonTupleProducer();
		ObjectTupleProducer producer = new ObjectTupleProducer();
		
		// JMS Queue Spout
		JmsSpout queueSpout = new JmsSpout();
		queueSpout.setJmsProvider( jmsQueueProvider );
		queueSpout.setJmsTupleProducer(producer);
		queueSpout.setJmsAcknowledgeMode( Session.CLIENT_ACKNOWLEDGE );
		queueSpout.setDistributed( true ); // allow multiple instances
		
		
		// spout with 5 parallel instances
		builder.setSpout( JMS_QUEUE_SPOUT, queueSpout, 5 );		
					
		builder.setBolt( "tumblingWindow", new TumblingWindowAvgBolt().withTumblingWindow( Count.of( 3 ) ), 1 ).shuffleGrouping( JMS_QUEUE_SPOUT );
				
		
		Config conf = new Config();
		conf.setDebug( true );
		if( args != null && args.length > 0 )
		{
			
			System.out.println( "Submitting Topology with StormSubmitter");
			
			conf.setNumWorkers( 1 );
			StormSubmitter.submitTopologyWithProgressBar( args[0], conf,
					builder.createTopology() );
		}
		else
		{
			System.out.println(  "Submitting Topology to LocalCluster" );
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology( "test", conf, builder.createTopology() );
			while( true ) 
			{
				Utils.sleep( 40000 );
			}
		}
	}
}