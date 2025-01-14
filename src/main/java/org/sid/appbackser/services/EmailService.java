package org.sid.appbackser.services;

import java.util.List;

import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.Project;
import org.sid.appbackser.entities.Proposition;

public interface EmailService {

    public void notifyAdmins(Proposition propositions);
    public String[] getAdminEmails();
    public String buildNewPropositionEmailTemplate(Proposition propositions);

    public void notifyUserAddedToProject(Project project, Integer newUserId, Integer adminId);
    public String buildUserAddedToProjectEmailTemplate(Project project, Account newUserAccount, Account addedBy);
} 