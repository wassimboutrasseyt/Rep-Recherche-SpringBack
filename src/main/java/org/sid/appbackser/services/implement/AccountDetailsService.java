package org.sid.appbackser.services.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.sid.appbackser.entity.Account;
import org.sid.appbackser.entity.AccountDetails;
import org.sid.appbackser.repostories.AccountRepository;

@Service
public class AccountDetailsService implements UserDetailsService{
	
	@Autowired
	AccountRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Account account=repo.findByEmail(email);
		if(account==null) {
			throw new UsernameNotFoundException("user not found");
		}
		return new AccountDetails(account);
	}

}
