package org.sid.appbackser.services;

import java.util.List;

import org.sid.appbackser.entities.Proposition;

public interface PropositionService {
    Proposition createProposition(Proposition proposition);
    List<Proposition> getPendingPropositions();
    Proposition approveProposition(Integer propositionId);
    Proposition rejectProposition(Integer propositionId);
}
