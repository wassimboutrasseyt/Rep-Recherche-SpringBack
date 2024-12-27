package org.sid.appbackser.services.initializers;

import java.util.Optional;
import java.util.Set;

import org.sid.appbackser.entities.Role;
import org.sid.appbackser.entities.User;
import org.sid.appbackser.entities.Account;
import org.sid.appbackser.enums.Roles;
import org.sid.appbackser.repositories.AccountRepository;
import org.sid.appbackser.repositories.RoleRepository;
import org.sid.appbackser.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
@DependsOn({"roleInitializer"})
public class AdminInitializer {

    @Autowired  
    private AccountRepository accountRepository;
    @Autowired  
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    @PostConstruct
    public void init(){
        Optional<Role> adminRoleOptional = roleRepository.findByRole(Roles.ADMIN);
        if (!adminRoleOptional.isPresent()) {
            throw new IllegalStateException("Admin role not found");
        }

        Role role = adminRoleOptional.get();
        boolean adminExists = accountRepository.existsByRole(role);
        if (!adminExists) {
            User user = new User();
            userRepository.save(user);
            Account admin = new Account();
            admin.setEmail("admin@ngr.com");
            admin.setPassword(encoder.encode("admin-ngr"));
            admin.setRole(role);
            admin.setUser(user);
            accountRepository.save(admin);
        }
    }
}
