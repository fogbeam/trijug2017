
package org.fogbeam.presentation.realtimeml.prediction.client;


import java.util.concurrent.TimeUnit;

import org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply;
import org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionRequest;
import org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionServiceGrpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;


public class PredictionServiceGrpcClient
{
	
	private final ManagedChannel channel;
	private final PredictionServiceGrpc.PredictionServiceBlockingStub blockingStub;

	
	public PredictionServiceGrpcClient( String host, int port )
	{
		this( ManagedChannelBuilder.forAddress( host, port )
				.usePlaintext( true ) );
	}

	/**
	 * Construct client for accessing PredictionServiceserver
	 */
	PredictionServiceGrpcClient( ManagedChannelBuilder<?> channelBuilder )
	{
		channel = channelBuilder.build();
		blockingStub = PredictionServiceGrpc.newBlockingStub( channel );
	}

	public void shutdown() throws InterruptedException
	{
		channel.shutdown().awaitTermination( 5, TimeUnit.SECONDS );
	}

		
	/**
	 * @param args
	 */
	public static void main( String[] args )
	{
		PredictionServiceGrpcClient client = new PredictionServiceGrpcClient("localhost", 50051);
		
		client.makePrediction();
		
		System.out.println( "done" );
	}
	
	public void makePrediction()
	{
		
		PredictionRequest request = PredictionRequest.newBuilder()
										.setPrimaryRedValue(0x0F)
										.setPrimaryGreenValue(0xF3)
										.setPrimaryBlueValue(0xAA)
										.setSecondaryRedValue(0x00)
										.setSecondaryGreenValue(0xC3)
										.setSecondaryBlueValue(0xAF)
										.setOfferProductCategory1(false)
										.setOfferProductCategory2(false)
										.setOfferProductCategory3(false)
										.setOfferProductCategory4(false)
										.setOfferProductCategory5(true)
										.setOfferProductCategory6(false)
										.setOfferProductCategory7(false)
										.setOfferType1(true)
										.setOfferType2(false)
										.setTotalAmount(5000)
										.setState1(false)
										.setState2(false)
										.setState3(false)
										.setState4(false)
										.setState5(false)
										.setState6(false)
										.setState7(false)
										.setState8(false)
										.setState9(false)
										.setState10(false)
										.setState11(false)
										.setState12(false)
										.setState13(false)
										.setState14(false)
										.setState15(false)
										.setState16(false)
										.setState17(false)
										.setState18(false)
										.setState19(false)
										.setState20(false)
										.setState21(false)
										.setState22(false)
										.setState23(false)
										.setState24(false)
										.setState25(false)
										.setState26(false)
										.setState27(false)
										.setState28(false)
										.setState29(false)
										.setState30(false)
										.setState31(false)
										.setState32(false)
										.setState33(false)
										.setState34(false)
										.setState35(false)
										.setState36(false)
										.setState37(true)
										.setState38(false)
										.setState39(false)
										.setState40(false)
										.setState41(false)
										.setState42(false)
										.setState43(false)
										.setState44(false)
										.setState45(false)
										.setState46(false)
										.setState47(false)
										.setState48(false)
										.setState49(false)
										.setState50(false)
										.setState51(false)
										.build();
	    PredictionReply response;

	    try 
	    {
	      response = blockingStub.makePrediction(request);
	      
	      System.out.println( "predictedValue from remote server: " + response.getPredictedValue());
	      
	    } 
	    catch (StatusRuntimeException e) 
	    {
	    	e.printStackTrace();
	    	// logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
	      return;
	    }
	}
	
	
	
}
