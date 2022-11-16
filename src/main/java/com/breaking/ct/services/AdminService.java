package com.breaking.ct.services;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
		return adminRepository.findById(login);
	}

	public void addAdmin(Admin admin) {
		adminRepository.save(admin);
	}

	public void updateAdmin(Admin admin) {
		adminRepository.save(admin);
	}

	public void deleteAdmin(String login) {
		adminRepository.deleteById(login);
	}
}
