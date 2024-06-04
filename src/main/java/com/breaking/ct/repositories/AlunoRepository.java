package com.breaking.ct.repositories;

import com.breaking.ct.models.Aluno;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlunoRepository extends MongoRepository<Aluno, String> {
	Optional<Aluno> findByEmail(String email);

	Optional<Aluno> findByCpf(String cpf);

	void deleteByCpf(String cpf);

	void deleteByEmail(String email);
}
