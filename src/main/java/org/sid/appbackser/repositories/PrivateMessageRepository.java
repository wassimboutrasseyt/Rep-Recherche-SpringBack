package org.sid.appbackser.repositories;

import java.util.List;

import org.sid.appbackser.entities.PrivateMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivateMessageRepository extends MongoRepository<PrivateMessage, String>{
    List<PrivateMessage> findBySenderId(Integer senderId);
    List<PrivateMessage> findByReceiverId(Integer receiverId);
    List<PrivateMessage> findByConversationId(String conversationId);

}
