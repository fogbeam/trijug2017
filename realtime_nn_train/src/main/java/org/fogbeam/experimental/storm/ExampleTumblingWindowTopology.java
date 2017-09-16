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


import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.jms.Session;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.jms.JmsProvider;
import org.apache.storm.jms.JmsTupleProducer;
import org.apache.storm.jms.spout.JmsSpout;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.IBasicBolt;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseWindowedBolt;
import org.apache.storm.topology.base.BaseWindowedBolt.Count;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.utils.Utils;
import org.apache.storm.windowing.TupleWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A sample topology that demonstrates the usage of
 * {@link org.apache.storm.topology.IWindowedBolt} to calculate sliding window
 * sum.
 */
public class ExampleTumblingWindowTopology
{
	
	private static final Logger LOG = LoggerFactory.getLogger( ExampleTumblingWindowTopology.class );

	public static final String JMS_QUEUE_SPOUT = "JMS_QUEUE_SPOUT";
	
	private static class TumblingWindowAvgBolt extends BaseWindowedBolt
	{
		
		static int count = 0;
		PrintWriter outWriter;
		OutputCollector collector;
		
		@Override
		public void prepare( Map stormConf, TopologyContext context, OutputCollector collector )
		{
		
			this.collector = collector;
			
			try
			{
				outWriter  = new PrintWriter( new BufferedOutputStream( new FileOutputStream( "/home/centos/storm_output.txt" )));
				
			}
			catch( Exception e ) 
			{
				throw new RuntimeException( e );
			}
		}

		@Override
		public void execute( TupleWindow inputWindow )
		{
			
			List<Tuple> tuplesInWindow = inputWindow.get();
			LOG.debug( "Events in current window: " + tuplesInWindow.size() );
			
			count++;
			
			System.out.println( "--------------------------------------");
			System.out.println(  "count: " + Integer.toString(count) );
			
			try
			{
				outWriter.write( "--------------------------------------\n");
				outWriter.write( "count: " + Integer.toString(count) + "\n");
			}
			catch( Exception e )
			{
				throw new RuntimeException( e );
			}
			
			
			if( tuplesInWindow.size() > 0 )
			{
				for( Tuple tuple : tuplesInWindow )
				{
					collector.ack(tuple);
					System.out.println( ((TextHolder)tuple.getValue( 0 )).getText() );
					outWriter.write( ((TextHolder)tuple.getValue( 0 )).getText() + "\n");
				}
			}
			
			
			System.out.println( "--------------------------------------");
			outWriter.write( "--------------------------------------\n\n");
			outWriter.flush();
			
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
        // JmsTupleProducer producer = new JsonTupleProducer();
		JmsTupleProducer producer = new ObjectTupleProducer();
		
		
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
