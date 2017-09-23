package org.fogbeam.experimental.activemq.client;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.fogbeam.experimental.storm.TextHolder;
import org.fogbeam.presentation.trijugml.model.OfferProductCategory;
import org.fogbeam.presentation.trijugml.model.OfferType;
import org.fogbeam.presentation.trijugml.model.Order;
import org.fogbeam.presentation.trijugml.model.Promotion;
import org.fogbeam.presentation.trijugml.model.State;
import org.fogbeam.presentation.trijugml.model.User;

/**
 * 
 * Just a little utility for sending one-off messages to ActiveMQ.
 * Useful for testing/dev, debugging, etc.
 * 
 * 
 * @author prhodes
 *
 */
public class SimpleActiveMqSender 
{

	public static void main(String[] args) 
	{
		
        try {
            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

            // Create a Connection
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createQueue("backtype.storm.contrib.example.queue");

            // Create a MessageProducer from the Session to the Topic or Queue
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // Create a messages
            String text = "Hello world! From thread: " + Thread.currentThread().getName() + " : " + SimpleActiveMqSender.class.hashCode();
            // TextMessage message = session.createTextMessage(text);
            
            
            // create a record for training...
            Order order = new Order();
            order.setId( 42L ); // just a dummy value
            order.setTotalAmount( 50000L );
            Promotion promotion = new Promotion();
            
            promotion.setPrimaryColor( "FACADA" );
            promotion.setSecondaryColor( "00FF00" );
            
            order.setPromotion( promotion );
            order.setPromotionAdded( true );
            OfferType offerType = new OfferType();
            offerType.setId( 1L );
            promotion.setOfferType( offerType );
            
            
            OfferProductCategory offerProductCategory = new OfferProductCategory();
            offerProductCategory.setId( 4L );
            promotion.setOfferProductCategory( offerProductCategory );
            
            User user = new User();
            user.setUserName( "testUser" );
            State state = new State();
            state.setAbbreviation( "NC" );
            state.setId( 37L );
            state.setName( "North Carolina" );
            user.setState( state );
            
            order.setUser( user );
            
            
            ObjectMessage message = session.createObjectMessage( order );
            
            
            // Tell the producer to send the message
            System.out.println("Sent message: "+ message.hashCode() + " : " + Thread.currentThread().getName());
            producer.send(message);

            // Clean up
            session.close();
            connection.close();
        }
        catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }		
		
		
		
		
		System.out.println( "done..." );

	}

}
