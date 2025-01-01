package org.sid.appbackser.dto;


import org.sid.appbackser.entities.User;

import lombok.Data;

@Data
public class UserLoggedDTO {

	private User user;
	private Integer id;
	private String email;
	private String role;
	
	

	
	
}
