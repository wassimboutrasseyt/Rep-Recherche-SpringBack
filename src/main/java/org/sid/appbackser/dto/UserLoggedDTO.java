package org.sid.appbackser.dto;


import org.sid.appbackser.entity.User;

import lombok.Data;

@Data

public class UserLoggedDTO {

	private User user;
	private String email;
	private String role;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user2) {
		this.user = user2;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	
	
}
