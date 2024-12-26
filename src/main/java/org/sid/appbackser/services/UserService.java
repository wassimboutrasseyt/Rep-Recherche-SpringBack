package org.sid.appbackser.services;

import org.sid.appbackser.dto.*; 
import java.util.List;

import org.sid.appbackser.entity.User;

public interface UserService {

public User createUser(User user);
public String updateUser(User user);
public User getUser(String userId);
public String deleteUser(String userId);
public List<User> getAllUsers();
}
