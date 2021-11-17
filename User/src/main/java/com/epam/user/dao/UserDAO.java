package com.epam.user.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.user.model.User;

@Repository
public interface UserDAO extends JpaRepository<User,String>{
	public List<User> findByUserName(String userName);
}
