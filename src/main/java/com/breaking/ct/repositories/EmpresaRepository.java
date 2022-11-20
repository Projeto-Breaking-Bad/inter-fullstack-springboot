package com.breaking.ct.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.breaking.ct.models.Aluno;
import com.breaking.ct.models.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, String> {
    
	Optional<Aluno> findByEmail(String email);
	
}
