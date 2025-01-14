package org.sid.appbackser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardAdminDTO {
    private Long nbAccounts;
    private Long nbProjects;
    private Long nbPendingPeopositions;
}
