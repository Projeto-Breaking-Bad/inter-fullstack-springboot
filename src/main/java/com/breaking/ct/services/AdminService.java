package com.breaking.ct.services;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.breaking.ct.models.Admin;
import com.breaking.ct.repositories.AdminRepository;

@Service
public class AdminService {
    @Autowired
	private AdminRepository adminRepository;

    public ArrayList<Admin> getTodosAdmins() {
		ArrayList<Admin> admins = new ArrayList<>();
		adminRepository.findAll().forEach(admins::add);
		return admins;
	}

	public Optional<Admin> getAdminByLogin(String login){
		return adminRepository.findByLogin(login);
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

		String login = "";
		if (principal instanceof UserDetails) {
			login = ((UserDetails)principal).getUsername();
		} else {
			login = principal.toString();
		}
		
		Optional<Admin> adminOp = getAdminByLogin(login);
		if(adminOp.isEmpty())
			return null;
		return adminOp.get();
	}
}
