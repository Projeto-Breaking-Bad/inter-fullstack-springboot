package com.breaking.ct.repositories;

import com.breaking.ct.models.Vaga;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VagaRepository extends MongoRepository<Vaga, String> {
	Optional<Vaga> findById(String id);
}
