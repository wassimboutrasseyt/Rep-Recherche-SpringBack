package org.sid.appbackser.services.implementations;

import java.util.Optional;

import org.sid.appbackser.entities.RessourceFolder.Calendrier;
import org.sid.appbackser.repositories.CalendrierRepository;

import org.sid.appbackser.services.CalendrierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalendrierServiceImpl implements CalendrierService{

    @Autowired
    CalendrierRepository calendrierRepository;

    @Override
    public Optional<Calendrier> getbyId(Integer calendrierId) {

        return calendrierRepository.findById(calendrierId);
        
    }

    @Override
    public Calendrier createCalendar(Calendrier calendrier) {
        return calendrierRepository.save(calendrier);
    }

    
}