package org.sid.appbackser.services;

import org.sid.appbackser.entities.Project;
import org.sid.appbackser.entities.RessourceFolder.Folder;

public interface FolderService {
    Folder createFolder(Integer depotId, String name, Integer parentFolderId, Integer owner);
    void deleteFolder(Project project, Integer folderId, Integer deleter);
}
