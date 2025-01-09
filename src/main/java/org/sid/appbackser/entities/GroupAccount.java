package org.sid.appbackser.entities;

import org.sid.appbackser.enums.RolesPerGroup;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor

public class GroupAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // The group the account belongs to
    @JsonBackReference
    @ManyToOne
    private Group group;

    // The account that belongs to the group
    @JsonBackReference
    @ManyToOne
    private Account account;

    @Enumerated(EnumType.STRING) 
    @Column(nullable = false)
    private RolesPerGroup role;


}
