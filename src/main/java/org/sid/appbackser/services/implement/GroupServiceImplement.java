package org.sid.appbackser.services.implement;

import org.sid.appbackser.entity.Group;
import org.sid.appbackser.entity.Account;
import org.sid.appbackser.repostories.GroupRepository;
import org.sid.appbackser.repostories.AccountRepository;
import org.sid.appbackser.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImplement implements GroupService {

    private final GroupRepository groupRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public GroupServiceImplement(GroupRepository groupRepository, AccountRepository accountRepository) {
        this.groupRepository = groupRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public Group getGroupById(Integer groupId) {
        Optional<Group> group = groupRepository.findById(groupId);
        return group.orElse(null);
    }

    @Override
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    public List<Account> getAccountsForGroup(Integer groupId) {
        Group group = groupRepository.findById(groupId).orElse(null);
        return group != null ? group.getAccounts() : null;
    }
}
