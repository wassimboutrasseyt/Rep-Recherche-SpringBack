package org.sid.appbackser.services;

import org.sid.appbackser.dto.*;
import org.sid.appbackser.entities.User;

import java.util.List;

public interface UserService {

public User createUser(User user);
public String updateUser(User user);
public User getUser(Integer userId);
public String deleteUser(Integer userId);
public List<User> getAllUsers();
}
