package org.sid.appbackser.services.implementations;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.sid.appbackser.dto.ProjectDTO;
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

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Project createProject(Project project, Account creator) {

        // Use EntityManager to persist the project and generate the ID
        // it just like opening a transaction and it can be rolledback before commit so it takes the id by the database
        entityManager.persist(project);
        entityManager.flush(); // Ensures the ID is generated

        /*
         * Groups creation
         */
        // project group
        Group projectGroup = new Group();
        projectGroup.setName(project.getShortName());
        projectGroup = groupRepository.save(projectGroup);

        // admin group
        Group adminGroup = new Group();
        adminGroup.setName(project.getShortName() + "-adm");
        adminGroup = groupRepository.save(adminGroup);


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
        
        // Set the chat group IDs in the project
        project.setGeneralChatGroupId(generalChatGroup.getId());
        project.setAdminChatGroupId(adminChatGroup.getId());

        // save the project
        project = projectRepository.save(project);

        return project;
    }

    @Override
    public Project getProjectByShortName(String shortName) {
        // Fetch the project from MySQL
        Project project = projectRepository.findByShortName(shortName);
    
        // Fetch chat groups from MongoDB using their IDs
        ChatGroup generalChatGroup = chatGroupService.getChatGroupById(project.getGeneralChatGroupId());
        ChatGroup adminChatGroup = chatGroupService.getChatGroupById(project.getAdminChatGroupId());
    
        // Set the actual ChatGroup objects on the project 
        project.setGeneralChatGroup(generalChatGroup);
        project.setAdminChatGroup(adminChatGroup);
    
        return project;
    }

    @Override
    public Page<Project> findAllProjects(PageRequest pageable) {
       return  projectRepository.findAll(pageable);
    }

    @Override
    public List<ProjectDTO> getProjectsByIds(List<Integer> projectIds) {
        if (projectIds == null || projectIds.isEmpty()) {
            return new ArrayList<>(); 
        }
        List<Project> projects= projectRepository.findByIdIn(projectIds);
        List<ProjectDTO> projectDTOs=new ArrayList<>();
        projects.forEach(p->
        projectDTOs.add(new ProjectDTO(
            p.getId(),
            p.getLongname(),
            p.getShortName(),
            p.getType(),
            p.getCategory(),
            p.getVisibility(),
            p.getLicenseName(),
            p.getDescription(),
            p.getCreatedAt(),
            p.getGeneralChatGroupId(),
            p.getAdminChatGroupId(),
            p.getProjectGroup(),
            p.getAdminGroup()
        )));

        return projectDTOs;
    }
}
