package org.sid.appbackser.Services;

import org.sid.appbackser.Repositories.UserRepository;
import org.sid.appbackser.entities.Account;

import java.util.List;

public interface ProjectServices {
    List<String> getRolesFromAccount(Account account);

}
