
package org.fogbeam.presentation.realtimeml.prediction.client;


import java.util.concurrent.TimeUnit;

import org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply;
import org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionRequest;
import org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionServiceGrpc;
import org.fogbeam.presentation.trijugml.model.Promotion;

import org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionRequest.Builder;

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
		PredictionServiceGrpcClient client = new PredictionServiceGrpcClient(
				"localhost", 50051 );
		client.makePredictionWithDefaultValues();
		System.out.println( "done" );
	}
	
	/* NOTE: when classifying a "candidate promotion" there is no actual "totalAmount" since there is no
	 * order.  But since we trained our model with that feature, we have to put something here, so assume
	 * the value passed here is something like "average order amount".  In real life, we might set this up so that
	 * we classify promotions based on the projected order amount (maybe based on current cart contents).  Or maybe
	 * totalAmount just isn't a good feature to use at all.  Feature engineering is complicated. */

	/* NOTE: likewise, since we included State as a feature, this basically assumes we are asking the question
	 * "How does this promotion rate, for this particular state?" 
	 */
	public boolean makePrediction(  Promotion candidatePromotion, double totalAmount, int stateId )
	{
		Builder prBuilder = PredictionRequest.newBuilder();
		
		String primaryColor = candidatePromotion.getPrimaryColor();
		
		prBuilder.setPrimaryRedValue( 0x0F );
		prBuilder.setPrimaryGreenValue( 0xF3 );
		prBuilder.setPrimaryBlueValue( 0xAA );
		
		String secondaryColor = candidatePromotion.getPrimaryColor();
		prBuilder.setSecondaryRedValue( 0x00 );
		prBuilder.setSecondaryGreenValue( 0xC3 );
		prBuilder.setSecondaryBlueValue( 0xAF );
		
		Long opcId = candidatePromotion.getOfferProductCategory().getId();
		
		prBuilder.setOfferProductCategory1( false );
		prBuilder.setOfferProductCategory2( false );
		prBuilder.setOfferProductCategory3( false );
		prBuilder.setOfferProductCategory4( false );
		prBuilder.setOfferProductCategory5( true );
		prBuilder.setOfferProductCategory6( false );
		prBuilder.setOfferProductCategory7( false );
		
		
		Long otId = candidatePromotion.getOfferType().getId();
		prBuilder.setOfferType1( true );
		prBuilder.setOfferType2( false );
		
		
		prBuilder.setTotalAmount( totalAmount );

		/* stateId */
		
		if( stateId == 1 )
		{
			prBuilder.setState1( true );

		}
		else
		{
			prBuilder.setState1( false );
		}
		
		if( stateId == 2 )
		{
			prBuilder.setState2( true );

		}
		else
		{
			prBuilder.setState2( false );
		}

		if( stateId == 4 )
		{
			prBuilder.setState3( true );

		}
		else
		{
			prBuilder.setState3( false );
		}

		
		if( stateId == 5 )
		{
			prBuilder.setState4( true );

		}
		else
		{
			prBuilder.setState4( false );
		}

		
		if( stateId == 6 )
		{
			prBuilder.setState5( true );

		}
		else
		{
			prBuilder.setState5( false );
		}

		if( stateId == 8 )
		{
			prBuilder.setState6( true );

		}
		else
		{
			prBuilder.setState6( false );
		}

		
		if( stateId == 9 )
		{
			prBuilder.setState7( true );

		}
		else
		{
			prBuilder.setState7( false );
		}

		
		if( stateId == 10 )
		{
			prBuilder.setState8( true );

		}
		else
		{
			prBuilder.setState8( false );
		}

		
		if( stateId == 11 )
		{
			prBuilder.setState9( true );

		}
		else
		{
			prBuilder.setState9( false );
		}

		
		if( stateId == 12 )
		{
			prBuilder.setState10( true );

		}
		else
		{
			prBuilder.setState10( false );
		}

		
		if( stateId == 13 )
		{
			prBuilder.setState11( true );

		}
		else
		{
			prBuilder.setState11( false );
		}

		
		if( stateId == 15 )
		{
			prBuilder.setState12( true );

		}
		else
		{
			prBuilder.setState12( false );
		}

		
		if( stateId == 16 )
		{
			prBuilder.setState13( true );

		}
		else
		{
			prBuilder.setState13( false );
		}

		
		if( stateId == 17 )
		{
			prBuilder.setState14( true );

		}
		else
		{
			prBuilder.setState14( false );
		}

		
		if( stateId == 18 )
		{
			prBuilder.setState15( true );

		}
		else
		{
			prBuilder.setState15( false );
		}

		
		if( stateId == 19 )
		{
			prBuilder.setState16( true );

		}
		else
		{
			prBuilder.setState16( false );
		}

		
		if( stateId == 20 )
		{
			prBuilder.setState17( true );

		}
		else
		{
			prBuilder.setState17( false );
		}

		
		if( stateId == 21 )
		{
			prBuilder.setState18( true );

		}
		else
		{
			prBuilder.setState18( false );
		}

		
		if( stateId == 22 )
		{
			prBuilder.setState19( true );

		}
		else
		{
			prBuilder.setState19( false );
		}

		
		if( stateId == 23 )
		{
			prBuilder.setState20( true );

		}
		else
		{
			prBuilder.setState20( false );
		}

		
		if( stateId == 24 )
		{
			prBuilder.setState21( true );

		}
		else
		{
			prBuilder.setState21( false );
		}

		
		if( stateId == 25 )
		{
			prBuilder.setState22( true );

		}
		else
		{
			prBuilder.setState22( false );
		}

		
		if( stateId == 26 )
		{
			prBuilder.setState23( true );

		}
		else
		{
			prBuilder.setState23( false );
		}

		
		if( stateId == 27 )
		{
			prBuilder.setState24( true );

		}
		else
		{
			prBuilder.setState24( false );
		}

		
		if( stateId == 28 )
		{
			prBuilder.setState25( true );

		}
		else
		{
			prBuilder.setState25( false );
		}

		
		if( stateId == 29 )
		{
			prBuilder.setState26( true );

		}
		else
		{
			prBuilder.setState26( false );
		}

		
		if( stateId == 30 )
		{
			prBuilder.setState27( true );

		}
		else
		{
			prBuilder.setState27( false );
		}

		
		if( stateId == 31 )
		{
			prBuilder.setState28( true );

		}
		else
		{
			prBuilder.setState28( false );
		}

		
		if( stateId == 32 )
		{
			prBuilder.setState29( true );

		}
		else
		{
			prBuilder.setState29( false );
		}

		
		if( stateId == 33 )
		{
			prBuilder.setState30( true );

		}
		else
		{
			prBuilder.setState30( false );
		}

		
		if( stateId == 34 )
		{
			prBuilder.setState31( true );

		}
		else
		{
			prBuilder.setState31( false );
		}

		
		if( stateId == 35 )
		{
			prBuilder.setState32( true );

		}
		else
		{
			prBuilder.setState32( false );
		}

		
		if( stateId == 36 )
		{
			prBuilder.setState33( true );

		}
		else
		{
			prBuilder.setState33( false );
		}

		
		if( stateId == 37 )
		{
			prBuilder.setState34( true );

		}
		else
		{
			prBuilder.setState34( false );
		}

		
		if( stateId == 38 )
		{
			prBuilder.setState35( true );

		}
		else
		{
			prBuilder.setState35( false );
		}

		
		if( stateId == 39 )
		{
			prBuilder.setState36( true );

		}
		else
		{
			prBuilder.setState36( false );
		}

		
		if( stateId == 40 )
		{
			prBuilder.setState37( true );

		}
		else
		{
			prBuilder.setState37( false );
		}

		if( stateId == 41 )
		{
			prBuilder.setState38( true );

		}
		else
		{
			prBuilder.setState38( false );
		}

		if( stateId == 42 )
		{
			prBuilder.setState39( true );

		}
		else
		{
			prBuilder.setState39( false );
		}

		if( stateId == 44 )
		{
			prBuilder.setState40( true );

		}
		else
		{
			prBuilder.setState40( false );
		}

		if( stateId == 45 )
		{
			prBuilder.setState41( true );

		}
		else
		{
			prBuilder.setState41( false );
		}

		if( stateId == 46 )
		{
			prBuilder.setState42( true );

		}
		else
		{
			prBuilder.setState42( false );
		}

		if( stateId == 47 )
		{
			prBuilder.setState43( true );

		}
		else
		{
			prBuilder.setState43( false );
		}

		if( stateId == 48 )
		{
			prBuilder.setState44( true );

		}
		else
		{
			prBuilder.setState44( false );
		}

		if( stateId == 49 )
		{
			prBuilder.setState45( true );

		}
		else
		{
			prBuilder.setState45( false );
		}

		if( stateId == 50 )
		{
			prBuilder.setState46( true );

		}
		else
		{
			prBuilder.setState46( false );
		}

		if( stateId == 51 )
		{
			prBuilder.setState47( true );

		}
		else
		{
			prBuilder.setState47( false );
		}

		if( stateId == 53 )
		{
			prBuilder.setState48( true );

		}
		else
		{
			prBuilder.setState48( false );
		}

		if( stateId == 54 )
		{
			prBuilder.setState49( true );

		}
		else
		{
			prBuilder.setState49( false );
		}

		if( stateId == 55 )
		{
			prBuilder.setState50( true );

		}
		else
		{
			prBuilder.setState50( false );
		}

		if( stateId == 56 )
		{
			prBuilder.setState51( true );

		}
		else
		{
			prBuilder.setState51( false );
		}

					
		PredictionRequest request = prBuilder.build();
		
		PredictionReply response;
		try
		{
			response = blockingStub.makePrediction( request );
			boolean predictedValue = response.getPredictedValue();
			
			System.out.println( "predictedValue from remote server: " + predictedValue );
			return predictedValue;
		}
		catch( StatusRuntimeException e )
		{
			throw new RuntimeException( e );
		}
	}

	
	private boolean makePredictionWithDefaultValues()
	{
		PredictionRequest request = PredictionRequest.newBuilder()
				.setPrimaryRedValue( 0x0F ).setPrimaryGreenValue( 0xF3 )
				.setPrimaryBlueValue( 0xAA ).setSecondaryRedValue( 0x00 )
				.setSecondaryGreenValue( 0xC3 ).setSecondaryBlueValue( 0xAF )
				.setOfferProductCategory1( false )
				.setOfferProductCategory2( false )
				.setOfferProductCategory3( false )
				.setOfferProductCategory4( false )
				.setOfferProductCategory5( true )
				.setOfferProductCategory6( false )
				.setOfferProductCategory7( false )
				.setOfferType1( true )
				.setOfferType2( false )
				.setTotalAmount( 5000 )
				.setState1( false )
				.setState2( false )
				.setState3( false )
				.setState4( false )
				.setState5( false )
				.setState6( false )
				.setState7( false )
				.setState8( false )
				.setState9( false )
				.setState10( false )
				.setState11( false )
				.setState12( false )
				.setState13( false )
				.setState14( false )
				.setState15( false )
				.setState16( false )
				.setState17( false )
				.setState18( false )
				.setState19( false )
				.setState20( false )
				.setState21( false )
				.setState22( false )
				.setState23( false )
				.setState24( false )
				.setState25( false )
				.setState26( false )
				.setState27( false )
				.setState28( false )
				.setState29( false )
				.setState30( false )
				.setState31( false )
				.setState32( false )
				.setState33( false )
				.setState34( false )
				.setState35( false )
				.setState36( false )
				.setState37( true )
				.setState38( false )
				.setState39( false )
				.setState40( false )
				.setState41( false )
				.setState42( false )
				.setState43( false )
				.setState44( false )
				.setState45( false )
				.setState46( false )
				.setState47( false )
				.setState48( false )
				.setState49( false )
				.setState50( false )
				.setState51( false )
				.build();
		
		PredictionReply response;
		try
		{
			response = blockingStub.makePrediction( request );
			boolean predictedValue = response.getPredictedValue();
			System.out.println(
					"predictedValue from remote server: " + predictedValue );
			return predictedValue;
		}
		catch( StatusRuntimeException e )
		{
			throw new RuntimeException( e );
		}
	}
}
