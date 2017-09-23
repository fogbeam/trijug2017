package org.fogbeam.presentation.realtimeml.nntrain;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.BasicConfigurator;
import org.datavec.api.records.reader.RecordReader;
import org.datavec.api.records.reader.impl.collection.CollectionRecordReader;
import org.datavec.api.writable.BooleanWritable;
import org.datavec.api.writable.FloatWritable;
import org.datavec.api.writable.IntWritable;
import org.datavec.api.writable.Writable;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.deeplearning4j.util.ModelSerializer;
import org.fogbeam.presentation.realtimeml.repository.OfferProductCategoryRepository;
import org.fogbeam.presentation.realtimeml.repository.OfferTypeRepository;
import org.fogbeam.presentation.realtimeml.repository.OrderRepository;
import org.fogbeam.presentation.realtimeml.repository.StateRepository;
import org.fogbeam.presentation.trijugml.model.OfferProductCategory;
import org.fogbeam.presentation.trijugml.model.OfferType;
import org.fogbeam.presentation.trijugml.model.Order;
import org.fogbeam.presentation.trijugml.model.Promotion;
import org.fogbeam.presentation.trijugml.model.State;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionStatus;



public class NNModelTrainer 
{

	
	static OrderRepository orders;
	static OfferProductCategoryRepository offerProductCategories;
	static OfferTypeRepository offerTypes;
	static StateRepository states;
	
	
	public static void main(String[] args) 
	{

		BasicConfigurator.configure();
		
		int seed = 123;
		double learningRate = 0.01;
		int batchSize = 50;
		int numEpochs = 15;
		
		int numInputs = 67;
		int numOutputs = 2;
		
		int numHiddenNodes = 100;

		
		List<List<Writable>> trainingData = new ArrayList<List<Writable>>();
		
		AbstractApplicationContext springContext = null;
		try
		{
			springContext = new ClassPathXmlApplicationContext( "applicationContext.xml" );
		
			JpaTransactionManager transactionManager = springContext.getBean( "transactionManager", JpaTransactionManager.class );
			
			TransactionStatus status = transactionManager.getTransaction( null );
			
			orders = springContext.getBean(OrderRepository.class);
			offerProductCategories = springContext.getBean( OfferProductCategoryRepository.class );
			offerTypes = springContext.getBean( OfferTypeRepository.class );
			states = springContext.getBean(  StateRepository.class );
			
			
			Iterable<Order> allOrders = orders.findAll();
			
			for( Order order : allOrders )
			{
			
				List<Writable> aRecord = makeRecord( order );
			
				trainingData.add( aRecord );
			}
			
		
			transactionManager.commit( status );
			
		}
		finally 
		{
			if( springContext != null ) 
			{
				springContext.close();
			}
		}
		
		
		try
		{
			
			RecordReader trainReader = new CollectionRecordReader(trainingData);
			DataSetIterator trainIterator = new RecordReaderDataSetIterator(trainReader, batchSize,0,2); 
			
		
			// do the normal DL4J stuff here...
			
			MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
											.seed(seed)
											.iterations(1)
											.optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
											.learningRate(learningRate)
											.updater(Updater.NESTEROVS)
											.momentum(0.7)
											.list()
											.layer(0, new DenseLayer.Builder()
												.nIn(numInputs)
												.nOut(numHiddenNodes)
												.weightInit(WeightInit.XAVIER)
												.activation("relu")
												.build() )
											.layer(1, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
													.weightInit(WeightInit.XAVIER)
													.activation("softmax")
													.nIn(numHiddenNodes)
													.nOut(numOutputs)
													.build() )
											.pretrain(false)
											.backprop(true)
											.build();			
			
			
			MultiLayerNetwork model = new MultiLayerNetwork(conf);
			model.init();
			model.setListeners(new ScoreIterationListener(10));
			
			System.out.println(  "Training network!" );
			for( int n = 0; n < numEpochs; n++ )
			{
				model.fit(trainIterator);
				
			}
			
			
			File locationToSave = new File("/opt/ml/models/PromotionsModel.zip");  // Where to save the network. Note: the file is in .zip format - can be opened externally
			ModelSerializer.writeModel( model, locationToSave, true );

		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		
		
		System.out.println( "done" );
	}

	
	public static List<Writable> makeRecord( Order order )
	{
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
		List<Long> opcIds = offerProductCategories.getAllIds();
		
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
		List<Long> otIds = offerTypes.getAllIds();
		
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
		
		List<Long> stateIds = states.getAllIds();
		
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
}
