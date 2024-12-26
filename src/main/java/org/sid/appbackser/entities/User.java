package org.sid.appbackser.entities;

import java.time.LocalDateTime;
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
    private long id ;
    private String nom ;
    private String prenom;
    private LocalDateTime dob;
    private String phone;
    private String profession;

    @OneToMany(mappedBy = "user")
    private Set<Account> acc;
    
}
