package org.sid.appbackser.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.Instant;
import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String password;
    private Instant createdAt = Instant.now();

    // Many accounts can belong to many groups with a specific role
    @ManyToMany
    @JsonIgnore
    @JsonManagedReference
    @JoinTable(
        name = "group_account",
        joinColumns = @JoinColumn(name = "account_id"),
        inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private List<Group> groups;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    
    // Many accounts belong to one user
    @ManyToOne
    
    @JsonBackReference
    private User user;
    
    @OneToMany(mappedBy="account")
    @JsonIgnore
    private List<Proposition> propositions;

	public String getRole() {
		return this.role.getRole().name();
	}

    public void setRole(Role role) {
        this.role = role;
    }

}
