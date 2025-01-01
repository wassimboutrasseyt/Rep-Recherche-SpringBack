package org.sid.appbackser.services;

import java.util.Collection;
import java.util.Collections;

import org.sid.appbackser.entities.Account;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators.In;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AccountDetails implements UserDetails{

	private Account account;
	
	
	public AccountDetails(Account account) {
		this.account=account;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	
		return Collections.singleton(new SimpleGrantedAuthority(account.getRole().name()));
	}

	public Integer getId() {
		// TODO Auto-generated method stub
		return this.account.getId();
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.account.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.account.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public Account getAccount() {
		return account;
	}

}
