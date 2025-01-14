package org.sid.appbackser.entities.RessourceFolder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;


@MappedSuperclass
public abstract class Ressource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nom;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "calendrier_id")
    private Calendrier calendrier;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCalendrier(Calendrier calendrier) {
        this.calendrier = calendrier;
    }

    public Integer getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public Calendrier getCalendrier() {
        return calendrier;
    }

}