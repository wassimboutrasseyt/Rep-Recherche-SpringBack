package org.sid.appbackser.services.initializers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import org.sid.appbackser.entities.Role;
import org.sid.appbackser.enums.Roles;
import org.sid.appbackser.repositories.RoleRepository;

import jakarta.annotation.PostConstruct;

@Service
public class RoleInitializer {

    @Autowired
    private RoleRepository roleRepository;

    @PostConstruct
    public void initRoles() {
        if (roleRepository.count() == 0) {
            for (Roles roleType : Roles.values()) {
                Role role = new Role(roleType);
                roleRepository.save(role);
            }
        }
    }
}