package org.sid.appbackser.entities;

import org.sid.appbackser.enums.GroupType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

import java.time.LocalDateTime;

@Document(collection = "messages")
@Data
public class Message {

    @Id
    private String id;

    private User sender; // The user sending the message

    private Groupe group; // The group in which the message was sent (null for private messages)

    private User receiver; // The receiver of the private message (null for group messages)

    private String content; // The message content

    private LocalDateTime timestamp; // Timestamp of the message

    private GroupType groupType; // Enum to differentiate group type: Admin, Default, Guest, Specific

    
}
