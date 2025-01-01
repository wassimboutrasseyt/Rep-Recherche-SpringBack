package org.sid.appbackser.controllers;

import org.sid.appbackser.dto.UserAccountDTO;
import org.sid.appbackser.dto.UserLoggedDTO;
import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.User;
import org.sid.appbackser.services.AccountService;
import org.sid.appbackser.services.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthentificationController {

	private static final Logger logger =  LoggerFactory.getLogger(UserService.class);
	
	@Autowired
    private final AccountService accountService;
	
	@Autowired
	private UserService userService;

    
    public AuthentificationController(AccountService accountService) {
        this.accountService = accountService;
    }
    
    
    
    //Authentification methodes
    
    @PostMapping("/login")
    public ResponseEntity<UserLoggedDTO> login(@RequestBody Account acc) {
        String s=accountService.verify(acc);
        if(s==null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        UserLoggedDTO userInformation=accountService.loadInfo(acc);
        
        return ResponseEntity.status(HttpStatus.ACCEPTED).header("bearer",s).body(userInformation);
    }
    
   
    @PostMapping("/register")
    public ResponseEntity<String> createAccount(@RequestBody UserAccountDTO userAccount) {
    	User User=userService.createUser(userAccount.getUser());
        Account createdAccount = userAccount.getAccount();
        logger.info("user name:"+userAccount.getUser().getFirstName()+" "+userAccount.getUser().getLastName());
        createdAccount.setUser(User);
        createdAccount=accountService.createAccount(createdAccount);
        if(createdAccount!=null)logger.info("the created acount:"+createdAccount.getEmail());
        // Ensure service returns Account
        return ResponseEntity.status(HttpStatus.CREATED).body("created");
    }
    
}
