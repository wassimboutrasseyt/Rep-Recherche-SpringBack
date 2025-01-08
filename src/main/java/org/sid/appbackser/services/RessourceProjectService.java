package org.sid.appbackser.services;

import org.sid.appbackser.entities.Project;
import org.sid.appbackser.entities.RessourceFolder.RessourceProject;

public interface RessourceProjectService {
    RessourceProject createRessourceProject(Project project);
}
