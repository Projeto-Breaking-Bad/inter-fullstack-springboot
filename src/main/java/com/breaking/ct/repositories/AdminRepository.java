package com.breaking.ct.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.breaking.ct.models.Admin;
@Repository
public interface AdminRepository extends MongoRepository<Admin, String> {

    public Optional<Admin> findByLogin(String login);
    public void deleteByLogin(String login);
}
