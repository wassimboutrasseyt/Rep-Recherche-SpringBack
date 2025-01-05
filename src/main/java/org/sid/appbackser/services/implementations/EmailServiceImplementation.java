package org.sid.appbackser.services.implementations;

import java.util.ArrayList;
import java.util.List;

import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.Proposition;
import org.sid.appbackser.services.AccountService;
import org.sid.appbackser.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;


@Service
public class EmailServiceImplementation implements EmailService {

    private final JavaMailSender mailSender;
    private final AccountService accountService;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailServiceImplementation(JavaMailSender mailSender, AccountService accountService) {
        this.mailSender = mailSender;
        this.accountService = accountService;
    }

    @Override
    public void notifyAdmins(Proposition proposition) {
        try {
            String subject = "Notification : Nouvelles demandes de propositions soumises";
            String body = buildEmailTemplate(proposition);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            String[] adminEmails = getAdminEmails();
            if (adminEmails.length == 0) {
                throw new IllegalStateException("No admin emails found for notification.");
            }

            helper.setFrom(fromEmail);
            helper.setTo(adminEmails);
            helper.setSubject(subject);
            helper.setText(body, true);

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Error while sending notification: " + e.getMessage(), e);
        }
    }

    @Override
    public String[] getAdminEmails() {
        List<Account> adminAccounts = accountService.getAdmisAccount();
        if (adminAccounts.isEmpty()) {
            throw new IllegalStateException("No admin accounts found.");
        }
        return adminAccounts.stream()
                .map(Account::getEmail)
                .toArray(String[]::new);
    }

    @Override
    public String buildEmailTemplate(Proposition proposition) {
        // Construct the email content
        StringBuilder builder = new StringBuilder();
        builder.append("<html><head>");
        builder.append("<style>");
        builder.append("body { font-family: Arial, sans-serif; background-color: #f9f9f9; color: #333; }");
        builder.append("h1 { color: #344CB7; }"); // Blue header
        builder.append("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
        builder.append("th, td { padding: 10px; text-align: left; border: 1px solid #ddd; }");
        builder.append("th { background-color: #344CB7; color: white; }"); // Blue table header
        builder.append("tr:nth-child(even) { background-color: #FADA7A; }"); // Yellow alternate rows
        builder.append(".button { display: inline-block; padding: 10px 15px; font-size: 16px; color: #344CB7; background-color: #FADA7A; text-decoration: none; border-radius: 5px; margin-top: 20px; }"); // Yellow button
        builder.append(".button:hover { background-color: #344CB7; color: white; }"); // Hover effect: Blue background, white text
        builder.append("</style>");
        builder.append("</head><body>");
        builder.append("<h1>Nouvelle Proposition Soumise</h1>");
        builder.append("<p>Bonjour,</p>");
        builder.append("<p>L'utilisateur <strong>")
            .append(proposition.getAccount().getUser().getLastName() + " " + proposition.getAccount().getUser().getFirstName())
            .append("</strong> a soumis une nouvelle proposition pour le projet <strong>")
            .append(proposition.getLongName())
            .append("</strong>. Cette proposition est en attente d'approbation.</p>");
        builder.append("<p>Voici les détails de la proposition :</p>");
        builder.append("<table>");
        builder.append("<tr><th>ID</th><th>Nom Complet du projet</th><th>Nom du projet</th><th>Type</th><th>Description</th><th>Date de soumission</th></tr>");
        builder.append("<tr>");
        builder.append("<td>").append(proposition.getId()).append("</td>");
        builder.append("<td>").append(proposition.getLongName()).append("</td>");
        builder.append("<td>").append(proposition.getShortName()).append("</td>");
        builder.append("<td>").append(proposition.getType()).append("</td>");
        builder.append("<td>").append(proposition.getDescription()).append("</td>");
        builder.append("<td>").append(proposition.getCreatedAt()).append("</td>");
        builder.append("</tr>");
        builder.append("</table>");
        builder.append("<br/>");
        builder.append("<p>Pour approuver ou rejeter cette proposition, veuillez cliquer sur le bouton ci-dessous :</p>");
        builder.append("<a href='").append("http://localhost:6541/admin/Proposition").append("' class='button'>Approuver la Proposition</a>");
        builder.append("<br/><br/>");
        builder.append("<p>Cordialement,</p>");
        builder.append("<p>Next Generation Research</p>");
        builder.append("</body></html>");
        return builder.toString();
    }


}

