package org.sid.appbackser.repositories;

import java.util.Optional;

import org.sid.appbackser.entities.RessourceFolder.Depot;
import org.sid.appbackser.enums.DepotType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepotRepository extends JpaRepository<Depot, Integer> {

    Optional<Depot> findByLocalPath(String depotPath);

    // You can define custom query methods here if needed
}