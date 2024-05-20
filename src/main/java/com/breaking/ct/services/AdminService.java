package com.breaking.ct.services;

import com.breaking.ct.models.Admin;
import com.breaking.ct.repositories.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminService {

	private AdminRepository adminRepository;

	public List<Admin> getTodosAdmins() {
		return new ArrayList<>(adminRepository.findAll());
	}

	public Optional<Admin> getAdminByLogin(String login) {
		return adminRepository.findByLogin(login);
	}

	public Optional<Admin> getAdminByEmail(String email) {
		return adminRepository.findByEmail(email);
	}

	public void addAdmin(Admin admin) {
		adminRepository.save(admin);
	}

	public void updateAdmin(Admin novoadmin) {
		deleteAdmin(novoadmin.getLogin());
		adminRepository.save(novoadmin);
	}

	public void deleteAdmin(String login) {
		adminRepository.deleteByLogin(login);
	}

	public Admin getLogged() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email;

		if (principal instanceof UserDetails)
			email = ((UserDetails) principal).getUsername();
		else
			email = principal.toString();

		Optional<Admin> adminOp = getAdminByLogin(email);
		return adminOp.orElse(null);
	}
}
