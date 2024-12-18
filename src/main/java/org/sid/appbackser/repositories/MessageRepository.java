package org.sid.appbackser.repositories;

import org.sid.appbackser.entities.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {

    List<Message> findByGroupId(Long groupId);

    List<Message> findByReceiverId(Long receiverId);

    List<Message> findBySenderId(Long senderId);
}
