package com.breaking.ct.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.breaking.ct.models.Aluno;

@Repository
public interface AlunoRepository extends MongoRepository<Aluno, String> {
	
	public Optional<Aluno> findByEmail(String email);
	
    public Optional<Aluno> findByCpf(String cpf);
    
	public void deleteByCpf(String cpf);
}
