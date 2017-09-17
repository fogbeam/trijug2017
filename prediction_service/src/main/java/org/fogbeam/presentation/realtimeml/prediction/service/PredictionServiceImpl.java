package org.fogbeam.presentation.realtimeml.prediction.service;

import java.io.File;
import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileLock;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.collection.CollectionRecordReader;
import org.datavec.api.writable.BooleanWritable;
import org.datavec.api.writable.FloatWritable;
import org.datavec.api.writable.IntWritable;
import org.datavec.api.writable.Writable;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionReply;
import org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionRequest;
import org.fogbeam.presentation.realtimeml.prediction.rpc.PredictionServiceGrpc;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

import io.grpc.stub.StreamObserver;

public class PredictionServiceImpl extends PredictionServiceGrpc.PredictionServiceImplBase 
{

	@Override
	public void makePrediction(PredictionRequest request, StreamObserver<PredictionReply> responseObserver) 
	{
		System.out.println("makePrediction called with request: " + ToStringBuilder.reflectionToString(request));
		FileLock lock = null;
		
		
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
			
			System.out.println(  "PredictionServiceImpl - Acquired lock..." );
		
			/* load our saved NN model */
			MultiLayerNetwork model = ModelSerializer.restoreMultiLayerNetwork(locationToSave);
		
			List<List<Writable>> predictionData = new ArrayList<List<Writable>>();
			
			List<Writable> aRecord = makeRecord( request ); // make this record from out input
			predictionData.add( aRecord );
			
			int batchSize = 1;
			RecordReader predictionReader = new CollectionRecordReader(predictionData);
			DataSetIterator predictionIter = new RecordReaderDataSetIterator(predictionReader,batchSize,0,2);
			
			DataSet ds = predictionIter.next();
			System.out.println( "labels: " + ds.getLabels() );
			
			INDArray input = ds.getFeatures();
			
			System.out.println( "features: " + ds.getFeatures() );
			
			INDArray output = model.output(input);
			
			System.out.println( "output: " + output );
			
			boolean prediction = (  ( output.getDouble(0) < output.getDouble(1) ) ? true : false );
			
			PredictionReply reply = PredictionReply.newBuilder().setPredictedValue(prediction).build();
			
			responseObserver.onNext(reply);
			responseObserver.onCompleted();		
					
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
		
	}

