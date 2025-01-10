package org.sid.appbackser.services;

import java.util.List;

import org.sid.appbackser.entities.GroupAccount;

public interface GroupAccountService {

    String addAccountToGroupWithRole(GroupAccount groupAccount);

    String removeAccountFromGroup(Integer accountId, Integer groupId);

    GroupAccount getRoleForAccountInGroup(Integer accountId, Integer groupId);

	void assignAccountToGroupWithRole(Integer accountId, Integer groupId);


    List<GroupAccount> getGroupsForAccount(Integer accountId);
}
