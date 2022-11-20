package com.breaking.ct.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Role {
	
	// ATTRIBUTES
	@Id
	private String role;

	// CONTRUCTORS
	public Role() {
		
	}
	
	public Role(String role) {
		this.role = role;
	}

	// GETTERS AND SETTERS
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
}
