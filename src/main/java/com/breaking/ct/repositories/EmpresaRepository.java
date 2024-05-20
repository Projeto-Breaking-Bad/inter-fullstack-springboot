package com.breaking.ct.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.breaking.ct.models.Empresa;

@Repository
public interface EmpresaRepository extends MongoRepository<Empresa, String> {
	Optional<Empresa> findByEmail(String email);

	Optional<Empresa> findByCnpj(String cnpj);

	void deleteByCnpj(String cnpj);
}
