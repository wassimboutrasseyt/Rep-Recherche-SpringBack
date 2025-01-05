package org.sid.appbackser.entities.RessourceFolder;

import org.sid.appbackser.enums.DepotType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Depot {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private	Integer id;
    private String nom;
    @Enumerated(EnumType.STRING) 
    private DepotType type;
    
}
