package org.sid.appbackser.repostories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.sid.appbackser.entity.Account;
import org.sid.appbackser.entity.User;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByEmail(String email);

	void deleteById(Integer accountId);

	Optional<Account> findById(Integer accountId);
	}
