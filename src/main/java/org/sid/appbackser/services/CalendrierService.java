package org.sid.appbackser.services;

import java.util.List;
import java.util.Optional;

import org.sid.appbackser.entities.RessourceFolder.Calendrier;
import org.sid.appbackser.entities.RessourceFolder.Tache;

public interface CalendrierService {
    public Optional<Calendrier> getbyId(Integer calendrierId);
    public Calendrier createCalendar(Calendrier calendrier);
}
