package org.sid.appbackser.entities;

import java.time.Instant;
import java.util.List;

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
public class PrivateConversation {

    @Id
    private String id; // Unique identifier for the conversation

    private Integer senderId;  // User ID of the sender
    private Integer receiverId; // User ID of the receiver

    private Integer projectId;  // Optional: Project context for the conversation
    
    
    @Field(targetType = FieldType.STRING)

    private Instant createdAt = Instant.now(); // When the conversation was created

    @Field(targetType = FieldType.STRING)

    private Instant lastUpdatedAt; // Last activity in the conversation

    private List<String> messageIds; // List of message IDs (references to PrivateMessage)
}
