package org.sid.appbackser.services.initializers;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.sid.appbackser.entities.User;
import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.ChatGroup;
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
    public void init(){
        // Optional<Role> adminRoleOptional = roleRepository.findByRole(Roles.ADMIN);
        // if (!adminRoleOptional.isPresent()) {
        //     throw new IllegalStateException("Admin role not found");
        // }

        Roles role = Roles.ADMIN;
        boolean adminExists = accountRepository.existsByRole(role);
        if (!adminExists) {
            User user1 = new User();
            userRepository.save(user1);
            Account admin = new Account();
            admin.setEmail("admin1@ngr.com");
            admin.setPassword(encoder.encode("admin-ngr"));
            admin.setRole(role);
            admin.setUser(user1);
            //accountRepository.save(admin);
            Account admin1 = new Account();
            admin1.setEmail("admin2@ngr.com");
            admin1.setPassword(encoder.encode("admin-ngr"));
            admin1.setRole(role);
            admin1.setUser(user1);
            //accountRepository.save(admin);
            Account admin2 = new Account();
            admin2.setEmail("admin3@ngr.com");
            admin2.setPassword(encoder.encode("admin-ngr"));
            admin2.setRole(role);
            admin2.setUser(user1);

            accountRepository.save(admin);
            accountRepository.save(admin1);
            accountRepository.save(admin2);

                ChatGroup newGroup = new ChatGroup();
                newGroup.setName("General Group 1");
                newGroup.setType(ChatGroupType.GENERALE);  // Adjust based on your use case
                newGroup.setMembers(List.of(1, 2, 3));  // Example: list of member IDs
                
                chatGroupRepository.save(newGroup);
                
                }
            }
        }
