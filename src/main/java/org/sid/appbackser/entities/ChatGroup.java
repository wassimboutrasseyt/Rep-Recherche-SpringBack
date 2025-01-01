package org.sid.appbackser.entities;

import java.time.Instant;
import java.util.List;

import org.sid.appbackser.enums.ChatGroupType;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document()
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatGroup {

    @Id
    private String id;
    private Integer projectId;
    private String name;

    private ChatGroupType type;
    private List<Integer> members;

    @Field(targetType = FieldType.STRING)
    private Instant createdAt = Instant.now();
}
