package org.sid.appbackser.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sid.appbackser.entities.Role;
import org.sid.appbackser.entities.RolePerGroup;
import org.sid.appbackser.entities.Roles;
import org.sid.appbackser.entities.RolesPerGroup;
import org.sid.appbackser.repositories.RolePerGroupRepository;
import org.sid.appbackser.repositories.RoleRepository;

import jakarta.annotation.PostConstruct;

@Service
public class RolePerGroupInitializer {

    @Autowired
    private RolePerGroupRepository rolePerGroupRepository;

    @PostConstruct
    public void initRoles() {
        if (rolePerGroupRepository.count() == 0) {
            for (RolesPerGroup roleType : RolesPerGroup.values()) {
                RolePerGroup role = new RolePerGroup(roleType);
                rolePerGroupRepository.save(role);
            }
        }
    }
}