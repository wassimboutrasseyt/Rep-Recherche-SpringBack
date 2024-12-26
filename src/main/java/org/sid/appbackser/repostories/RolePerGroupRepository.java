package org.sid.appbackser.repostories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.sid.appbackser.entity.RolePerGroup;
import org.sid.appbackser.entity.RolesPerGroup;

@Repository
public interface RolePerGroupRepository extends JpaRepository<RolePerGroup, Long> {
    Optional<RolePerGroup> findByRole(RolesPerGroup role);
}
