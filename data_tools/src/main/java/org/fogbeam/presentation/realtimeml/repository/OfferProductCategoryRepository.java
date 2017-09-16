package org.fogbeam.presentation.realtimeml.repository;

import java.util.List;

import org.fogbeam.presentation.trijugml.model.OfferProductCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferProductCategoryRepository extends CrudRepository<OfferProductCategory, Long>
{
	public OfferProductCategory getOfferProductCategoryById( final Long randomOfferProductCategory );
	
	@Query( "select distinct opc.id from OfferProductCategory as opc order by opc.id" )
	public List<Long> getAllIds();
}
