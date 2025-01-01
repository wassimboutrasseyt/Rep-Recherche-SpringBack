package org.sid.appbackser.repositories;

import org.sid.appbackser.entities.GroupAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface GroupAccountRepository extends JpaRepository<GroupAccount, Integer> {

    // Custom method to delete GroupAccount by accountId and groupId
    void deleteByAccountIdAndGroupId(Integer accountId, Integer groupId);
}
