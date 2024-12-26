package org.sid.appbackser.repostories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.sid.appbackser.entity.Proposition;
import org.sid.appbackser.entity.PropositionStatus;

@Repository
public interface PropositionRepository extends JpaRepository<Proposition, Integer> {
    List<Proposition> findByStatus(PropositionStatus status);
}
