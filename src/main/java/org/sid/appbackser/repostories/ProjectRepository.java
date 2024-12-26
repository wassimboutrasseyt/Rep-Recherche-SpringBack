package org.sid.appbackser.repostories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.sid.appbackser.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
