package org.sid.appbackser.entities;

import java.time.Instant;

import org.sid.appbackser.enums.MessageType;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document()
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    
    @Id
    private String id;
    
    @DBRef
    private ChatGroup chatGroup;
    private Integer senderId;
    private String content;
    
    
    private Instant timestamp = Instant.now();

    private MessageType type;
}
