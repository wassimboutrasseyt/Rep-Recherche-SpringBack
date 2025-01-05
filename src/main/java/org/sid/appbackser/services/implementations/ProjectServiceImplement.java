package org.sid.appbackser.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.Group;
import org.sid.appbackser.entities.GroupAccount;
import org.sid.appbackser.entities.Project;
import org.sid.appbackser.enums.ChatGroupType;
import org.sid.appbackser.enums.RolesPerGroup;
import org.sid.appbackser.repositories.GroupAccountRepository;
import org.sid.appbackser.repositories.GroupRepository;
import org.sid.appbackser.repositories.ProjectRepository;
import org.sid.appbackser.services.ChatGroupService;
import org.sid.appbackser.services.ProjectService;

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

    @Transactional
    public Project createProject(Project project, Account creator) {

        Group projectGroup = new Group();
        projectGroup.setName(project.getShortName());
        projectGroup = groupRepository.save(projectGroup);

        Group adminGroup = new Group();
        adminGroup.setName(project.getShortName() + "-adm");
        adminGroup = groupRepository.save(adminGroup);

        GroupAccount groupAccount = new GroupAccount();
        groupAccount.setAccount(creator);
        groupAccount.setGroup(adminGroup);
        groupAccount.setRole(RolesPerGroup.ADMIN);

        groupAccount = groupAccountRepository.save(groupAccount);

        project.setProjectGroup(projectGroup);
        project.setAdminGroup(adminGroup);

        project = projectRepository.save(project);

        // Creating Generale chat group for the project
        // List<Integer> accountIds = project.getAdminGroup().getAccounts().stream()
        //     .map(account -> account.getId())
        //     .collect(Collectors.toList());
        
        // admins chat group
        chatGroupService.createChatGroup(project.getId(), project.getShortName() + "-adm", ChatGroupType.ADMIN, List.of(creator.getId()));
        // generale chat group (admins + members)
        chatGroupService.createChatGroup(project.getId(), project.getShortName(), ChatGroupType.GENERALE, List.of(creator.getId()));

        return project;

    }
}
