package org.sid.appbackser.services;

import java.util.List;

import org.sid.appbackser.entities.Proposition;

public interface EmailService {

    public void notifyAdmins(Proposition propositions);
    public String[] getAdminEmails();
    public String buildEmailTemplate(Proposition propositions);
} 