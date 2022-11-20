package com.breaking.ct.security;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.breaking.ct.models.Aluno;
import com.breaking.ct.repositories.AlunoRepository;
import com.breaking.ct.repositories.EmpresaRepository;

@Service("userDetailsService")
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// Este metodo eh chamado pelo email
		try {
			Optional<Aluno> user = alunoRepository.findByEmail(email);
			if(!user.isPresent()) {
				user = empresaRepository.findByEmail(email);
				if(!user.isPresent()) {
					throw new Exception();
				}
			}
			
			return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), user.get().getAuthorities());
		} 
		catch(Exception e) {
			throw new UsernameNotFoundException("Username not found");
		}
	}

}
