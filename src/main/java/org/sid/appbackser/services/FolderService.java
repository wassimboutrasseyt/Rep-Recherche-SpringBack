package org.sid.appbackser.services;

import org.sid.appbackser.entities.RessourceFolder.Folder;

public interface FolderService {
    Folder createFolder(Integer depotId, String name, Integer parentFolderId);
    void deleteFolder(Integer folderId);
}
