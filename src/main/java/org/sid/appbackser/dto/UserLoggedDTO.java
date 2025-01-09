package org.sid.appbackser.dto;


import org.sid.appbackser.entities.User;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators.In;

import java.util.ArrayList;
import java.util.List;

import org.sid.appbackser.entities.GroupAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoggedDTO {

	public UserLoggedDTO(User user,Integer id, String email, String role) {
		this.user = user;
		this.id = id;
		this.email = email;
		this.role = role;
	}

	private User user;
	private Integer id;
	private String email;
	private String role;
	private List<GroupData> groups = new ArrayList<>(); // Groups the user belongs to



	@Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GroupData {
        private String rolePerGroup; // User's role within the group
        private Integer projectId; 
		private String projectShortName;// Associated project's ID
    }
	
}
