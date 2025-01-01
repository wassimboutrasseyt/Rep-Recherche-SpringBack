package org.sid.appbackser.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

import org.sid.appbackser.enums.ProjectType;
import org.sid.appbackser.enums.ProjectVisibility;
import org.sid.appbackser.enums.PropositionStatus;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Proposition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String shortName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectType type;

    @Column(nullable = false)
    private String theme;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProjectVisibility visibility;

    private String licenseName; 

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PropositionStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proposition that = (Proposition) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
