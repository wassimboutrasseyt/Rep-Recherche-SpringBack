package org.sid.appbackser.repositories;

import org.sid.appbackser.entities.Account;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findFirstByMail(String mail);
  /*  List<String> findByRole(RoleTypes roleTypes);*/
}
