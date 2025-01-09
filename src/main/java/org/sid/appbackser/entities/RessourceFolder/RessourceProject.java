package org.sid.appbackser.entities.RessourceFolder;

import org.sid.appbackser.entities.Project;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RessourceProject extends Ressource {
    @OneToOne
    @JoinColumn(name = "project_id")
    @JsonManagedReference // Prevents infinite recursion, manages serialization for project
    private Project projet;
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true) 
    @JoinColumn(name = "web_depot_id")
    private Depot webDepot; // Represents the WEB depot

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true) 
    @JoinColumn(name = "src_depot_id")
    private Depot srcDepot; // Represents the SRC depot
}
