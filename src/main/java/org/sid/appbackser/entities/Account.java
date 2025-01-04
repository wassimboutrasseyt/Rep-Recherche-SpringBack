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
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String password;
    private Instant createdAt = Instant.now();

    // Many accounts can belong to many groups with a specific role
    // @JoinTable(
    //     name = "group_account",
    //     joinColumns = @JoinColumn(name = "account_id"),
    //     inverseJoinColumns = @JoinColumn(name = "group_id")
    // )
    @OneToMany
    private List<GroupAccount> groups;


    @Enumerated(EnumType.STRING) // This ensures the enum value is stored as a string
    @Column(nullable = false)
    private Roles role;

    @ManyToOne
    @JsonBackReference
    private User user;
    
    @OneToMany(mappedBy="account")
    @JsonIgnore
    private List<Proposition> propositions;

    // Getters and Setters
    // public Integer getId() {
    //     return id;
    // }

    // public void setId(Integer id) {
    //     this.id = id;
    // }

    // public String getEmail() {
    //     return email;
    // }

    // public void setEmail(String email) {
    //     this.email = email;
    // }

    // public String getPassword() {
    //     return password;
    // }

    // public void setPassword(String password) {
    //     this.password = password;
    // }

    // public List<Group> getGroups() {
    //     return groups;
    // }

    // public void setGroups(List<Group> groups) {
    //     this.groups = groups;
    // }

    // public User getUser() {
    //     return user;
    // }

    // public void setUser(User user) {
    //     this.user = user;
    // }

	public String getRole() {
		return this.role.name();
	}
    public void setRole(Roles role) {
        this.role = role;
    }
	
}
