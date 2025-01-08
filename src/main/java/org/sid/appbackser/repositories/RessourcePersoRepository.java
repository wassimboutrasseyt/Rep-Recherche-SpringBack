package org.sid.appbackser.repositories;

import org.sid.appbackser.entities.RessourceFolder.RessourcePerso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RessourcePersoRepository extends JpaRepository<RessourcePerso, Integer> {
    // You can define custom query methods here if needed
}