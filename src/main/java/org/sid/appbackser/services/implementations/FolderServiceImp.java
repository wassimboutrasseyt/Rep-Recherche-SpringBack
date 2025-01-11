package org.sid.appbackser.services.implementations;

import java.io.File;
import java.time.Instant;

import org.sid.appbackser.entities.RessourceFolder.Depot;
import org.sid.appbackser.entities.RessourceFolder.Folder;
import org.sid.appbackser.repositories.DepotRepository;
import org.sid.appbackser.repositories.FolderRepository;
import org.sid.appbackser.services.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FolderServiceImp implements FolderService {
    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private DepotRepository depotRepository;


    public Folder createFolder(Integer depotId, String name, Integer parentFolderId) {
        Depot depot = depotRepository.findById(depotId)
                .orElseThrow(() -> new IllegalArgumentException("Depot not found with id: " + depotId));
                
        Folder parentFolder = null;
        if (parentFolderId != null) {
            parentFolder = folderRepository.findById(parentFolderId)
                    .orElseThrow(() -> new IllegalArgumentException("Parent folder not found with id: " + parentFolderId));
        }

        String folderPath;

        if (parentFolder != null) {
            folderPath = parentFolder.getLocalPath() + "/" + name;
        } else {
            folderPath = depot.getLocalPath() + "/" + name;
        }

        // Create and save the Folder entity in the database
        Folder folder = new Folder();
        folder.setName(name);
        folder.setCreatedAt(Instant.now());
        folder.setLocalPath(folderPath);  // Store the local file path
        folder.setParentFolder(parentFolder);
        if(parentFolder==null){
            folder.setDepot(depot);  // Link the folder to the depot
        }  // Link the folder to its parent

        // Create the folder on the local filesystem
        File folderDirectory = new File(folderPath);
        if (!folderDirectory.exists()) {
            folderDirectory.mkdirs();  // Create the folder if it doesn't exist
        } else {
            throw new IllegalArgumentException("Directory already exists at path: " + folderPath);
        }

        folderRepository.save(folder);
        return folder;
    }

    public void deleteFolder(Integer folderId) {
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new IllegalArgumentException("Folder not found with id: " + folderId));

        // Delete the folder from the local filesystem
        File folderDirectory = new File(folder.getLocalPath());
        if (folderDirectory.exists()) {
            deleteDirectoryRecursively(folderDirectory);
        } else {
            throw new IllegalArgumentException("Directory does not exist at path: " + folder.getLocalPath());
        }

        // Delete the folder entity from the database
        folderRepository.delete(folder);
    }

    private void deleteDirectoryRecursively(File directory) {
        File[] allContents = directory.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectoryRecursively(file);
            }
        }
        directory.delete();
    }
}