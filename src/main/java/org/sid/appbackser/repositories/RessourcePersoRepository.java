package org.sid.appbackser.repositories;

import org.sid.appbackser.entities.RessourceFolder.Calendrier;
import org.sid.appbackser.entities.RessourceFolder.RessourcePerso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import org.sid.appbackser.entities.Account;


@Repository
public interface RessourcePersoRepository extends JpaRepository<RessourcePerso, Integer> {
    List<RessourcePerso> findByAccount(Account account); 
}