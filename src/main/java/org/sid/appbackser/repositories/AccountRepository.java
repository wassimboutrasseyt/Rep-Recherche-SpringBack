package org.sid.appbackser.repositories;

import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.User;
import org.sid.appbackser.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;


public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByEmail(String email);
	List<Account> findByRole(Roles role);

	void deleteById(Integer accountId);

	Optional<Account> findById(Integer accountId);
	boolean existsByRole(Roles role);
	List<Account> findByRole(String role);
}
