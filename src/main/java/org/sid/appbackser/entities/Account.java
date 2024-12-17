package org.sid.appbackser.entities;


import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    private String username;
    private String mail;
    private String password;
    
    @OneToMany(mappedBy = "account")
    private Set<GroupeAccount> groupeAccounts;
}
