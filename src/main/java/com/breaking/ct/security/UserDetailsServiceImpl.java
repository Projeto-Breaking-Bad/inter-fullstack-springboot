package com.breaking.ct.security;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.breaking.ct.models.Aluno;
import com.breaking.ct.repositories.AlunoRepository;
import com.breaking.ct.models.Empresa;
import com.breaking.ct.repositories.EmpresaRepository;
import com.breaking.ct.models.Admin;
import com.breaking.ct.repositories.AdminRepository;

@Service("userDetailsService")
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private AdminRepository adminRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// Este metodo eh chamado pelo email
		try {
			
			Optional<Aluno> aluno = alunoRepository.findByEmail(email);
			if(aluno.isPresent())
				return new User(aluno.get().getUsername(), aluno.get().getPassword(), aluno.get().getAuthorities());
			
			Optional<Empresa> empresa = empresaRepository.findByEmail(email);
			if(empresa.isPresent())
				return new User(empresa.get().getUsername(), empresa.get().getPassword(), empresa.get().getAuthorities());
			
			Optional<Admin> admin = adminRepository.findByEmail(email);
			if(admin.isPresent()) 
				return new User(admin.get().getUsername(), admin.get().getPassword(), admin.get().getAuthorities());
			
			throw new Exception();
		} 
		catch(Exception e) {
			throw new UsernameNotFoundException("Username not found");
		}
	}

}
