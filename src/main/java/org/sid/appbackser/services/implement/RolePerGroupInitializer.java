package org.sid.appbackser.services.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.sid.appbackser.entity.Role;
import org.sid.appbackser.entity.RolePerGroup;
import org.sid.appbackser.entity.Roles;
import org.sid.appbackser.entity.RolesPerGroup;
import org.sid.appbackser.repostories.RolePerGroupRepository;
import org.sid.appbackser.repostories.RoleRepository;

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