package org.sid.appbackser.services.implementations;

import org.sid.appbackser.dto.DashboardAdminDTO;
import org.sid.appbackser.dto.DashboardRUserDTO;
import org.sid.appbackser.entities.Account;
import org.sid.appbackser.enums.PropositionStatus;
import org.sid.appbackser.repositories.AccountRepository;
import org.sid.appbackser.repositories.GroupAccountRepository;
import org.sid.appbackser.repositories.ProjectRepository;
import org.sid.appbackser.repositories.PropositionRepository;
import org.sid.appbackser.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImp implements DashboardService{

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PropositionRepository propositionRepository;

    @Autowired
    private GroupAccountRepository groupAccountRepository;

    @Override
    public DashboardAdminDTO getDashboardAdminInfos() {
        DashboardAdminDTO dashboardAdmin = new DashboardAdminDTO();

        long totalAccounts = accountRepository.count();
        long totalProjects = projectRepository.count();
        long pendingPropositions = propositionRepository.countByStatus(PropositionStatus.PENDING.toString());

        dashboardAdmin.setNbProjects(totalProjects);
        dashboardAdmin.setNbAccounts(totalAccounts);
        dashboardAdmin.setNbPendingPeopositions(pendingPropositions);

        return dashboardAdmin;
    }

    @Override
    public DashboardRUserDTO getDashboardRUsersInfos(Account authUser) {
        DashboardRUserDTO dashboardRUserDTO = new DashboardRUserDTO();

        Long totalProjectCreated = propositionRepository.countByAccountIdAndStatus(authUser.getId(), PropositionStatus.APPROVED);
        Long totalProjectMemberOn = groupAccountRepository.countDistinctGroupByAccountId(authUser.getId());

        dashboardRUserDTO.setProjectsMemberOn(totalProjectMemberOn);
        dashboardRUserDTO.setProjectsHeCreated(totalProjectCreated);

        return dashboardRUserDTO;
    }

}
