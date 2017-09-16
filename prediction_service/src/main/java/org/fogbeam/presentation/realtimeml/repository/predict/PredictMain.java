package org.fogbeam.presentation.realtimeml.repository.predict;

import java.io.File;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.cpu.nativecpu.NDArray;

public class PredictMain
{
	public static void main( String[] args ) throws Exception
	{
		
		// load our model and use it to make a prediction...
		File locationToSave = new File("MyMultiLayerNetwork.zip");
		
		MultiLayerNetwork model = ModelSerializer.restoreMultiLayerNetwork(locationToSave);
		
		
		float[] data = {};
		INDArray features = new NDArray(data);
		
		INDArray predicted = model.output(features,false);
		
		
		System.out.println( "prediction: " + (  ( predicted.getDouble(0) < predicted.getDouble(1) ) ? 1 : 0 ) );
		
		System.out.println(  "done" );
	}
}