	private List<Writable> makeRecord(PredictionRequest request) {
		
		List<Writable> aRecord = new ArrayList<Writable>();
		
		// just a placeholder since this is the field label we're trying to predict
		BooleanWritable fieldZero = new BooleanWritable(false);
		aRecord.add( fieldZero );
		
		// primary red
		IntWritable fieldOne = 		new IntWritable( request.getPrimaryRedValue() );
		aRecord.add(fieldOne);
		
		// primary green
		IntWritable fieldTwo = 		new IntWritable( request.getPrimaryGreenValue() );
		aRecord.add( fieldTwo );
		
		// primary blue
		IntWritable fieldThree = 	new IntWritable( request.getPrimaryBlueValue() );
		aRecord.add( fieldThree );
		
		// secondary red
		IntWritable fieldFour =  new IntWritable( request.getSecondaryRedValue() );
		aRecord.add(fieldFour);
		
		// secondary green
		IntWritable fieldFive = 		new IntWritable( request.getSecondaryGreenValue() );
		aRecord.add( fieldFive );
		
		// secondary blue
		IntWritable fieldSix = 		new IntWritable( request.getSecondaryBlueValue() );
		aRecord.add( fieldSix );
		
		// OfferProductCategory
		BooleanWritable opcOne = new BooleanWritable( request.getOfferProductCategory1());
		aRecord.add( opcOne );
		
		BooleanWritable opcTwo = new BooleanWritable( request.getOfferProductCategory2());
		aRecord.add( opcTwo );
		
		BooleanWritable opcThree = new BooleanWritable( request.getOfferProductCategory3());
		aRecord.add( opcThree );
		
		BooleanWritable opcFour = new BooleanWritable( request.getOfferProductCategory4());
		aRecord.add( opcFour );
		
		BooleanWritable opcFive = new BooleanWritable( request.getOfferProductCategory5());
		aRecord.add( opcFive );
		
		BooleanWritable opcSix = new BooleanWritable( request.getOfferProductCategory6());
		aRecord.add( opcSix );
		
		BooleanWritable opcSeven = new BooleanWritable( request.getOfferProductCategory7());
		aRecord.add( opcSeven );
		
		
		// OfferType
		BooleanWritable otOne = new BooleanWritable( request.getOfferType1());
		aRecord.add(otOne);
		
		BooleanWritable otTwo = new BooleanWritable( request.getOfferType2());
		aRecord.add(otTwo);
		
		// totalAmount
		FloatWritable totalAmountField = new FloatWritable( (float)request.getTotalAmount() );
		aRecord.add( totalAmountField );

		// state
		BooleanWritable state1 = new BooleanWritable( request.getState1() );
		aRecord.add( state1 );

		BooleanWritable state2 = new BooleanWritable( request.getState2() );
		aRecord.add( state2 );
	
		BooleanWritable state3 = new BooleanWritable( request.getState3() );
		aRecord.add( state3 );
		
		BooleanWritable state4 = new BooleanWritable( request.getState4() );
		aRecord.add( state4 );

		BooleanWritable state5 = new BooleanWritable( request.getState5() );
		aRecord.add( state5 );

		BooleanWritable state6 = new BooleanWritable( request.getState6() );
		aRecord.add( state6 );

		BooleanWritable state7 = new BooleanWritable( request.getState7() );
		aRecord.add( state7 );

		BooleanWritable state8 = new BooleanWritable( request.getState8() );
		aRecord.add( state8 );

		BooleanWritable state9 = new BooleanWritable( request.getState9() );
		aRecord.add( state9 );

		BooleanWritable state10 = new BooleanWritable( request.getState10() );
		aRecord.add( state10 );

		BooleanWritable state11 = new BooleanWritable( request.getState11() );
		aRecord.add( state11 );

		BooleanWritable state12 = new BooleanWritable( request.getState12() );
		aRecord.add( state12 );

		BooleanWritable state13 = new BooleanWritable( request.getState13() );
		aRecord.add( state13 );

		BooleanWritable state14 = new BooleanWritable( request.getState14() );
		aRecord.add( state14 );

		BooleanWritable state15 = new BooleanWritable( request.getState15() );
		aRecord.add( state15 );

		BooleanWritable state16 = new BooleanWritable( request.getState16() );
		aRecord.add( state16 );

		BooleanWritable state17 = new BooleanWritable( request.getState17() );
		aRecord.add( state17 );

		BooleanWritable state18 = new BooleanWritable( request.getState18() );
		aRecord.add( state18 );

		BooleanWritable state19 = new BooleanWritable( request.getState19() );
		aRecord.add( state19 );

		BooleanWritable state20 = new BooleanWritable( request.getState20() );
		aRecord.add( state20 );

		BooleanWritable state21 = new BooleanWritable( request.getState21() );
		aRecord.add( state21 );

		BooleanWritable state22 = new BooleanWritable( request.getState22() );
		aRecord.add( state22 );

		BooleanWritable state23 = new BooleanWritable( request.getState23() );
		aRecord.add( state23 );

		BooleanWritable state24 = new BooleanWritable( request.getState24() );
		aRecord.add( state24 );

		BooleanWritable state25 = new BooleanWritable( request.getState25() );
		aRecord.add( state25 );

		BooleanWritable state26 = new BooleanWritable( request.getState26() );
		aRecord.add( state26 );

		BooleanWritable state27 = new BooleanWritable( request.getState27() );
		aRecord.add( state27 );

		BooleanWritable state28 = new BooleanWritable( request.getState28() );
		aRecord.add( state28 );

		BooleanWritable state29 = new BooleanWritable( request.getState29() );
		aRecord.add( state29 );

		BooleanWritable state30 = new BooleanWritable( request.getState30() );
		aRecord.add( state30 );

		BooleanWritable state31 = new BooleanWritable( request.getState31() );
		aRecord.add( state31 );

		BooleanWritable state32 = new BooleanWritable( request.getState32() );
		aRecord.add( state32 );

		BooleanWritable state33 = new BooleanWritable( request.getState33() );
		aRecord.add( state33 );

		BooleanWritable state34 = new BooleanWritable( request.getState34() );
		aRecord.add( state34 );

		BooleanWritable state35 = new BooleanWritable( request.getState35() );
		aRecord.add( state35 );

		BooleanWritable state36 = new BooleanWritable( request.getState36() );
		aRecord.add( state36 );

		BooleanWritable state37 = new BooleanWritable( request.getState37() );
		aRecord.add( state37 );

		BooleanWritable state38 = new BooleanWritable( request.getState38() );
		aRecord.add( state38 );

		BooleanWritable state39 = new BooleanWritable( request.getState39() );
		aRecord.add( state39 );

		BooleanWritable state40 = new BooleanWritable( request.getState40() );
		aRecord.add( state40 );

		BooleanWritable state41 = new BooleanWritable( request.getState41() );
		aRecord.add( state41 );

		BooleanWritable state42 = new BooleanWritable( request.getState42() );
		aRecord.add( state42 );

		BooleanWritable state43 = new BooleanWritable( request.getState43() );
		aRecord.add( state43 );

		BooleanWritable state44 = new BooleanWritable( request.getState44() );
		aRecord.add( state44 );

		BooleanWritable state45 = new BooleanWritable( request.getState45() );
		aRecord.add( state45 );

		BooleanWritable state46 = new BooleanWritable( request.getState46() );
		aRecord.add( state46 );

		BooleanWritable state47 = new BooleanWritable( request.getState47() );
		aRecord.add( state47 );

		BooleanWritable state48 = new BooleanWritable( request.getState48() );
		aRecord.add( state48 );
		
		BooleanWritable state49 = new BooleanWritable( request.getState49() );
		aRecord.add( state49 );

		BooleanWritable state50 = new BooleanWritable( request.getState50() );
		aRecord.add( state50 );

		BooleanWritable state51 = new BooleanWritable( request.getState51() );
		aRecord.add( state51 );
		
		
		return aRecord;
	}

}
