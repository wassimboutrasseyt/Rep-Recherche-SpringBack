package org.sid.appbackser.repositories;

import org.sid.appbackser.entities.RessourceFolder.File_;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface File_Repository extends JpaRepository<File_, Integer> {
    // You can define custom query methods here if needed
}
