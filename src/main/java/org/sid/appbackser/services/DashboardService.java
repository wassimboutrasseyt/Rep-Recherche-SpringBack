package org.sid.appbackser.services;

import java.security.Principal;

import org.sid.appbackser.dto.DashboardAdminDTO;
import org.sid.appbackser.dto.DashboardRUserDTO;
import org.sid.appbackser.entities.Account;

public interface DashboardService {
    DashboardAdminDTO getDashboardAdminInfos();
    DashboardRUserDTO getDashboardRUsersInfos(Account authUser);
}
