package com.breaking.ct.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.breaking.ct.models.Admin;
import com.breaking.ct.repositories.AdminRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private AdminRepository adminRepository;
	
	UserDetailsServiceImpl(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		// Este metodo eh chamado pelo numero de cpf, ao inves de username
		Admin admin = adminRepository.findByLogin(login)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario com login \""+login+"\" nao foi encontrado!"));
		return admin;
	}

}
