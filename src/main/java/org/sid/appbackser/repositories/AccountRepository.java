package org.sid.appbackser.repositories;

import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByEmail(String email);

	void deleteById(Integer accountId);

	Optional<Account> findById(Integer accountId);
	}
