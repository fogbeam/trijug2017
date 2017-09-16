package org.fogbeam.presentation.realtimeml.repository;

import org.fogbeam.presentation.trijugml.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long>
{
}
