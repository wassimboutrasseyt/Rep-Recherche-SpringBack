package org.sid.appbackser.repositories;

import java.util.List;

import org.sid.appbackser.entities.Proposition;
import org.sid.appbackser.entities.PropositionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropositionRepository extends JpaRepository<Proposition, Integer> {
    List<Proposition> findByStatus(PropositionStatus status);
}
