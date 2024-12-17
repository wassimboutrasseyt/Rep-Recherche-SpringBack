package org.sid.appbackser.Repositories;

import org.sid.appbackser.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User , Long> {
   // User findById();
}
