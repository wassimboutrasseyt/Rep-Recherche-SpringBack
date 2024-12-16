package org.sid.appbackser.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private RoleTypes roleTypes;
    @ElementCollection
    @Enumerated(EnumType.STRING) // Store enums as Strings in the database
    private List<String> privileges;
    /*@OneToMany(mappedBy = "roles")
    private List<Groupe> groupes;
    @OneToMany(mappedBy = "role")
    private List<Account>accounts;*/


    public Role( RoleTypes roleTypes) {
        this.roleTypes = roleTypes;
        this.privileges = assignPrivilegesBasedOnRole(roleTypes);
    }

    // Map RoleTypes to appropriate privileges as List of Strings
    private List<String> assignPrivilegesBasedOnRole(RoleTypes roleTypes) {
        switch (roleTypes) {
            case Gest:
                return List.of(GuestPrivileges.BROWSE_PROJECTS.name(), GuestPrivileges.VIEW_INFO.name());
            case ProjectMembre:
                return List.of(ProjectMembrePrivileges.CREATE_TASK.name(), ProjectMembrePrivileges.UPDATE_TASK.name());
            case  AdminGrp:
                return List.of(AdminGrpPrivileges.MANAGE_USERS.name(), AdminGrpPrivileges.MANAGE_PROJECTS.name());
            default:
                return List.of();
        }
    }
















}
