package org.sid.appbackser.services;

import org.sid.appbackser.entities.RessourceFolder.Depot;
import org.sid.appbackser.entities.RessourceFolder.Folder;

public interface FolderService {
    Folder createFolder( Depot depot, String name, Folder parentFolder);
}
