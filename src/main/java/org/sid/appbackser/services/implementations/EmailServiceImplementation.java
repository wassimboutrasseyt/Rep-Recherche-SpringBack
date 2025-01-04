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
public class EmailServiceImplementation implements EmailService{

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}") // L'adresse e-mail de l'expéditeur
    private String fromEmail;

    @Autowired
    AccountService accountService;

    @Override
    public void notifyAdmins(Proposition propositions) {
         try {
            // Préparer le contenu de l'e-mail
            String subject = "Notification : Nouvelles demandes de propositions soumises";
            String body = buildEmailTemplate(propositions);

            // Créer un objet MimeMessage
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Configurer les destinataires, l'objet et le contenu
            helper.setFrom(fromEmail);
            helper.setTo(getAdminEmails()); // Liste des e-mails des administrateurs
            helper.setSubject(subject);
            helper.setText(body, true); // true pour HTML

            // Envoyer l'e-mail
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'envoi de la notification : " + e.getMessage(), e);
        }    
    }

    @Override
    public String [] getAdminEmails() {
        List<Account> adminAccount = accountService.getAdmisAccount();
        List<String> adminEmails = new ArrayList<>();
        for (Account account : adminAccount) {
            adminEmails.add(account.getEmail());
        }

        return adminEmails.toArray(new String[0]);
    }

    @Override
    public String buildEmailTemplate(Proposition proposition) {
        
        // Construire le contenu de l'e-mail

        StringBuilder builder = new StringBuilder();
        builder.append("<html><body>");
        builder.append("<h1>Nouvelles demandes de propositions</h1>");
        builder.append("<p>Voici la liste des nouvelles propositions soumises :</p>");
        builder.append("<table border='1' style='width:100%;border-collapse:collapse;'>");
        builder.append("<tr><th>ID</th><th>Nom du projet</th><th>Description</th><th>Date de soumission</th></tr>");

        
            builder.append("<tr>");
            builder.append("<td>").append(proposition.getId()).append("</td>");
            builder.append("<td>").append(proposition.getLongName()).append("</td>");
            builder.append("<td>").append(proposition.getDescription()).append("</td>");
            builder.append("<td>").append(proposition.getVisibility()).append("</td>");
            builder.append("</tr>");
        

        builder.append("</table>");
        builder.append("<p>Cordialement,</p>");
        builder.append("<p>L'équipe de la plateforme</p>");
        builder.append("</body></html>");
        return builder.toString();
    }// TODO Auto-generated method stub
      
    }
    

