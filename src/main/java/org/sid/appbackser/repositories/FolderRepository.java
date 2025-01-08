package org.sid.appbackser.repositories;

import org.sid.appbackser.entities.RessourceFolder.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Integer> {
    // You can define custom query methods here if needed
}