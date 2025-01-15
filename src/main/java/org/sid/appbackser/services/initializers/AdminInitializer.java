package org.sid.appbackser.services.initializers;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.sid.appbackser.entities.User;
import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.ChatGroup;
import org.sid.appbackser.enums.AccountStatus;
import org.sid.appbackser.enums.ChatGroupType;
import org.sid.appbackser.enums.Roles;
import org.sid.appbackser.repositories.AccountRepository;
import org.sid.appbackser.repositories.ChatGroupRepository;
import org.sid.appbackser.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service

public class AdminInitializer {

    @Autowired  
    private AccountRepository accountRepository;
    @Autowired  
    private UserRepository userRepository;


    @Autowired
    private ChatGroupRepository chatGroupRepository;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    @PostConstruct
    public void init() {
        Roles role = Roles.ADMIN;
        boolean adminExists = accountRepository.existsByRole(role);
        if (!adminExists) {
            // Create a single user entity to associate with multiple accounts
            User user1 = new User();
            user1.setFirstName("");
            user1.setLastName("");
            user1.setPhone("");
            user1.setDob(new Date(System.currentTimeMillis())); // Set to the current date
            user1.setProffession("");
            userRepository.save(user1);
    
            // Create and save admin accounts
            Account admin1 = new Account();
            admin1.setEmail("Boutrasseytwassim@gmail.com");
            admin1.setPassword(encoder.encode("admin-ngr"));
            admin1.setRole(role);
            admin1.setUser(user1);
            admin1.setStatus(AccountStatus.ACTIVE);
    
            User user2 = new User();
            user2.setFirstName("");
            user2.setLastName("");
            user2.setPhone("");
            user2.setDob(new Date(System.currentTimeMillis())); // Set to the current date
            user2.setProffession("");
            userRepository.save(user2);
    
            Account admin2 = new Account();
            admin2.setEmail("amranihassan.am@gmail.com");
            admin2.setPassword(encoder.encode("admin-ngr"));
            admin2.setRole(role);
            admin2.setUser(user2);
            admin2.setStatus(AccountStatus.ACTIVE);
    
            User user3 = new User();
            user3.setFirstName("");
            user3.setLastName("");
            user3.setPhone("");
            user3.setDob(new Date(System.currentTimeMillis())); // Set to the current date
            user3.setProffession("");
            userRepository.save(user3);
    

            Account admin3 = new Account();
            admin3.setEmail("aymanaomaripro@gmail.com");
            admin3.setPassword(encoder.encode("admin-ngr"));
            admin3.setRole(role);
            admin3.setUser(user3);
            admin3.setStatus(AccountStatus.ACTIVE);
    
            accountRepository.save(admin1);
            accountRepository.save(admin2);
            accountRepository.save(admin3);
        }
    }
    
        }
