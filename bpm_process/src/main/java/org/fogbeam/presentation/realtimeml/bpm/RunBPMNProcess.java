package org.fogbeam.presentation.realtimeml.bpm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.fogbeam.presentation.trijugml.model.OfferProductCategory;
import org.fogbeam.presentation.trijugml.model.OfferType;
import org.fogbeam.presentation.trijugml.model.Promotion;

public class RunBPMNProcess
{
	public static void main( String[] args )
	{
		

		// in real life this process could be started in response to something
		// happening, like a user saving a new promotion of the DB.  Here those
		// details are left out in the name of simplicity.  
		
        // Create Activiti process engine                                                                                      
        ProcessEngine processEngine = ProcessEngineConfiguration
            .createStandaloneProcessEngineConfiguration()
            .setJdbcUrl( "jdbc:h2:~/activiti" )
            .setDatabaseSchemaUpdate("false")
            .buildProcessEngine();


        
        // Get Activiti services                                                                                               
        RepositoryService repositoryService = processEngine.getRepositoryService();
        RuntimeService runtimeService = processEngine.getRuntimeService();

        
		System.out.println("Number of process definitions: " + repositoryService.createProcessDefinitionQuery().count());
		
		List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().list();
		
		for( ProcessDefinition pd : processDefinitions )
		{
			System.out.println(  "pd: " + pd  );
		}

        
        TaskService taskService = processEngine.getTaskService();
		
        Map<String,Object> variables = new HashMap<String, Object>();
        
        Promotion candidatePromotion = new Promotion();
        candidatePromotion.setId( 42L );
        candidatePromotion.setPrimaryColor( "0F0A0C" );
        candidatePromotion.setSecondaryColor( "AFAAAC" );
        
        OfferProductCategory offerProductCategory = new OfferProductCategory();
        offerProductCategory.setId( 2L );
        candidatePromotion.setOfferProductCategory( offerProductCategory );
        
        OfferType offerType = new OfferType();
        offerType.setId( 1L );
        candidatePromotion.setOfferType( offerType );
        
        variables.put( "candidatePromotion", candidatePromotion );
        
        
		runtimeService.startProcessInstanceByKey( "releasePromotion", variables );
        
		
		System.out.println(  "done" );
	}
}
