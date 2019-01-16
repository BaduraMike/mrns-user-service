package com.soft.mikessolutions.userservice.repositories;

import com.soft.mikessolutions.userservice.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}