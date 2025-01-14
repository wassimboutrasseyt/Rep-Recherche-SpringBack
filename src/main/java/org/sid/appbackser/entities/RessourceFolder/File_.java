package org.sid.appbackser.entities.RessourceFolder;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class File_ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long size; // File size in bytes

    @Column(nullable = false)
    private String localPath; // Full path to the file on the local file system

    private String type; // e.g., "text/plain", "image/png"

    private Instant createdAt = Instant.now();

    @ManyToOne
    private Folder folder;

    @ManyToOne
    @JoinColumn(name = "depot_id", nullable = true) // A file can belong directly to a depot
    @JsonBackReference
    private Depot depot;

}