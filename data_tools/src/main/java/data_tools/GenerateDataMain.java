package data_tools;

import java.util.Date;

import org.apache.log4j.BasicConfigurator;
import org.fogbeam.presentation.realtimeml.repository.OfferProductCategoryRepository;
import org.fogbeam.presentation.realtimeml.repository.OfferTypeRepository;
import org.fogbeam.presentation.realtimeml.repository.OrderRepository;
import org.fogbeam.presentation.realtimeml.repository.PromotionRepository;
import org.fogbeam.presentation.realtimeml.repository.StateRepository;
import org.fogbeam.presentation.realtimeml.repository.UserRepository;
import org.fogbeam.presentation.trijugml.model.Order;
import org.fogbeam.presentation.trijugml.model.Promotion;
import org.fogbeam.presentation.trijugml.model.User;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionStatus;

public class GenerateDataMain
{
	public static void main( String[] args )
	{
		
		RandomData randomData = new RandomData();
		
		BasicConfigurator.configure();
		
		AbstractApplicationContext springContext = null;
		try
		{
			springContext = new ClassPathXmlApplicationContext( "applicationContext.xml" );
		
			JpaTransactionManager transactionManager = springContext.getBean( "transactionManager", JpaTransactionManager.class );
			
			
			TransactionStatus status = transactionManager.getTransaction( null );
			
			OrderRepository orders = springContext.getBean(OrderRepository.class);
			StateRepository states = springContext.getBean(  StateRepository.class );
			OfferTypeRepository offerTypes = springContext.getBean(  OfferTypeRepository.class );
			OfferProductCategoryRepository offerProductCategories = springContext.getBean( OfferProductCategoryRepository.class );
			UserRepository users = springContext.getBean( UserRepository.class );
			PromotionRepository promotions = springContext.getBean( PromotionRepository.class );
			

			for( int i = 0; i < 100; i++ )
			
			{

				// generate 1 record of random data
				Promotion promotion = new Promotion();
				promotion.setOfferType( offerTypes.getOfferTypeById( new Long( randomData.getRandomOfferType() ) ) );
				promotion.setOfferProductCategory( offerProductCategories.getOfferProductCategoryById( new Long( randomData.getRandomOfferProductCategory() ) ) );
				promotion.setPrimaryColor( randomData.getRandomColor() );
				promotion.setSecondaryColor( randomData.getRandomColor() );
				promotions.save( promotion );
				
				
				User user = new User();
				user.setState( states.getStateById( new Long( randomData.getRandomState() ) ) );
				user.setUserName( randomData.getRandomUserName() );
				users.save( user );
				
				Order order = new Order();
				order.setSubmittedTime( new Date() );
				order.setTotalAmount( randomData.getRandomTotalAmount() );
				order.setPromotion( promotion );
				order.setUser( user );
				order.setPromotionAdded( randomData.getRandomPromotionAdded() );
				
				
				// persist Order to db
				orders.save( order );
				
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
		
		

		
		System.out.println(  "done" );
				
	}
}
