package org.sid.appbackser.services.implementations;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.sid.appbackser.dto.UserAccountDTO;
import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.User;
import org.sid.appbackser.repositories.AccountRepository;
import org.sid.appbackser.repositories.UserRepository;
import org.sid.appbackser.services.UserService;


@Service

public class UserServicesImplement implements UserService{

	private static final Logger logger =  LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	UserRepository userRepository ;
	
	@Autowired
	AccountRepository accountRepository;
	
	public UserServicesImplement(UserRepository userRepo) {
		this.userRepository=userRepo;
	}
	
	

	@Override
	public User createUser(User user) {
	    
		// Save the user to the database
	    User userr=this.userRepository.save(user);
	    
	    logger.info("User and account created successfully for: {}", user.getFirstName());

	    return userr;
	}
	
	

	@Override
	public String updateUser(User user) {
		this.userRepository.save(user);
		return "user Updated succesfully";
	}

	@Override
	public User getUser(Integer userId) {
		return this.userRepository.findById(userId).get();
	}

	@Override
	public String deleteUser(Integer userId) {
		this.userRepository.deleteById(userId);
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return null;
	}

}