package org.sid.appbackser.repositories;

import org.sid.appbackser.entities.RessourceFolder.Calendrier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalendrierRepository extends JpaRepository<Calendrier, Integer> {
  
}
