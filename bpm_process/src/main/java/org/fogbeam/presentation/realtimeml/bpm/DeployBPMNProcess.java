package org.fogbeam.presentation.realtimeml.bpm;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;

public class DeployBPMNProcess
{
	public static void main( String[] args )
	{
	
		
        // Create Activiti process engine                                                                                      
        ProcessEngine processEngine = ProcessEngineConfiguration
            .createStandaloneProcessEngineConfiguration()
            .setJdbcUrl( "jdbc:h2:~/activiti" )
            .setDatabaseSchemaUpdate("true")
            .buildProcessEngine();


        
        // Get Activiti services                                                                                               
        RepositoryService repositoryService = processEngine.getRepositoryService();	
		
		repositoryService.createDeployment().addClasspathResource( "ReleasePromotion.bpmn" ).name( "releasePromotion" ).deploy();
		
		
		System.out.println("Number of process definitions: " + repositoryService.createProcessDefinitionQuery().count());
		
		List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().list();
		
		for( ProcessDefinition pd : processDefinitions )
		{
			System.out.println(  "pd: " + pd  );
		}
		
		
		System.out.println(  "done" );
	}
}
