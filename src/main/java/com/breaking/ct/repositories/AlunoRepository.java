package com.breaking.ct.repositories;

import com.breaking.ct.models.Aluno;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlunoRepository extends MongoRepository<Aluno, String> {
	Optional<Aluno> findFirstByEmail(String email);

	Optional<Aluno> findFirstByCpf(String cpf);

	void deleteAllByCpf(String cpf);

	void deleteAllByEmail(String email);
}
