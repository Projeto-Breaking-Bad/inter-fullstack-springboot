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
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/images/**").permitAll()
			.antMatchers("/css/**").permitAll()
			.antMatchers("/js/**").permitAll()
			// .antMatchers("/alunos/cadastrar").permitAll()
			// .antMatchers("/alunos/**").authenticated()
			// .anyRequest().permitAll()
			// .anyRequest().authenticated()
			.and().formLogin().loginPage("/login").permitAll()
			.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout").permitAll()
			.and().csrf().disable();

			// .httpBasic()
			// .and()
			// .authorizeHttpRequests()
			// .anyRequest().authenticated()
			// .and()
			// .csrf().disable();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(userDetailsService)
			.passwordEncoder(pc());
	}
	
	public PasswordEncoder pc() {
		return new BCryptPasswordEncoder();
	}
	
}
