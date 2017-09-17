package org.fogbeam.presentation.realtimeml.prediction.runner;

import org.fogbeam.presentation.realtimeml.prediction.service.PredictionServiceImpl;

import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;

public class PredictionServiceMain 
{

	public static void main(String[] args) throws Exception
	{
		
		System.out.println( "Starting PredictionService RPC interface" );
		
		// Start the gRPC server for our PredictionService
	    /* The port on which the server should run */
	    int port = 50051;
	    
	    
	    Server server = NettyServerBuilder.forPort( port )
	    		.addService(new PredictionServiceImpl())
	    		.build()
	    	 	.start();
	    
	    System.out.println("PredictionService RPC server started, listening on " + port);
	
	    while( true ) 
	    {
	    		Thread.sleep( 60000 );
	    }  
	    
	}	
}