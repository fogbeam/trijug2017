package org.fogbeam.presentation.realtimeml.repository;

import java.util.List;

import org.fogbeam.presentation.trijugml.model.State;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends CrudRepository<State,Long>
{

	public State getStateById( final Long randomState );
	
	@Query( "select distinct state.id from State as state order by state.id" )
	public List<Long> getAllIds();

}
