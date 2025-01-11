package org.sid.appbackser.services;

import java.io.File;
import java.io.IOException;

import org.sid.appbackser.entities.RessourceFolder.File_;
import org.springframework.web.multipart.MultipartFile;

public interface File_Service {
    File_ createFile(String fileName, Integer folderId, Integer ownerId, String fileType) throws IOException;
    File_ uploadFile(String fileName, Integer folderId, Integer ownerId, String fileType, MultipartFile file) throws IOException;
    File downloadFile(Integer fileId) throws IOException;
    public void deleteFile(Integer fileId);
    // File_ retrieveFile(Integer fileId);
}
