package org.sid.appbackser.repositories;

import org.sid.appbackser.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
