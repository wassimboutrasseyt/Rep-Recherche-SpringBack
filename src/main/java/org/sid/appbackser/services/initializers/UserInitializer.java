// package org.sid.appbackser.services.initializers;

// import java.sql.Date;
// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.List;

// import org.sid.appbackser.entities.Account;
// import org.sid.appbackser.entities.User;
// import org.sid.appbackser.entities.RessourceFolder.Calendrier;
// import org.sid.appbackser.entities.RessourceFolder.RessourcePerso;
// import org.sid.appbackser.entities.RessourceFolder.Tache;
// import org.sid.appbackser.enums.AccountStatus;
// import org.sid.appbackser.enums.Roles;
// import org.sid.appbackser.repositories.AccountRepository;
// import org.sid.appbackser.repositories.CalendrierRepository;

// import org.sid.appbackser.repositories.RessourcePersoRepository;
// import org.sid.appbackser.repositories.TacheRepository;
// import org.sid.appbackser.repositories.UserRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.stereotype.Service;

// import jakarta.annotation.PostConstruct;

// @Service
// public class UserInitializer {
//     @Autowired  
//     private AccountRepository accountRepository;
//     @Autowired  
//     private UserRepository userRepository;
//     @Autowired  
//     private TacheRepository tacheRepository;
//     @Autowired  
//     private CalendrierRepository calendrierRepository;
//     @Autowired
//     private RessourcePersoRepository ressourcePersoRepository;

    
//     private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);

//     @PostConstruct
//     public void init() {

//         User user5 = new User();
//             user5.setFirstName("test");
//             user5.setLastName("test");
//             user5.setPhone("");
//             user5.setDob(new Date(System.currentTimeMillis())); // Set to the current date
//             user5.setProffession("");
//             userRepository.save(user5);
            

//             Account admin5 = new Account();
//             admin5.setEmail("test@gmail.com");
//             admin5.setPassword(encoder.encode("test"));
//             admin5.setRole(Roles.REGISTRED_USER);
//             admin5.setUser(user5);
//             admin5.setStatus(AccountStatus.ACTIVE);
//             admin5 =accountRepository.save(admin5); 

//             Calendrier calendar = new Calendrier();
//             calendar.setTaches(new ArrayList<>());

//             Tache kickoffMeeting = new Tache();
//             kickoffMeeting.setNom("Project Kickoff Meeting");
//             kickoffMeeting.setDescription(" loremloremlorem lorem lorem lorem loremlorem lorem lorem");
//             kickoffMeeting.setDateDebut(LocalDateTime.of(2025, 1, 12, 10, 0));
//             kickoffMeeting.setDateFin(LocalDateTime.of(2025, 1, 12, 11, 0));
//             kickoffMeeting.setCalendrier(calendar);
//            // kickoffMeeting = tacheRepository.save(kickoffMeeting);

//             Tache codeReview = new Tache();
//             codeReview.setNom("Code Review Session");
//             codeReview.setDescription("loremloremlorem lorem lorem lorem loremlorem lorem lorem");
//             codeReview.setDateDebut(LocalDateTime.of(2025, 1, 13, 14, 0));
//             codeReview.setDateFin(LocalDateTime.of(2025, 1, 13, 15, 30));
//             codeReview.setCalendrier(calendar);
//             //codeReview=tacheRepository.save(codeReview); 
            
//             Tache quarterlyReport = new Tache();
//             quarterlyReport.setNom("Submit Quarterly Report");
//             quarterlyReport.setDescription("    quarterlyReport quarterlyReport quarterlyReport quarterlyReport quarterlyReportquarterlyReport");
//             quarterlyReport.setDateDebut(LocalDateTime.of(2025, 1, 15, 9, 0));
//             quarterlyReport.setDateFin(LocalDateTime.of(2025, 1, 15, 17, 0));
//             quarterlyReport.setCalendrier(calendar);
//             //quarterlyReport=tacheRepository.save(quarterlyReport);

//             Tache teamBuilding = new Tache();
//             teamBuilding.setNom("Team Building Activity");
//             teamBuilding.setDescription("teamBuilding teamBuilding teamBuildingteamBuildingteamBuildingv teamBuilding teamBuilding");
//             teamBuilding.setDateDebut(LocalDateTime.of(2025, 1, 16, 13, 0));
//             teamBuilding.setDateFin(LocalDateTime.of(2025, 1, 16, 16, 0));
//             teamBuilding.setCalendrier(calendar);
//             //teamBuilding=tacheRepository.save(teamBuilding);

//             List<Tache> taches = new ArrayList<>();
//             taches.add(kickoffMeeting);
//             taches.add(codeReview);
//             taches.add(quarterlyReport);
//             taches.add(teamBuilding);

//             calendar.getTaches().addAll(taches);

//             RessourcePerso ressourcePerso = new RessourcePerso();
//             ressourcePerso.setAccount(admin5);
//             ressourcePerso.setCalendrier(calendar);
//             ressourcePersoRepository.save(ressourcePerso);


        
//         }
//     }

