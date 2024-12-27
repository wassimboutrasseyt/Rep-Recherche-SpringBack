package org.sid.appbackser.repositories;

import java.util.*;

import org.sid.appbackser.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
	Optional<User> findByEmail(String email);
	Optional<User> findByUsernameOrEmail(String username, String email);
	List<User> findByIdIn(List<Integer> userIds);
	Optional<User> findByUsername(String username);
}
