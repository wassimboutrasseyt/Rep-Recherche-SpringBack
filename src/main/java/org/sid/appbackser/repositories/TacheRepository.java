package org.sid.appbackser.repositories;

import org.sid.appbackser.entities.RessourceFolder.Tache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TacheRepository extends JpaRepository<Tache, Integer>{
    
}
