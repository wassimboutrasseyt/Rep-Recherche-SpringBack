package org.sid.appbackser.services.implementations;

import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.RessourceFolder.Calendrier;
import org.sid.appbackser.entities.RessourceFolder.RessourcePerso;
import org.sid.appbackser.repositories.CalendrierRepository;
import org.sid.appbackser.repositories.RessourcePersoRepository;
import org.sid.appbackser.services.RessourcePersoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RessourcePersoImpl implements RessourcePersoService {
    @Autowired
    RessourcePersoRepository ressourcePersoRepository;
    
    // @Autowired
    // CalendrierRepository calendrierRepository;

    
    @Override
    public RessourcePerso createRessourcePerso(String email, RessourcePerso ressourcePerso) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createRessourcePerso'");
    }

    @Override
    public Calendrier getCalendrierByAccount(Account account) {
        RessourcePerso ressourcePerso=  ressourcePersoRepository.findByAccount(account).get(0);
        return ressourcePerso.getCalendrier();

    }
    
    
}
