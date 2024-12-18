package org.sid.appbackser.entities;

import java.util.Set;
import java.util.TreeSet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String nom ;
    private String prenom;
    @OneToMany(mappedBy = "user")
    private TreeSet<Account> acc;
    
}
