package com.clothing.manager.repositories;

import com.clothing.manager.models.AtumUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AtumUserRepository extends CrudRepository<AtumUser, Integer> {
    Optional<AtumUser> findByUsername(String username);
}
