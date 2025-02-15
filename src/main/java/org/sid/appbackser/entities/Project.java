package org.sid.appbackser.entities;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

import org.sid.appbackser.entities.RessourceFolder.RessourceProject;
import org.sid.appbackser.enums.ProjectType;
import org.sid.appbackser.enums.ProjectVisibility;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String longname;

    @Column(nullable = false, unique = true)
    private String shortName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectType type;

    @Column(nullable = false)
    private String category;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectVisibility visibility;

    private String licenseName; // Optional, only for "logiciel"

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    private String generalChatGroupId; // Reference to general ChatGroup
    private String adminChatGroupId;   // Reference to admin ChatGroup

    @Transient
    private ChatGroup generalChatGroup; // To store the actual ChatGroup object

    @Transient
    private ChatGroup adminChatGroup;   // To store the actual ChatGroup object


    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "project_group_id", referencedColumnName = "id")
    // @JsonBackReference // Prevents infinite recursion
    private Group projectGroup;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "admin_group_id", referencedColumnName = "id")
    // @JsonBackReference // Prevents infinite recursion
    private Group adminGroup;

    @OneToOne(mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Prevents infinite recursion, manages serialization for project
    private RessourceProject ressourceProject;
    
}
