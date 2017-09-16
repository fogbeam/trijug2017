package data_tools;

import org.apache.log4j.BasicConfigurator;
import org.fogbeam.presentation.realtimeml.repository.OrderRepository;
import org.fogbeam.presentation.trijugml.model.Order;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionStatus;

public class ListDataMain 
{

	public static void main(String[] args) 
	{
		BasicConfigurator.configure();
		
		AbstractApplicationContext springContext = null;
		try
		{
			springContext = new ClassPathXmlApplicationContext( "applicationContext.xml" );
		
			JpaTransactionManager transactionManager = springContext.getBean( "transactionManager", JpaTransactionManager.class );
			
			
			TransactionStatus status = transactionManager.getTransaction( null );
			
			OrderRepository orders = springContext.getBean(OrderRepository.class);
			
			Order myOrder = orders.getOrderById( 1L );
		
			System.out.println( "Order is: " + myOrder );
		
			transactionManager.commit( status );
			
		}
		finally 
		{
			if( springContext != null ) 
			{
				springContext.close();
			}
		}
		
		System.out.println( "done" );
	}

}
