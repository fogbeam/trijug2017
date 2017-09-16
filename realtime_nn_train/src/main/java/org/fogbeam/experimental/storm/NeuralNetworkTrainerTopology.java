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
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import javax.jms.Session;

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
import org.datavec.api.writable.Writable;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
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
			LOG.debug( "Tuples in current window: " + tuplesInWindow.size() );
			
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
						throw new RuntimeException( ioe );
					}
				}

				
				// if we made it here, we successfully acquired our lock...
				
				System.out.println(  "GetFileLock2 - Acquired lock..." );
			
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
				
				// serialize this...
				ModelSerializer.writeModel( model, locationToSave, true );
				
			}
			catch( Exception e ) 
			{
				throw new RuntimeException( e );
			}
			finally
			{
				if( lock != null ) 
				{
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

			List<Writable> aRecord = new ArrayList<Writable>();
			
			// TODO: fill in the fields from the tuple...
			
			return aRecord;
		}

		@Override
		public void declareOutputFields( OutputFieldsDeclarer declarer )
		{
		}
	}

	
	public static void main( String[] args ) throws Exception
	{
		TopologyBuilder builder = new TopologyBuilder();
		
		
		// JMS Queue Provider
		JmsProvider jmsQueueProvider = new SpringJmsProvider(
				"jms-activemq.xml", "jmsConnectionFactory",
				"notificationQueue" );
		
		
		// JMS Producer
        JmsTupleProducer producer = new JsonTupleProducer();
		
		
		// JMS Queue Spout
		JmsSpout queueSpout = new JmsSpout();
		queueSpout.setJmsProvider( jmsQueueProvider );
		queueSpout.setJmsTupleProducer(producer);
		queueSpout.setJmsAcknowledgeMode( Session.CLIENT_ACKNOWLEDGE );
		queueSpout.setDistributed( true ); // allow multiple instances
		
		
		// spout with 5 parallel instances
		builder.setSpout( JMS_QUEUE_SPOUT, queueSpout, 5 );		
					
		builder.setBolt( "tumblingWindow", new TumblingWindowAvgBolt().withTumblingWindow( Count.of( 10 ) ), 1 ).shuffleGrouping( JMS_QUEUE_SPOUT );
				
		
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
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology( "test", conf, builder.createTopology() );
			while( true ) 
			{
				Utils.sleep( 40000 );
			}
		}
	}
}