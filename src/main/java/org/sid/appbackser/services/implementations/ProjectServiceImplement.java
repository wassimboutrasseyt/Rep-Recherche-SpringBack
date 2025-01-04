package org.sid.appbackser.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sid.appbackser.entities.Group;
import org.sid.appbackser.entities.Project;
import org.sid.appbackser.repositories.GroupRepository;
import org.sid.appbackser.repositories.ProjectRepository;
import org.sid.appbackser.services.ProjectService;

@Service
public class ProjectServiceImplement implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    
    @Autowired
    private GroupRepository groupRepository;

    public Project createProject(Project project) {
        Group projectGroup = new Group();
        projectGroup.setName(project.getLongname());
        groupRepository.save(projectGroup);

        Group adminGroup = new Group();
        adminGroup.setName(project.getLongname() + "-adm");
        groupRepository.save(adminGroup);

        project.setProjectGroup(projectGroup);
        project.setAdminGroup(adminGroup);

        return projectRepository.save(project);
    }
}
