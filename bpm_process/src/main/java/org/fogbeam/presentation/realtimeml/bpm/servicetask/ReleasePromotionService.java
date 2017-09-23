package org.fogbeam.presentation.realtimeml.bpm.servicetask;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class ReleasePromotionService implements JavaDelegate
{
	@Override
	public void execute( DelegateExecution execution ) throws Exception
	{
		
		
		System.out.println( "Release new Promotion into the wild!!" );
	}
}
