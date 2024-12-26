package org.sid.appbackser.repostories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.sid.appbackser.entity.Role;
import org.sid.appbackser.entity.Roles;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(Roles role);
}
