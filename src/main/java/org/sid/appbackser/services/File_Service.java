package org.sid.appbackser.services;

import java.io.IOException;

import org.sid.appbackser.entities.RessourceFolder.File_;
import org.springframework.web.multipart.MultipartFile;

public interface File_Service {
    File_ uploadFile(MultipartFile file, Long folderId) throws IOException;
    void deleteFile(Integer fileId);
    File_ retrieveFile(Integer fileId);
}
