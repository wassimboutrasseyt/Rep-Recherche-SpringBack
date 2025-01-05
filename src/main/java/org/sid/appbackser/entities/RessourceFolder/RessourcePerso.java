package org.sid.appbackser.entities.RessourceFolder;

import org.sid.appbackser.entities.Account;

import jakarta.persistence.OneToOne;

public class RessourcePerso extends Ressource {
    @OneToOne
    private Account account;

}