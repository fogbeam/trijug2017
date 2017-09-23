package org.fogbeam.presentation.realtimeml.bpm.servicetask;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.fogbeam.presentation.realtimeml.prediction.client.PredictionServiceGrpcClient;
import org.fogbeam.presentation.trijugml.model.Promotion;

public class MLPromotionClassifierService implements JavaDelegate
{
	@Override
	public void execute( DelegateExecution execution ) throws Exception
	{
		
		System.out.println(  "MLPromotionClassifierService - executing..." );
		
		Promotion candidatePromotion = (Promotion)execution.getVariable( "candidatePromotion" );
		
		
		
		PredictionServiceGrpcClient predictionClient = new PredictionServiceGrpcClient( "localhost", 50051 );
		
		// here we're just using dummy values for the totalAmount and State to keep this demo simple.
		// in real life, you might keep either not use state and totalAmount as features at all, or you
		// might choose to release/not-release promotions on a state-by-state basis. You might also
		// cluster the order amounts and then release promotions by the average value of each cluster
		// then select a promotion at runtime using the current total value of the user's cart, etc.
		
		boolean goodPromotion = predictionClient.makePrediction( candidatePromotion, 500.00, 37 );
		
		System.out.println(  "MLPromotionClassifierService - setting autoRelease to predicted value: " + goodPromotion );
		execution.setVariable( "autoRelease", goodPromotion );
		
	}
}