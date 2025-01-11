package org.sid.appbackser.entities.RessourceFolder;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String localPath; // Full path to the folder on the local file system

    // @Column(nullable = false)
    private Integer ownerId; // The accountID of the owner
    
    @ManyToOne
    @JoinColumn(name = "depot_id", nullable = false)
    @JsonBackReference // Prevents infinite recursion by ignoring this side during serialization
    private Depot depot;

    @ManyToOne
    @JoinColumn(name = "parent_folder_id", nullable = true)
    @JsonIgnore
    private Folder parentFolder;
    
    @OneToMany(mappedBy = "parentFolder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Folder> subFolders;
    
    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<File_> files;
    
    private Instant createdAt = Instant.now();

}