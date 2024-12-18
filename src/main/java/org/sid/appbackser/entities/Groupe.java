package org.sid.appbackser.entities;


import java.util.Set;

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
public class Groupe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomGRP;
    @OneToMany(mappedBy = "groupe")
    private Set<GroupeAccount> groupAccount;

    @ManyToOne()
    private Project project;



}
