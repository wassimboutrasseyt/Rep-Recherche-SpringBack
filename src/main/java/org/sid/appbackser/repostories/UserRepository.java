package org.sid.appbackser.repostories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.sid.appbackser.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

	User findById(Integer id);
}
