package org.sid.appbackser.services.implementations;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.sid.appbackser.dto.ProjectDTO;
import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.ChatGroup;
import org.sid.appbackser.entities.Group;
import org.sid.appbackser.entities.GroupAccount;
import org.sid.appbackser.entities.Project;
import org.sid.appbackser.entities.RessourceFolder.RessourceProject;
import org.sid.appbackser.enums.ChatGroupType;
import org.sid.appbackser.enums.RolesPerGroup;
import org.sid.appbackser.repositories.AccountRepository;
import org.sid.appbackser.repositories.ChatGroupRepository;
import org.sid.appbackser.repositories.GroupAccountRepository;
import org.sid.appbackser.repositories.GroupRepository;
import org.sid.appbackser.repositories.ProjectRepository;
import org.sid.appbackser.services.AccountService;
import org.sid.appbackser.services.ChatGroupService;
import org.sid.appbackser.services.EmailService;
import org.sid.appbackser.services.GroupAccountService;
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
    private AccountService accountService;
    
    @Autowired
    private GroupAccountService groupAccountService;

    @Autowired
    private ChatGroupService chatGroupService;

    @Autowired
    private RessourceProjectService ressourcePersoService;
    
    @Autowired
    private ChatGroupRepository chatGroupRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private EmailService emailService;

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

    @Override
    public boolean isAccountMemberOfProject(Integer accountId, Integer projectId) {
        // Step 1: Retrieve the project by ID
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    
        // Step 2: Retrieve all groups the account belongs to
        List<Integer> accountGroupIds = groupAccountRepository.findByAccountId(accountId).stream()
                .map(accountGroup -> accountGroup.getGroup().getId())
                .collect(Collectors.toList());
    
        // Step 3: Check if the account's groups match the project groups
        Integer projectGroupId = project.getProjectGroup().getId();
        Integer adminGroupId = project.getAdminGroup().getId();
    
        return accountGroupIds.contains(projectGroupId) || accountGroupIds.contains(adminGroupId);
    }

    public List<Map<String, Object>> getProjectGroupsWithMembers(Integer projectId) {
    // Step 1: Retrieve the project by ID
    Project project = projectRepository.findById(projectId)
            .orElseThrow(() -> new RuntimeException("Project not found"));

    // Step 2: Retrieve the project and admin groups associated with the project
    Group projectGroup = project.getProjectGroup();
    Group adminGroup = project.getAdminGroup();

    // Step 3: Prepare the response list
    List<Map<String, Object>> groupsWithMembers = new ArrayList<>();

    // Add the project group details
    Map<String, Object> projectGroupInfo = new HashMap<>();
    projectGroupInfo.put("groupName", projectGroup.getName());

    // Get members of the project group
    List<Map<String, Object>> projectGroupMembers = projectGroup.getAccounts().stream()
            .map(groupAccount -> {
                Account account = groupAccount.getAccount();
                Map<String, Object> memberInfo = new HashMap<>();
                memberInfo.put("id", account.getId());
                memberInfo.put("firstName", account.getUser().getFirstName());
                memberInfo.put("lastName", account.getUser().getLastName());
                memberInfo.put("email", account.getEmail());
                memberInfo.put("role", account.getRole());
                memberInfo.put("status", account.getStatus());
                return memberInfo;
            })
            .collect(Collectors.toList());

    projectGroupInfo.put("members", projectGroupMembers);
    groupsWithMembers.add(projectGroupInfo);

    // Add the admin group details
    Map<String, Object> adminGroupInfo = new HashMap<>();
    adminGroupInfo.put("groupName", adminGroup.getName());

    // Get members of the admin group
    List<Map<String, Object>> adminGroupMembers = adminGroup.getAccounts().stream()
            .map(groupAccount -> {
                Account account = groupAccount.getAccount();
                Map<String, Object> memberInfo = new HashMap<>();
                memberInfo.put("id", account.getId());
                memberInfo.put("firstName", account.getUser().getFirstName());
                memberInfo.put("lastName", account.getUser().getLastName());
                memberInfo.put("email", account.getEmail());
                memberInfo.put("role", account.getRole());
                memberInfo.put("status", account.getStatus());
                return memberInfo;
            })
            .collect(Collectors.toList());

    adminGroupInfo.put("members", adminGroupMembers);
    groupsWithMembers.add(adminGroupInfo);

    return groupsWithMembers;
}

    

    @Override
    public void addMemberToProject(Integer projectId, Integer adminId, String newMemberEmail) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    
        Account newMemberAccount = accountService.getAccountByEmail(newMemberEmail);
        // Step 2: Check if the admin is a member of the admin group
        if (!groupAccountService.isAccountMemberOfGroup(adminId, project.getAdminGroup().getId())) {
            throw new RuntimeException("Only project admins can add members");
        }
        // Step 4: Check if the user is already a member of the project group
        if (groupAccountService.isAccountMemberOfGroup(newMemberAccount.getId(), project.getProjectGroup().getId())) {
            throw new RuntimeException("User is already a member of the project");
        }

        //adding hem as a normal member
        groupAccountService.assignAccountToGroupWithRole(newMemberAccount.getId(), project.getProjectGroup().getId() , RolesPerGroup.MEMBER);
        chatGroupService.addMember(project.getGeneralChatGroupId(), newMemberAccount.getId());
        emailService.notifyUserAddedToProject(project, newMemberAccount.getId(), adminId);
    }

    @Override
    public void promoteMemberToAdmin(Integer projectId, Integer adminId, Integer memberId) {
        // Step 1: Retrieve the project by ID
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // Step 2: Check if the requesting admin is part of the admin group
        if (!groupAccountService.isAccountMemberOfGroup(adminId, project.getAdminGroup().getId())) {
            throw new RuntimeException("Only project admins can promote members to admins");
        }

        // Step 3: Check if the target member is part of the project group
        if (!groupAccountService.isAccountMemberOfGroup(memberId, project.getProjectGroup().getId())) {
            throw new RuntimeException("The user is not a member of the project");
        }

        // Step 4: Check if the member is already part of the admin group
        if (groupAccountService.isAccountMemberOfGroup(memberId, project.getAdminGroup().getId())) {
            throw new RuntimeException("The user is already an admin of the project");
        }

        // Step 5: Assign the member to the admin group with the admin role
        groupAccountService.assignAccountToGroupWithRole(memberId, project.getAdminGroup().getId(), RolesPerGroup.ADMIN);
        groupAccountService.removeAccountFromGroup( memberId, project.getProjectGroup().getId());

        //step 6 adding hem to the admin chat group
        chatGroupService.addMember(project.getAdminChatGroupId(), memberId);
    }

    @Override
    public void removeMemberFromProject(Integer projectId, Integer adminId, String memberEmail) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Account memberAccount = accountService.getAccountByEmail(memberEmail);
        // Step 2: Check if the admin is a member of the admin group
        if (!groupAccountService.isAccountMemberOfGroup(adminId, project.getAdminGroup().getId())) {
            throw new RuntimeException("Only project admins can remove members");
        }

        // Step 3: Check if the user to be removed is part of the project groups
        if (!groupAccountService.isAccountMemberOfGroup(memberAccount.getId(), project.getProjectGroup().getId()) 
            || !groupAccountService.isAccountMemberOfGroup(memberAccount.getId(), project.getAdminGroup().getId())
            ) {
            throw new RuntimeException("User is not a member of the project");
        }
        // Step 4: Remove the user from the project group
        groupAccountService.removeAccountFromGroup(memberAccount.getId(), project.getProjectGroup().getId());

        // if he was an admin removing hem from the admin group
        if (groupAccountService.isAccountMemberOfGroup(memberAccount.getId(), project.getAdminGroup().getId())) {
            groupAccountService.removeAccountFromGroup(memberAccount.getId(), project.getAdminGroup().getId());
        }

        // remove hem from the general chat group
        chatGroupService.removeMember(project.getGeneralChatGroupId(), memberAccount.getId());

        // if he was an admin removing hem from the admin chat group
        if (chatGroupService.getMembers(project.getAdminChatGroupId()).contains(memberAccount.getId())) {
            chatGroupService.removeMember(project.getAdminChatGroupId(), memberAccount.getId());
        }
    }

    @Override
    public void demoteAdminToMember(Integer projectId, Integer adminId, Integer adminToDemoteId) {
        // Step 1: Retrieve the project by ID
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // Step 2: Check if the requesting admin is part of the admin group
        if (!groupAccountService.isAccountMemberOfGroup(adminId, project.getAdminGroup().getId())) {
            throw new RuntimeException("Only project admins can demote other admins");
        }

        // Step 3: Check if the admin to be demoted is part of the admin group
        if (!groupAccountService.isAccountMemberOfGroup(adminToDemoteId, project.getAdminGroup().getId())) {
            throw new RuntimeException("The user is not an admin of the project");
        }

        // Step 4: Remove the admin from the admin group
        groupAccountService.removeAccountFromGroup(adminToDemoteId, project.getAdminGroup().getId());

        // Step 5: Assign the demoted user as a regular member in the project group
        groupAccountService.assignAccountToGroupWithRole(adminToDemoteId, project.getProjectGroup().getId(), RolesPerGroup.MEMBER);
        groupAccountService.removeAccountFromGroup( adminToDemoteId, project.getAdminGroup().getId());
        //removing from admin chat group
        chatGroupService.removeMember(project.getAdminChatGroupId(), adminToDemoteId);
        if (!chatGroupService.getMembers(project.getGeneralChatGroupId()).contains(adminToDemoteId)) {
            chatGroupService.addMember(project.getGeneralChatGroupId(), adminToDemoteId);
        } 
    }

    @Override
    public Project getProjectById(Integer projectId) {
        return projectRepository.findProjectById(projectId);
    }



    


    
}
