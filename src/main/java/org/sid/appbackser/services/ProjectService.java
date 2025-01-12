package org.sid.appbackser.services;

import java.util.List;
import java.util.Map;

import org.sid.appbackser.dto.ProjectDTO;
import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ProjectService {
	public Project createProject(Project project, Account creatorId);

    public Page<Project> findAllProjects(PageRequest pageable);
	public List<ProjectDTO> getProjectsByIds(List<Integer> projectIds);

	public Project getProjectByShortName(String shortName);

	public boolean isAccountMemberOfProject(Integer accountId, Integer projectId);
	List<Map<String, Object>> getProjectGroupsWithMembers(Integer projectId);

	void addMemberToProject(Integer projectId, Integer adminId, String newMemberEmail);
	void promoteMemberToAdmin(Integer projectId, Integer adminId, Integer memberId);

	void removeMemberFromProject(Integer projectId, Integer adminId, String memberEmail);
	void demoteAdminToMember(Integer projectId, Integer adminId, Integer adminToDemoteId);
}
