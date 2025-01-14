package org.sid.appbackser.services.implementations;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sid.appbackser.entities.RessourceFolder.File_;
import org.sid.appbackser.entities.RessourceFolder.Folder;
import org.sid.appbackser.repositories.File_Repository;
import org.sid.appbackser.repositories.FolderRepository;
import org.sid.appbackser.services.File_Service;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class File_ServiceImp implements File_Service {
    @Autowired
    private File_Repository fileRepository;

    @Autowired
    private FolderRepository folderRepository;

    @Override
    public File_ createFile(String fileName, Integer folderId, Integer ownerId, String fileType) throws IOException {
        // Retrieve the folder where the file will be stored
        Folder folder = folderRepository.findById(folderId)
                .orElseThrow(() -> new RuntimeException("Folder not found"));
    
        // If fileType is not provided, default to a generic "application/octet-stream"
        if (fileType == null || fileType.isEmpty()) {
            fileType = "application/octet-stream";
        }
    
        // Check if the folder exists on the server
        File localDir = new File(folder.getLocalPath());
        if (!localDir.exists()) {
            throw new RuntimeException("Folder does not exist on the server: " + folder.getLocalPath());
        }
    
        // Get the local folder path where the file will be saved
        String localPath = folder.getLocalPath() + "/" + fileName;
    
        // Check if the file already exists
        File destinationFile = new File(localPath);
        if (destinationFile.exists()) {
            throw new RuntimeException("File already exists: " + localPath);
        }
    
        // Create the file's metadata
        File_ fileMetadata = new File_();
        fileMetadata.setName(fileName);
        fileMetadata.setSize(0L); // Since it's an empty file, size is 0
        fileMetadata.setType(fileType); // Set the custom file type here
        fileMetadata.setOwnerId(ownerId);
        fileMetadata.setFolder(folder);
    
        // Create the empty file in the local path
        destinationFile.createNewFile(); // Create the empty file
    
        // Set the local path in the metadata
        fileMetadata.setLocalPath(localPath);
    
        // Save the file metadata in the database
        fileRepository.save(fileMetadata);
    
        // Return the file metadata object
        return fileMetadata;
    }


    public File_ uploadFile(String fileName, Integer folderId, Integer ownerId, String fileType, MultipartFile file) throws IOException {
    
        Folder folder = folderRepository.findById(folderId).orElseThrow(() -> {
            return new RuntimeException("Folder not found");
        });
    
        String sanitizedFileName = fileName.replace("'", ""); // Sanitize file name
        String localPath = folder.getLocalPath() + "/" + sanitizedFileName;
    
        File localDir = new File(folder.getLocalPath());
        if (!localDir.exists()) {
            throw new RuntimeException("directory not found: " + folder.getName());
        }
    
        File destinationFile = new File(localPath);
        file.transferTo(destinationFile);
    
        File_ fileMetadata = new File_();
        fileMetadata.setName(fileName);
        fileMetadata.setSize(file.getSize());
        fileMetadata.setType(fileType);
        fileMetadata.setOwnerId(ownerId);
        fileMetadata.setCreatedAt(Instant.now());
        fileMetadata.setLocalPath(localPath);
        fileMetadata.setFolder(folder);
    
        fileRepository.save(fileMetadata);
    
        return fileMetadata;
    }

    @Override
    public File downloadFile(Integer fileId) throws IOException {
        // Retrieve the file metadata from the database
        File_ fileMetadata = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        // Get the file's local path
        String localPath = fileMetadata.getLocalPath();
        File file = new File(localPath);

        // Check if the file exists
        if (!file.exists()) {
            throw new RuntimeException("File not found on the server: " + localPath);
        }
        return file; // Return the file object
    }

    @Override
    public void deleteFile(Integer fileId) {
        // Retrieve the file metadata
        File_ fileMetadata = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));

        // Get the file's local path
        String localPath = fileMetadata.getLocalPath();
        File file = new File(localPath);

        // Attempt to delete the physical file
        if (file.exists() && !file.delete()) {
            throw new RuntimeException("Failed to delete the file from the server: " + localPath);
        }

        // Delete the file metadata from the database
        fileRepository.delete(fileMetadata);
    }

}
