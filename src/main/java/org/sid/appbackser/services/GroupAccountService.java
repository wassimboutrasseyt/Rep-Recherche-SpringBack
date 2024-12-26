package org.sid.appbackser.services;

import org.sid.appbackser.entity.GroupAccount;

public interface GroupAccountService {

    String addAccountToGroupWithRole(GroupAccount groupAccount);

    String removeAccountFromGroup(Integer accountId, Integer groupId);

    GroupAccount getRoleForAccountInGroup(Integer accountId, Integer groupId);


	void assignAccountToGroupWithRole(Integer accountId, Integer groupId);
}
