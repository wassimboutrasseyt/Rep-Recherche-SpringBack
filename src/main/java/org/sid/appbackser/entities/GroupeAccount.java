package org.sid.appbackser.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupeAccount {
    @Id
    private Long id;
    @ManyToOne
    private Groupe groupe;
    
    @ManyToOne
    private Account account;
    
    @ManyToOne
    private Role role;

}
