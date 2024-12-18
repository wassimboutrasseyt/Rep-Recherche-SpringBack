package org.sid.appbackser.repositories;

import org.sid.appbackser.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    
    // Custom query to find a user by their name (if needed)
    Optional<User> findByNom(String nom);
    
    // You can also use this to find a user by ID, which is useful in your case
    Optional<User> findById(Long id);
}
