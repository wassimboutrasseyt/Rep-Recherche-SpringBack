package org.sid.appbackser.entities;

import java.security.PrivateKey;
import java.util.Set;

import org.sid.appbackser.enums.ProjectType;
import org.sid.appbackser.enums.ProjectVisibility;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {
    @Id
    private Long id;
    private String name;
//    @ElementCollection
    @Enumerated(EnumType.STRING) // Store enums as Strings in the database
    private ProjectVisibility visibility;
//    @ElementCollection
    @Enumerated(EnumType.STRING) // Store enums as Strings in the database
    private ProjectType type;
    private String licence;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Groupe> group;
    
}
