package org.sid.appbackser.entities;

import java.time.Instant;
import java.util.List;

import org.sid.appbackser.enums.AccountStatus;
import org.sid.appbackser.enums.Roles;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private Instant createdAt = Instant.now();
    @JsonManagedReference
    @JsonIgnore
    @OneToMany(mappedBy="account")
    private List<GroupAccount> groups;


    @Enumerated(EnumType.STRING) 
    @Column(nullable = false)
    private Roles role;

    @Enumerated(EnumType.STRING) // This ensures the enum value is stored as a string
    @Column(nullable = false)
    private AccountStatus status;

    @ManyToOne
    @JsonBackReference
    private User user;
    
    @OneToMany(mappedBy = "account")
    @JsonIgnore
    private List<Proposition> propositions;

	public String getRole() {
		return this.role.name();
	}
    public void setRole(Roles role) {
        this.role = role;
    }
	
    public void setEmail(String _email) {
        this.email = _email == null ? null : _email.toLowerCase();
    }
}
