package org.sid.appbackser.services.implementations;

import java.io.File;
import java.util.logging.Logger;
import java.util.logging.Level;

import org.sid.appbackser.entities.RessourceFolder.Depot;
import org.sid.appbackser.enums.DepotType;
import org.sid.appbackser.repositories.DepotRepository;
import org.sid.appbackser.services.DepotService;
import org.sid.appbackser.services.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DepotServiceImp implements DepotService {

    @Autowired
    private DepotRepository depotRepository;

    @Autowired
    private FolderService folderService;
    private static final String BASE_PATH = "C:\\Users\\wassi\\Documents\\Proojet\\Rep-Recherche-SpringBack\\src\\main\\resources\\Storage";
    
    private  final Logger LOGGER = Logger.getLogger(DepotServiceImp.class.getName());

    @Override
    public Depot createDepot(String projectName, DepotType type) {
        // Construct the full path to the depot with projectName
        String depotPath = BASE_PATH +"/"+ projectName + "_ressources/" + type.toString();
        
        // Create and save the depot entity to the database
        Depot depot = new Depot();
        depot.setNom(type.toString());
        depot.setType(type);
        depot.setLocalPath(depotPath);
        
           LOGGER.log(Level.INFO, "Depot already exists in the database **************************");
          // LOGGER.info("before the if condition "+depotRepository.findByLocalPath(depotPath));
        // if ((depotRepository.findByLocalPath(depotPath).isEmpty())) {
          
        //    LOGGER.info("after the second logger check isEmpty() "+depotRepository.findByLocalPath(depotPath).isEmpty());
        // }
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

    @Override
    public Depot getDepotById(Integer depotId) {
        return depotRepository.findById(depotId)
            .orElseThrow(() -> new EntityNotFoundException("Depot not found with ID: " + depotId));
    }
}