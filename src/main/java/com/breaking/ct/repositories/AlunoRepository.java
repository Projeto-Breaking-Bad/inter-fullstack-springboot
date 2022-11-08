package com.breaking.ct.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.breaking.ct.models.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, String> {
	
	Optional<Aluno> findByEmail(String email);
	
}
