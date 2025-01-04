package org.sid.appbackser.services;

import java.util.List;

import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.Proposition;
import org.sid.appbackser.enums.PropositionStatus;

public interface PropositionService {
    String createProposition(Proposition proposition);
    List<Proposition> getPendingPropositions();
    Proposition approveProposition(Integer propositionId);
    Proposition rejectProposition(Integer propositionId);
    List<Proposition> getbByStatus(PropositionStatus status);
    List<Proposition> getAllPropositions();
    List<Proposition> getPropositionsByAccount(Account account);
    Proposition getPropositionById(Integer id);
}
