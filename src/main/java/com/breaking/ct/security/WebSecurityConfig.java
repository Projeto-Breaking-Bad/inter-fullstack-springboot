package com.breaking.ct.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	 @Autowired
	 UserDetailsServiceImpl userDetailsService;

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
			.antMatchers("/images/**").permitAll()
			.antMatchers("/css/**").permitAll()
			.antMatchers("/js/**").permitAll()
			
			// Autorizacoes velhas
			.antMatchers("/alunos/cadastrar").permitAll()
			.antMatchers("/alunos/**").hasAnyAuthority("ALUNO", "ADMIN")
			.antMatchers("/empresas/cadastrar").permitAll()
			.antMatchers("/empresas/**").hasAnyAuthority("EMPRESA", "ADMIN")
			.antMatchers("/admins/**").hasAnyAuthority("ADMIN")
			.antMatchers("/vagas/cadastrar").hasAnyAuthority("EMPRESA", "ADMIN")
			// Autorizacoes velhas
			
			// Autorizacoes novas
			.antMatchers("/a/**").hasAnyAuthority("ADMIN")
			.antMatchers("/s/**").hasAnyAuthority("ADMIN","ALUNO")
			.antMatchers("/c/**").hasAnyAuthority("ADMIN","EMPRESA")
			.antMatchers("/cadastro/**").permitAll()
			// Autorizacoes novas
			
			.anyRequest().permitAll()
			.and().formLogin().loginPage("/login").permitAll()
			.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").permitAll()
			.and().csrf().disable();
	}
	
	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(pc());
	}
	
	@Bean
	public PasswordEncoder pc() {
		return new BCryptPasswordEncoder();
	}
	
}
