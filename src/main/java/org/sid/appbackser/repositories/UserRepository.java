package org.sid.appbackser.repositories;

import java.util.*;

import org.sid.appbackser.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Page<User> findAll(Pageable pageable);
	
}
