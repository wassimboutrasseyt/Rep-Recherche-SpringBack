package org.sid.appbackser.services;

import org.sid.appbackser.entities.RessourceFolder.Depot;
import org.sid.appbackser.enums.DepotType;

public interface DepotService {
    Depot createDepot(String email, DepotType type);
    Depot getDepotById(Integer depotId);
}
