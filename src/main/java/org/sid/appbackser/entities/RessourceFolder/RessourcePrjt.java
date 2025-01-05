package org.sid.appbackser.entities.RessourceFolder;

import org.sid.appbackser.entities.Project;

import jakarta.persistence.OneToOne;

public class RessourcePrjt extends Ressource {
    @OneToOne
    private Project projet;
    
}
