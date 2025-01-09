package org.sid.appbackser.services;

import java.util.List;

import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ProjectService {
	 public Project createProject(Project project, Account creatorId);

    public Page<Project> findAllProjects(PageRequest pageable);
	public List<Project> getProjectsByIds(List<Integer> projectIds);
	 
}
