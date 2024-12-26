package org.sid.appbackser.services;

import java.util.List;

import org.sid.appbackser.entity.Proposition;

public interface PropositionService {
    Proposition createProposition(Proposition proposition);
    List<Proposition> getPendingPropositions();
    Proposition approveProposition(Integer propositionId);
    Proposition rejectProposition(Integer propositionId);
}
