package org.sid.appbackser.services;

import org.sid.appbackser.entity.Account;
import org.sid.appbackser.entity.Group;
import java.util.List;
import org.sid.appbackser.dto.*;

public interface AccountService {

    Account createAccount(Account account); // Updated to return Account

    String updateAccount(Account account);

    Account getAccount(Integer accountId); // Consistent naming

    String deleteAccount(Integer accountId);

    List<Account> getAccountsByUserId(Integer userId);

    List<Account> getAllAccounts(); // Added for completeness

    List<Group> getGroupsForAccount(Integer accountId); // Added to fetch groups

	String verify(Account acc);

	UserLoggedDTO loadInfo(Account acc);
}
