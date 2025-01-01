package org.sid.appbackser.dto;

import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.User;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserAccountDTO {
	
    private User user;
    private Account account;


}
