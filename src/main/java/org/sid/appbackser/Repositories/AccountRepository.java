package org.sid.appbackser.Repositories;

import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.RoleTypes;
import org.sid.appbackser.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findFirstByMail(String mail);
    List<String> findByRole(RoleTypes roleTypes);
}
