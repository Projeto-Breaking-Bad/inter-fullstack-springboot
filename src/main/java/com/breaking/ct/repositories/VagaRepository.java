package com.breaking.ct.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.breaking.ct.models.Vaga;
@Repository
public interface VagaRepository extends JpaRepository<Vaga, String>{
    
}
