package org.sid.appbackser.repositories;

import java.util.List;
import org.sid.appbackser.entities.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MongoRepository<Message, String>{
    // Custom query method to find messages by chatGroupId
    List<Message> findByChatGroupId(String chatGroupId);

    // Custom query method to find messages by senderId
    List<Message> findBySenderId(Integer senderId);
    
    
}
