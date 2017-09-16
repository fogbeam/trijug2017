package data_tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.text.RandomStringGenerator;

public class RandomData
{
	Random stateRandom 			= new Random();
	Random colorRandom 			= new Random();
	Random offerTypeRandom 		= new Random();
	Random totalAmountRandom	= new Random();
	Random promotionAddedRandom = new Random();
	
	RandomStringGenerator userNameRandom = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
	
	
	List<String> previouslyGeneratedUserNames = new ArrayList<String>();
	
	
	
	public String getRandomUserName() 
	{
		
		String userName = "";
		
		boolean retry = false;
		do
		{
			userName = userNameRandom.generate( 12 );
			
			if( previouslyGeneratedUserNames.contains( userName )) 
			{
				System.out.println(  "duplicate username generated!" );
				retry = true;
			}
			else
			{
				previouslyGeneratedUserNames.add( userName );
				retry = false;
			}
			
		} while( retry );
		
		
		return userName;
	}
	
	public int getRandomState() 
	{
		int state = -1;
		boolean retry = false;
		do {
			state = stateRandom.nextInt( 56 ) + 1;
			
			if( state >= 57 || state <= 0 ) 
			{
				System.out.println( "invalid random int generated!" );
				retry = true;
			}
			else
			{
				switch( state ) 
				{
					case 3:
					case 7:
					case 14:
					case 43:
					case 52:
						retry = true;
						break;
					default:
						retry = false;
						break;
				}
			}
			
		} while( retry );
		
		
		return state;
		
	}
	
	
	public String getRandomColor()
	{
		
		String redPart = String.format("%02X", colorRandom.nextInt( 256 ));
		String greenPart = String.format("%02X", colorRandom.nextInt( 256 ));
		String bluePart = String.format("%02X", colorRandom.nextInt( 256 ));
		
		
		return redPart + greenPart + bluePart;
	}
	
	
	public int getRandomOfferType()
	{
		int offerType = offerTypeRandom.nextInt( 2 ) + 1;
		
		return offerType;
	}
	
	
	public int getRandomOfferProductCategory()
	{
		int offerType = offerTypeRandom.nextInt( 7 ) + 1;
		
		return offerType;
	}

	public Long getRandomTotalAmount()
	{
	
		Long totalAmount = (long)( totalAmountRandom.nextInt( 32000 ) + 500);
		
		return totalAmount;
	}

	public Boolean getRandomPromotionAdded()
	{
		boolean promotionAdded = promotionAddedRandom.nextBoolean();
		
		return promotionAdded;
	}
	
}
