package org.sid.appbackser.services;


import org.sid.appbackser.entities.Account;

import java.util.List;

public interface ProjectServices {
    List<String> getRolesFromAccount(Account account);

}
