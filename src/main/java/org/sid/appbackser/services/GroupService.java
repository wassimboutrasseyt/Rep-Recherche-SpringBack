package org.sid.appbackser.services;

import org.sid.appbackser.entity.Group;
import org.sid.appbackser.entity.Account;

import java.util.List;

public interface GroupService {

    Group createGroup(Group group);

    Group getGroupById(Integer groupId);

    List<Group> getAllGroups();

    List<Account> getAccountsForGroup(Integer groupId);
}
