package org.sid.appbackser.entities.RessourceFolder;

import java.util.List;

import org.sid.appbackser.enums.DepotType;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Depot {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private	Integer id;
    private String nom;
    
    @Column(nullable = false)
    private String localPath;

    @Enumerated(EnumType.STRING) 
    private DepotType type;

    @OneToMany(mappedBy = "depot", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Prevents infinite recursion, manages serialization for folders
    private List<Folder> folders;

}
