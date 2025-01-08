package org.sid.appbackser.services;

import org.sid.appbackser.entities.RessourceFolder.RessourcePerso;

public interface RessourcePersoService {
    RessourcePerso createRessourcePerso(String email, RessourcePerso ressourcePerso);
}
