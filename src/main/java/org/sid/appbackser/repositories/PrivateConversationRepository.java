package org.sid.appbackser.repositories;

import java.util.List;
import java.util.Optional;

import org.sid.appbackser.entities.PrivateConversation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PrivateConversationRepository extends MongoRepository<PrivateConversation, String> {
    // Custom query to find a conversation by participants
    Optional<PrivateConversation> findBySenderIdAndReceiverId(Integer senderId, Integer receiverId);

    // Find conversations for a user
    List<PrivateConversation> findBySenderIdOrReceiverId(Integer senderId, Integer receiverId);
}
