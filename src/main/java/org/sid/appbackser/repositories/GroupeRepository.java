package org.sid.appbackser.repositories;

import org.sid.appbackser.entities.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupeRepository extends JpaRepository<Groupe , Long> {
    //Groupe findAllByIdBetween(int a , int b);  exemple

}
