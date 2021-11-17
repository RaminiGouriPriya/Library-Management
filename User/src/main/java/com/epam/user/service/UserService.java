package com.epam.user.service;

import java.util.List;

import com.epam.user.dto.UserDTO;
import com.epam.user.exception.UserNameDoesNotExistException;

public interface UserService {
	UserDTO createUser(UserDTO userDTO);
	List<UserDTO> getUsers();
	UserDTO getUserBasedOnUserName(String userName) throws UserNameDoesNotExistException;
	UserDTO updateUser(String userName, UserDTO userDTO) throws UserNameDoesNotExistException;
	UserDTO deleteUser(String userName) throws UserNameDoesNotExistException;
}
