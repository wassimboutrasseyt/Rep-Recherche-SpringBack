package org.sid.appbackser.services;

import org.sid.appbackser.dto.*;
import org.sid.appbackser.entities.Project;
import org.sid.appbackser.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface UserService {

public User createUser(User user);
public String updateUser(User user);
public User getUser(Integer userId);
public String deleteUser(Integer userId);
public Page<User> findAllUsers(Pageable pageable);
}
