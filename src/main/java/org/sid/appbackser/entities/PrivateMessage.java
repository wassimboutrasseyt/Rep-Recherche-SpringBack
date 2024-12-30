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
public class PrivateMessage {

    @Id
    private String id; // Unique identifier for the private message

    private Integer senderId;  // Account ID of the sender
    private Integer receiverId; // Account ID of the receiver

    private Integer projectId;  // Project ID for this private message

    private String content;    // Message content

    @Field(targetType = FieldType.STRING)

    private Instant timestamp = Instant.now(); // Time the message was sent

    private MessageType type;  // Type of message (text, image, etc.)

    @DBRef
    private PrivateConversation conversation; // Reference to the conversation
}
