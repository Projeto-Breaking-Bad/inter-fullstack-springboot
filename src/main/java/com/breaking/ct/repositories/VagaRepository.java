package com.breaking.ct.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.breaking.ct.models.Vaga;

@Repository
public interface VagaRepository extends MongoRepository<Vaga, String> {
	Optional<Vaga> findById(String id);

	List<Vaga> findByCnpj(String cnpj);
}
