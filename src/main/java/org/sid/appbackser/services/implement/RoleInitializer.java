package org.sid.appbackser.services.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.sid.appbackser.entity.Role;
import org.sid.appbackser.entity.Roles;
import org.sid.appbackser.repostories.RoleRepository;

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