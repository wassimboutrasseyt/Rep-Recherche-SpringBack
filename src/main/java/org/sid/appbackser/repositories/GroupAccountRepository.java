package org.sid.appbackser.repositories;

import java.util.List;

import org.sid.appbackser.entities.GroupAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface GroupAccountRepository extends JpaRepository<GroupAccount, Integer> {
    List<GroupAccount> findByAccountId(Integer accountId);
    void deleteByAccountIdAndGroupId(Integer accountId, Integer groupId);
}
