package org.sid.appbackser.repositories;

import java.util.List;

import org.sid.appbackser.entities.ChatGroup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatGroupRepository extends MongoRepository<ChatGroup, String>{
    List<ChatGroup> findByProjectId(Integer projectId);
}
