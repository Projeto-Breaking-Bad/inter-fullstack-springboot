package com.breaking.ct.security;

import com.breaking.ct.models.Admin;
import com.breaking.ct.models.Aluno;
import com.breaking.ct.models.Empresa;
import com.breaking.ct.repositories.AdminRepository;
import com.breaking.ct.repositories.AlunoRepository;
import com.breaking.ct.repositories.EmpresaRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service("userDetailsService")
@Transactional
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private AlunoRepository alunoRepository;
	private EmpresaRepository empresaRepository;
	private AdminRepository adminRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Aluno> aluno = alunoRepository.findFirstByEmail(email);
		if (aluno.isPresent())
			return new User(aluno.get().getUsername(), aluno.get().getPassword(), aluno.get().getAuthorities());

		Optional<Empresa> empresa = empresaRepository.findFirstByEmail(email);
		if (empresa.isPresent())
			return new User(empresa.get().getUsername(), empresa.get().getPassword(), empresa.get().getAuthorities());

		Optional<Admin> admin = adminRepository.findFirstByEmail(email);
		if (admin.isPresent())
			return new User(admin.get().getUsername(), admin.get().getPassword(), admin.get().getAuthorities());

		throw new UsernameNotFoundException("Usuario não encontrado");
	}
}
