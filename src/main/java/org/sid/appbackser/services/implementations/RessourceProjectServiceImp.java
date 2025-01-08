package org.sid.appbackser.services.implementations;

import org.sid.appbackser.entities.Project;
import org.sid.appbackser.entities.RessourceFolder.Depot;
import org.sid.appbackser.entities.RessourceFolder.RessourcePerso;
import org.sid.appbackser.entities.RessourceFolder.RessourceProject;
import org.sid.appbackser.enums.DepotType;
import org.sid.appbackser.repositories.RessourceProjectRepository;
import org.sid.appbackser.services.DepotService;
import org.sid.appbackser.services.RessourceProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RessourceProjectServiceImp implements RessourceProjectService {

    @Autowired
    private DepotService depotService;

    @Autowired
    private RessourceProjectRepository ressourceProjectRepository;

    public RessourceProject createRessourceProject(Project project) {
        
        // Create depots for the Project (WEB and SRC)
        Depot webDepot = depotService.createDepot(project.getShortName(), DepotType.WEB);
        Depot srcDepot = depotService.createDepot(project.getShortName(), DepotType.SRC);

        // Create a RessourcePerso entry and link the depots
        RessourceProject ressourceProject = new RessourceProject();
        ressourceProject.setProjet(project);
        ressourceProject.setWebDepot(webDepot);
        ressourceProject.setSrcDepot(srcDepot);
        ressourceProjectRepository.save(ressourceProject);

        return ressourceProject;
    }
}   
