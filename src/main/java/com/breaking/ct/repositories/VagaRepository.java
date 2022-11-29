package com.breaking.ct.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.breaking.ct.models.Vaga;
@Repository
public interface VagaRepository extends MongoRepository<Vaga, String>{
    
    public Optional<Vaga> findById(String id);
    public List<Vaga> findByCnpj(String cnpj);

}
