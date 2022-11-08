package com.breaking.ct.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.breaking.ct.models.Aluno;
import com.breaking.ct.repositories.AlunoRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	UserDetailsServiceImpl(AlunoRepository alunoRepository) {
		this.alunoRepository = alunoRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// Este metodo eh chamado pelo numero de cpf, ao inves de username
		Aluno aluno = alunoRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario com email \""+email+"\" nao foi encontrado!"));
		return aluno;
	}

}
