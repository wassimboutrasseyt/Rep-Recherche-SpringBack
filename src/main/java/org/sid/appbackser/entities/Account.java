package org.sid.appbackser.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

import org.sid.appbackser.enums.Roles;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    @OneToMany(mappedBy = "account")
    private List<GroupAccount> groups;


    @Enumerated(EnumType.STRING) 
    @Column(nullable = false)
    private Roles role;

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
	
}
