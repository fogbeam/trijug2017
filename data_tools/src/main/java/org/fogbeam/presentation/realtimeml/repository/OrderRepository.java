package org.fogbeam.presentation.realtimeml.repository;

import org.fogbeam.presentation.trijugml.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> 
{
	public Order getOrderById( final Long id );
	
}
