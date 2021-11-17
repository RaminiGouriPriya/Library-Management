package com.epam.user.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.user.constant.Constants;
import com.epam.user.dao.UserDAO;
import com.epam.user.dto.UserDTO;
import com.epam.user.exception.UserNameDoesNotExistException;
import com.epam.user.model.User;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserDAO userRepo;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public UserDTO createUser(UserDTO userDTO) {
		User user=modelMapper.map(userDTO, User.class);
		return modelMapper.map(userRepo.save(user), UserDTO.class);	
	}

	@Override
	public List<UserDTO> getUsers() {
		return modelMapper.map(userRepo.findAll(), new TypeToken<List<UserDTO>>() {}.getType());
	}

	@Override
	public UserDTO getUserBasedOnUserName(String userName) throws UserNameDoesNotExistException {
		User user = findUserByUserName(userName);
		return modelMapper.map(user, UserDTO.class);
	}

	@Override
	public UserDTO updateUser(String userName, UserDTO userDTO) throws UserNameDoesNotExistException {
		User user = findUserByUserName(userName);
		user.setEmail(userDTO.getEmail());
		user.setName(userDTO.getName());
		return modelMapper.map(userRepo.save(user), UserDTO.class);
	}

	@Override
	public UserDTO deleteUser(String userName) throws UserNameDoesNotExistException {
		User user = findUserByUserName(userName);
		System.out.println("=================================================================="+user.getUserName());
//		UserDTO userDTO = modelMapper.map(user, UserDTO.class);
		userRepo.delete(user);
		return modelMapper.map(user, UserDTO.class);
	}
	
	public User findUserByUserName(String userName) throws UserNameDoesNotExistException {
		if(userRepo.findByUserName(userName).isEmpty()) {
			throw new UserNameDoesNotExistException(Constants.THE_USERNAME_DOES_NOT_EXIST);
		}
		return userRepo.findByUserName(userName).get(0);
	}

}
