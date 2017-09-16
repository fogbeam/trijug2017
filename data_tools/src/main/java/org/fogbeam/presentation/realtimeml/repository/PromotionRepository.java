package org.fogbeam.presentation.realtimeml.repository;

import org.fogbeam.presentation.trijugml.model.Promotion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends CrudRepository<Promotion, Long>
{
}
