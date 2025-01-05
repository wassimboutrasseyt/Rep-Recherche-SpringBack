package org.sid.appbackser.entities.RessourceFolder;

import java.time.LocalDateTime;
import java.util.List;
import org.sid.appbackser.entities.Account;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Tache {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nom;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
   // private List<Account> responsable;

}
