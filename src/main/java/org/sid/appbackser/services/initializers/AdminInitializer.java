package org.sid.appbackser.services.initializers;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.sid.appbackser.entities.User;
import org.sid.appbackser.entities.RessourceFolder.Calendrier;
import org.sid.appbackser.entities.RessourceFolder.RessourcePerso;
import org.sid.appbackser.entities.RessourceFolder.Tache;
import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.ChatGroup;
import org.sid.appbackser.enums.AccountStatus;
import org.sid.appbackser.enums.ChatGroupType;
import org.sid.appbackser.enums.Roles;
import org.sid.appbackser.repositories.AccountRepository;
import org.sid.appbackser.repositories.CalendrierRepository;
import org.sid.appbackser.repositories.ChatGroupRepository;
import org.sid.appbackser.repositories.RessourcePersoRepository;
import org.sid.appbackser.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service

public class AdminInitializer {

    @Autowired  
    private AccountRepository accountRepository;
    @Autowired  
    private UserRepository userRepository;
    @Autowired
    private RessourcePersoRepository ressourcePersoRepository;
    @Autowired
    private CalendrierRepository calendrierRepository;


    @Autowired
    private ChatGroupRepository chatGroupRepository;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

    @PostConstruct
    public void init() {
        Roles role = Roles.ADMIN;
        boolean adminExists = accountRepository.existsByRole(role);
        if (!adminExists) {
            // Create a single user entity to associate with multiple accounts
            User user1 = new User();
            user1.setFirstName("");
            user1.setLastName("");
            user1.setPhone("");
            user1.setDob(new Date(System.currentTimeMillis())); // Set to the current date
            user1.setProffession("");
            userRepository.save(user1);
    
            // Create and save admin accounts
            Account admin1 = new Account();
            admin1.setEmail("Boutrasseytwassim@gmail.com");
            admin1.setPassword(encoder.encode("admin-ngr"));
            admin1.setRole(role);
            admin1.setUser(user1);
            admin1.setStatus(AccountStatus.ACTIVE);
            accountRepository.save(admin1);

            RessourcePerso ressourcePerso = new RessourcePerso();
            Calendrier calendrier = new Calendrier();
            calendrier.setTaches(new ArrayList<>());
            //calendrierRepository.save(calendrier);
            ressourcePerso.setAccount(admin1);
            ressourcePerso.setCalendrier(calendrier);
            
            ressourcePersoRepository.save(ressourcePerso);
    
            User user2 = new User();
            user2.setFirstName("");
            user2.setLastName("");
            user2.setPhone("");
            user2.setDob(new Date(System.currentTimeMillis())); // Set to the current date
            user2.setProffession("");
            userRepository.save(user2);
    
            Account admin2 = new Account();
            admin2.setEmail("amranihassan.am@gmail.com");
            admin2.setPassword(encoder.encode("admin-ngr"));
            admin2.setRole(role);
            admin2.setUser(user2);
            admin2.setStatus(AccountStatus.ACTIVE);
            accountRepository.save(admin2);

            RessourcePerso ressourcePerso1 = new RessourcePerso();
            Calendrier calendrier1 = new Calendrier();
            calendrier1.setTaches(new ArrayList<>());
            ressourcePerso.setAccount(admin2);
            ressourcePerso.setCalendrier(calendrier1);
            ressourcePersoRepository.save(ressourcePerso1);
            
    
            User user3 = new User();
            user3.setFirstName("");
            user3.setLastName("");
            user3.setPhone("");
            user3.setDob(new Date(System.currentTimeMillis())); // Set to the current date
            user3.setProffession("");
            userRepository.save(user3);
    

            Account admin3 = new Account();
            admin3.setEmail("aymanaomaripro@gmail.com");
            admin3.setPassword(encoder.encode("admin-ngr"));
            admin3.setRole(role);
            admin3.setUser(user3);
            admin3.setStatus(AccountStatus.ACTIVE);
            accountRepository.save(admin3);

            RessourcePerso ressourcePerso2 = new RessourcePerso();
            Calendrier calendrier2 = new Calendrier();
            calendrier2.setTaches(new ArrayList<>());
            ressourcePerso.setAccount(admin3);
            ressourcePerso.setCalendrier(calendrier2);
            ressourcePersoRepository.save(ressourcePerso2);
    
            

            User user5 = new User();
            user5.setFirstName("test");
            user5.setLastName("test");
            user5.setPhone("");
            user5.setDob(new Date(System.currentTimeMillis())); // Set to the current date
            user5.setProffession("");
            userRepository.save(user5);
            

            Account admin5 = new Account();
            admin5.setEmail("test@gmail.com");
            admin5.setPassword(encoder.encode("test"));
            admin5.setRole(Roles.REGISTRED_USER);
            admin5.setUser(user5);
            admin5.setStatus(AccountStatus.ACTIVE);
            admin5 =accountRepository.save(admin5); 

            Calendrier calendar = new Calendrier();
            calendar.setTaches(new ArrayList<>());

            Tache kickoffMeeting = new Tache();
            kickoffMeeting.setNom("Project Kickoff Meeting");
            kickoffMeeting.setDescription(" loremloremlorem lorem lorem lorem loremlorem lorem lorem");
            kickoffMeeting.setDateDebut(LocalDateTime.of(2025, 1, 12, 10, 0));
            kickoffMeeting.setDateFin(LocalDateTime.of(2025, 1, 12, 11, 0));
            kickoffMeeting.setCalendrier(calendar);
           // kickoffMeeting = tacheRepository.save(kickoffMeeting);

            Tache codeReview = new Tache();
            codeReview.setNom("Code Review Session");
            codeReview.setDescription("loremloremlorem lorem lorem lorem loremlorem lorem lorem");
            codeReview.setDateDebut(LocalDateTime.of(2025, 1, 13, 14, 0));
            codeReview.setDateFin(LocalDateTime.of(2025, 1, 13, 15, 30));
            codeReview.setCalendrier(calendar);
            //codeReview=tacheRepository.save(codeReview); 
            
            Tache quarterlyReport = new Tache();
            quarterlyReport.setNom("Submit Quarterly Report");
            quarterlyReport.setDescription("    quarterlyReport quarterlyReport quarterlyReport quarterlyReport quarterlyReportquarterlyReport");
            quarterlyReport.setDateDebut(LocalDateTime.of(2025, 1, 15, 9, 0));
            quarterlyReport.setDateFin(LocalDateTime.of(2025, 1, 15, 17, 0));
            quarterlyReport.setCalendrier(calendar);
            //quarterlyReport=tacheRepository.save(quarterlyReport);

            Tache teamBuilding = new Tache();
            teamBuilding.setNom("Team Building Activity");
            teamBuilding.setDescription("teamBuilding teamBuilding teamBuildingteamBuildingteamBuildingv teamBuilding teamBuilding");
            teamBuilding.setDateDebut(LocalDateTime.of(2025, 1, 16, 13, 0));
            teamBuilding.setDateFin(LocalDateTime.of(2025, 1, 16, 16, 0));
            teamBuilding.setCalendrier(calendar);
            //teamBuilding=tacheRepository.save(teamBuilding);

            List<Tache> taches = new ArrayList<>();
            taches.add(kickoffMeeting);
            taches.add(codeReview);
            taches.add(quarterlyReport);
            taches.add(teamBuilding);

            calendar.getTaches().addAll(taches);

            RessourcePerso ressourcePerso5 = new RessourcePerso();
            ressourcePerso5.setAccount(admin5);
            ressourcePerso5.setCalendrier(calendar);
            ressourcePersoRepository.save(ressourcePerso5);
            
        }
    }
    
        }
