package com.breaking.ct.services;

import com.breaking.ct.dto.AdminDTO;
import com.breaking.ct.mappers.AdminMapper;
import com.breaking.ct.models.Admin;
import com.breaking.ct.repositories.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminService {
	private final AdminMapper adminMapper;
	private AdminRepository adminRepository;

	public List<Admin> getTodosAdmins() {
		return adminRepository.findAll();
	}

	public Optional<Admin> getAdminByLogin(String login) {
		return adminRepository.findFirstByLogin(login);
	}

	public void addAdmin(AdminDTO dto) {
		Optional<Admin> teste1 = adminRepository.findFirstByLogin(dto.getLogin());
		if (teste1.isPresent()) return;
		Optional<Admin> teste2 = adminRepository.findFirstByEmail(dto.getEmail());
		if (teste2.isPresent()) return;
		Admin newAdmin = adminMapper.map(dto);
		adminRepository.save(newAdmin);
	}

	public void updateAdmin(AdminDTO dto) {
		adminRepository.deleteAllByLogin(dto.getLogin());
		Admin admin = adminMapper.map(dto);
		adminRepository.save(admin);
	}

	public void deleteAdmin(String login) {
		adminRepository.deleteAllByLogin(login);
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
