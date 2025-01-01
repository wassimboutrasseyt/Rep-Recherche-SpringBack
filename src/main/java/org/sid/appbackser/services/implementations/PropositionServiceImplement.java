package org.sid.appbackser.services.implementations;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sid.appbackser.entities.Project;
import org.sid.appbackser.entities.Proposition;
import org.sid.appbackser.enums.PropositionStatus;
import org.sid.appbackser.repositories.PropositionRepository;
import org.sid.appbackser.services.ProjectService;
import org.sid.appbackser.services.PropositionService;

@Service
public class PropositionServiceImplement implements PropositionService {

    @Autowired
    private PropositionRepository propositionRepository;

    @Autowired
    private ProjectService projectService;
    

    @Override
    public Proposition createProposition(Proposition proposition) {
        proposition.setStatus(PropositionStatus.PENDING);
        proposition.setCreatedAt(LocalDateTime.now());
        return propositionRepository.save(proposition);
    }

    @Override
    public List<Proposition> getPendingPropositions() {
        return propositionRepository.findByStatus(PropositionStatus.PENDING);
    }

    @Override
    public Proposition approveProposition(Integer propositionId) {
        Proposition proposition = propositionRepository.findById(propositionId)
                .orElseThrow(() -> new RuntimeException("Proposition not found"));
        proposition.setStatus(PropositionStatus.APPROVED);
        propositionRepository.save(proposition);

        // Automatically create the project
        Project project = new Project();
        project.setName(proposition.getLongName());
        project.setShortName(proposition.getShortName());
        project.setType(proposition.getType());
        project.setTheme(proposition.getTheme());
        project.setVisibility(proposition.getVisibility());
        project.setLicenseName(proposition.getLicenseName());
        projectService.createProject(project);

        return proposition;
    }

    @Override
    public Proposition rejectProposition(Integer propositionId) {
        Proposition proposition = propositionRepository.findById(propositionId)
                .orElseThrow(() -> new RuntimeException("Proposition not found"));
        proposition.setStatus(PropositionStatus.REJECTED);
        return propositionRepository.save(proposition);
    }
}
