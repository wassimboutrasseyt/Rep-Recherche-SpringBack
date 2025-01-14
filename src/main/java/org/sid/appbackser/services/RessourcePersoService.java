package org.sid.appbackser.services;

import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.RessourceFolder.Calendrier;
import org.sid.appbackser.entities.RessourceFolder.RessourcePerso;

public interface RessourcePersoService {
    RessourcePerso createRessourcePerso(String email, RessourcePerso ressourcePerso);
    Calendrier getCalendrierByAccount(Account account);
}
