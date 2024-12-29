package org.sid.appbackser.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="group_account")
public class GroupAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // The group the account belongs to
    @ManyToOne
    private Group group;

    // The account that belongs to the group
    @ManyToOne
    private Account account;

    @ManyToOne
    @JoinColumn(name = "rolePerGroup_id")
    private RolePerGroup role;
//    @ManyToOne
//    @JoinColumn(name = "role_id")
//    
//    private Role role;
    


}
