package org.sid.appbackser.dto;

import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.User;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)

public class UserAccountDTO {
	
    private User user;
    private Account account;

    // Getters and setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
