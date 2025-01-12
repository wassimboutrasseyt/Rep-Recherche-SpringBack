package org.sid.appbackser.repositories;

import java.util.List;

import org.sid.appbackser.entities.Group;
import org.sid.appbackser.entities.Group;
import org.sid.appbackser.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    List<Project> findByIdIn(List<Integer> ids);
    Project findByAdminGroup(Group adminGroup);
    Project findByProjectGroup(Group ProjectGroup);
    Project findByShortName(String shortName);

    @Query("SELECT p FROM Project p WHERE p.adminGroup = :group OR p.projectGroup = :group")
    Project findByAdminGroupOrProjectGroup(@Param("group") Group group);
    Project findProjectById(Integer projectId);

}
