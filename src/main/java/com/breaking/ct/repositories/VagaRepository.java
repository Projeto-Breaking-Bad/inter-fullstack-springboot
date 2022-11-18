package com.breaking.ct.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.breaking.ct.models.Vaga;
@Repository
public interface VagaRepository extends MongoRepository<Vaga, String>{
    
}
