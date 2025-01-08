package org.sid.appbackser.services.implementations;

import java.io.File;

import org.sid.appbackser.entities.RessourceFolder.Depot;
import org.sid.appbackser.enums.DepotType;
import org.sid.appbackser.repositories.DepotRepository;
import org.sid.appbackser.services.DepotService;
import org.sid.appbackser.services.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DepotServiceImp implements DepotService {

    @Autowired
    private DepotRepository depotRepository;

    @Autowired
    private FolderService folderService;

    private static final String BASE_PATH = "C:\\Users\\user\\Desktop\\Spring\\NGR\\AppBackService\\src\\main\\resources\\Storage\\";

    @Override
    public Depot createDepot(String projectName, DepotType type) {
        // Construct the full path to the depot with projectName
        String depotPath = BASE_PATH + projectName + "_ressources/" + type.toString();
        
        // Create and save the depot entity to the database
        Depot depot = new Depot();
        depot.setNom(type.toString());
        depot.setType(type);
        depot.setLocalPath(depotPath);
        
        // Check if the depot already exists in the database
        if ((depotRepository.findByLocalPath(depotPath)).isPresent()) {
            throw new IllegalArgumentException("Depot already exists at path: " + depotPath);
        }
        // Check if the directory already exists localy
        File depotDir = new File(depotPath);
        if (!depotDir.exists()) {
            depotDir.mkdirs(); // Create the directory if it doesn't exist
        } else {
            throw new IllegalArgumentException("Directory already exists at path: " + depotPath);
        }

        depotRepository.save(depot);
        return depot;
    }

}
