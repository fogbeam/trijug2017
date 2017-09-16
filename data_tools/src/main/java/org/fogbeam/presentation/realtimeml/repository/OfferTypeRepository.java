package org.fogbeam.presentation.realtimeml.repository;

import java.util.List;

import org.fogbeam.presentation.trijugml.model.OfferType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferTypeRepository extends CrudRepository<OfferType,Long>
{
	public OfferType getOfferTypeById( final Long randomOfferType );
	
	
	@Query( "select distinct ot.id from OfferType as ot order by ot.id" )
	public List<Long> getAllIds();

}
