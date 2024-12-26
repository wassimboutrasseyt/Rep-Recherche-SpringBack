package org.sid.appbackser.services.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.sid.appbackser.entity.Group;
import org.sid.appbackser.entity.Project;
import org.sid.appbackser.repostories.GroupRepository;
import org.sid.appbackser.repostories.ProjectRepository;
import org.sid.appbackser.services.ProjectService;

@Service
public class ProjectServiceImplement implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    
    @Autowired
    private GroupRepository groupRepository;

    public Project createProject(Project project) {
        Group projectGroup = new Group();
        projectGroup.setName(project.getName());
        groupRepository.save(projectGroup);

        Group adminGroup = new Group();
        adminGroup.setName(project.getName() + "-adm");
        groupRepository.save(adminGroup);

        project.setProjectGroup(projectGroup);
        project.setAdminGroup(adminGroup);

        return projectRepository.save(project);
    }
}
