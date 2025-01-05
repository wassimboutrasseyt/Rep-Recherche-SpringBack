package org.sid.appbackser.services;

import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.Project;

public interface ProjectService {
	 public Project createProject(Project project, Account creatorId);
	 
}
