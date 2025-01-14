package org.sid.appbackser.entities.RessourceFolder;

import org.sid.appbackser.entities.Account;

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
public class RessourcePerso extends Ressource {
    @OneToOne
    private Account account;
    // @OneToOne
    // private Calendrier calendrier;

    
}