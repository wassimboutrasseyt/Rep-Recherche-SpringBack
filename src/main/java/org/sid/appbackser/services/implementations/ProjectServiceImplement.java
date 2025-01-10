package org.sid.appbackser.services.implementations;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.ChatGroup;
import org.sid.appbackser.entities.Group;
import org.sid.appbackser.entities.GroupAccount;
import org.sid.appbackser.entities.Project;
import org.sid.appbackser.entities.RessourceFolder.RessourceProject;
import org.sid.appbackser.enums.ChatGroupType;
import org.sid.appbackser.enums.RolesPerGroup;
import org.sid.appbackser.repositories.ChatGroupRepository;
import org.sid.appbackser.repositories.GroupAccountRepository;
import org.sid.appbackser.repositories.GroupRepository;
import org.sid.appbackser.repositories.ProjectRepository;
import org.sid.appbackser.services.ChatGroupService;
import org.sid.appbackser.services.ProjectService;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.sid.appbackser.services.RessourceProjectService;

@Service
public class ProjectServiceImplement implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupAccountRepository groupAccountRepository;

    @Autowired
    private ChatGroupService chatGroupService;

    @Autowired
    private RessourceProjectService ressourcePersoService;
    
    @Autowired
    private ChatGroupRepository chatGroupRepository;

    @Transactional
    public Project createProject(Project project, Account creator) {

        /*
         * Groups creation
         */
        // project group
        Group projectGroup = new Group();
        projectGroup.setName(project.getShortName());
        projectGroup = groupRepository.save(projectGroup);
        projectGroup.setProject(project);

        // admin group
        Group adminGroup = new Group();
        adminGroup.setName(project.getShortName() + "-adm");
        adminGroup = groupRepository.save(adminGroup);
        adminGroup.setProject(project);

        // creator is the admin of the project (the association between the account and the group)
        GroupAccount groupAccount = new GroupAccount();
        groupAccount.setAccount(creator);
        groupAccount.setGroup(adminGroup);
        groupAccount.setRole(RolesPerGroup.ADMIN);

        groupAccount = groupAccountRepository.save(groupAccount);

        project.setProjectGroup(projectGroup);
        project.setAdminGroup(adminGroup);


        /*
         * creating the Ressources of the project  (WEB and SRC depots)
         */
        RessourceProject ressourceProject = ressourcePersoService.createRessourceProject(project);
        
        project.setRessourceProject(ressourceProject);
        
        /*
        * chat groups creation
        */
        
        // admins chat group
        ChatGroup adminChatGroup = chatGroupService.createChatGroup(project.getId(), project.getShortName() + "-adm", ChatGroupType.ADMIN, List.of(creator.getId()));
        // generale chat group (admins + members)
        ChatGroup generalChatGroup = chatGroupService.createChatGroup(project.getId(), project.getShortName(), ChatGroupType.GENERALE, List.of(creator.getId()));

        // Fetch the general ChatGroup from MongoDB

        project.setGeneralChatGroup(generalChatGroup); // Set the general chat group
        project.setAdminChatGroup(adminChatGroup); // Set the admin chat group
        
        // save the project
        project = projectRepository.save(project);

        return project;
    }

    @Override
    public Page<Project> findAllProjects(PageRequest pageable) {
       return  projectRepository.findAll(pageable);
    }

    @Override
    public List<Project> getProjectsByIds(List<Integer> projectIds) {
        if (projectIds == null || projectIds.isEmpty()) {
            return new ArrayList<>(); 
        }
        return projectRepository.findByIdIn(projectIds);
    }

@Override
    public Project getProjectByShortName(String shortName) {
        // Fetch the project from MySQL
        Project project = projectRepository.findByShortName(shortName);

        return project;
    }
    
    
}
