package org.sid.appbackser.repositories;

import java.util.List;

import org.sid.appbackser.entities.Group;
import org.sid.appbackser.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    List<Project> findByIdIn(List<Integer> ids);

    Project findByShortName(String shortName);
    Project findByProjectGroupOrAdminGroup(Group group);
}
