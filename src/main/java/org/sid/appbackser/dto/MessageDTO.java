package org.sid.appbackser.dto;

import java.time.Instant;

import org.sid.appbackser.entities.User;
import org.sid.appbackser.enums.MessageType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    private String id;
    private Integer senderId;
    private String senderFirstName;
    private String senderLastName;
    private String content;
    private Instant timestamp;
    private MessageType type;

}
