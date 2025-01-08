package org.sid.appbackser.services.implementations;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

import org.sid.appbackser.entities.RessourceFolder.File_;
import org.sid.appbackser.entities.RessourceFolder.Folder;
import org.sid.appbackser.repositories.File_Repository;
import org.sid.appbackser.repositories.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class File_ServiceImp {
    @Autowired
    private File_Repository fileRepository;

    @Autowired
    private FolderRepository folderRepository;

    public File_ uploadFile(MultipartFile file, Integer folderId) throws IOException {
        Folder folder = folderRepository.findById(folderId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid folder ID"));

        // Create a new file entry in the local file system
        String path = folder.getLocalPath() + "/" + file.getOriginalFilename();
        File localFile = new File(path);
        file.transferTo(localFile); // Save the file locally

        // Create a file entry in the database
        File_ newFile = new File_();
        newFile.setName(file.getOriginalFilename());
        newFile.setSize(file.getSize());
        newFile.setType(file.getContentType());
        newFile.setCreatedAt(Instant.now());
        newFile.setFolder(folder);
        fileRepository.save(newFile);

        return newFile;
    }

    public File_ retrieveFile(Integer fileId) {
        return fileRepository.findById(fileId)
            .orElseThrow(() -> new IllegalArgumentException("File not found"));
    }

    public void deleteFile(Integer fileId) {
        File_ file = fileRepository.findById(fileId)
            .orElseThrow(() -> new IllegalArgumentException("File not found"));

        // Delete file entry from database
        fileRepository.delete(file);

        // Delete file from local storage
        File localFile = new File(file.getLocalPath());
        if (localFile.exists()) {
            localFile.delete(); // Delete the file from the local filesystem
        }
    }
}
