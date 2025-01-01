package org.sid.appbackser.services.implementations;

import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.Group;
import org.sid.appbackser.entities.GroupAccount;
import org.sid.appbackser.entities.Role;
import org.sid.appbackser.repositories.AccountRepository;
import org.sid.appbackser.repositories.GroupAccountRepository;
import org.sid.appbackser.repositories.GroupRepository;
import org.sid.appbackser.repositories.RoleRepository;
import org.sid.appbackser.services.GroupAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupAccountServiceImplement implements GroupAccountService {

	
    private final GroupAccountRepository groupAccountRepository;

    
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    
    @Autowired
    public GroupAccountServiceImplement(GroupAccountRepository groupAccountRepository) {
        this.groupAccountRepository = groupAccountRepository;
    }

    @Override
    public String addAccountToGroupWithRole(GroupAccount groupAccount) {
        groupAccountRepository.save(groupAccount);
        return "Account added to group with role successfully!";
    }

    @Override
    public String removeAccountFromGroup(Integer accountId, Integer groupId) {
    	groupAccountRepository.deleteByAccountIdAndGroupId(accountId, groupId);
    	
    	return "Account removed from group successfully!";
    }

    @Override
    public GroupAccount getRoleForAccountInGroup(Integer accountId, Integer groupId) {
        return groupAccountRepository.findById(accountId).orElse(null);
    }

	@Override
    public void assignAccountToGroupWithRole(Integer accountId,Integer groupId) {
        // Fetch the Account, Group, and Role
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
        // Create the GroupAccount and assign Account, Role, and Group
        GroupAccount groupAccount = new GroupAccount();
        groupAccount.setAccount(account);
        groupAccount.setGroup(group);

        // Save the GroupAccount to the repository
        groupAccountRepository.save(groupAccount);
    }
   

}
