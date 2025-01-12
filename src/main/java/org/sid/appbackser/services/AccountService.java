package org.sid.appbackser.services;

import java.security.Principal;
import java.util.List;
import org.sid.appbackser.dto.*;
import org.sid.appbackser.entities.Account;
import org.sid.appbackser.services.AccountDetails;
import org.sid.appbackser.entities.Group;


public interface AccountService {

    Account createAccount(Account account); // Updated to return Account

    String updateAccount(Account account);

    Account getAccount(Integer accountId); // Consistent naming

    String deleteAccount(Integer accountId);
    
    Account getAccountByEmail(String email);

    List<Account> getAccountsByUserId(Integer userId);
    
    List<Account> getAdmisAccount();

    List<Account> getAllAccounts(); // Added for completeness

    // List<Group> getGroupsForAccount(Integer accountId); 

	String verify(Account acc);

	UserLoggedDTO loadInfo(Account acc);


    Account getAccountFromToken(Principal principal);
}