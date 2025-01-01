package org.sid.appbackser.services;

import java.util.List;

import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.Group;
import org.sid.appbackser.entities.GroupAccount;

public interface GroupService {

    Group createGroup(Group group);

    Group getGroupById(Integer groupId);

    List<Group> getAllGroups();

    List<GroupAccount> getAccountsForGroup(Integer groupId);
}
