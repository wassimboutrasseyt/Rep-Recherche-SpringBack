package org.sid.appbackser.repositories;

import java.util.List;

import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.Proposition;
import org.sid.appbackser.enums.PropositionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropositionRepository extends JpaRepository<Proposition, Integer> {
    List<Proposition> findByStatus(PropositionStatus status);
    List<Proposition> findByAccount(Account account);
    // Proposition fin
    long countByStatus(String string);
    long countByAccountIdAndStatus(Integer id, PropositionStatus approved);
}
