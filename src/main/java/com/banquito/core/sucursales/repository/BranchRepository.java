package com.banquito.core.sucursales.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.banquito.core.sucursales.model.Branch;

@Repository
public interface BranchRepository extends MongoRepository<Branch, String> {

    Optional<Branch> findByEmailAddress(String emailAddress);
    
    Boolean existsByEmailAddress(String emailAddress);
}