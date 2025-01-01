package org.sid.appbackser.repositories;

import java.util.Optional;

import org.sid.appbackser.entities.RolePerGroup;
import org.sid.appbackser.enums.RolesPerGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePerGroupRepository extends JpaRepository<RolePerGroup, Long> {
    Optional<RolePerGroup> findByRole(RolesPerGroup role);
}
