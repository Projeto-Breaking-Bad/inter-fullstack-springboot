package com.breaking.ct.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.breaking.ct.models.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
    
}
