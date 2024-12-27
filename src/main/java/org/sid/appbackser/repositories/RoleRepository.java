package org.sid.appbackser.repositories;

import java.util.Optional;

import org.sid.appbackser.entities.Role;
import org.sid.appbackser.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(Roles role);
}
