package com.epam.user.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.user.constant.Constants;
import com.epam.user.dto.UserDTO;
import com.epam.user.exception.UserNameDoesNotExistException;
import com.epam.user.service.UserService;

@RestController
public class UserRestController {
	@Autowired
	UserService userService;
	
	@Autowired
	Environment environment;
	
	@PostMapping("/users")
	public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO){
		UserDTO responseUserDTO = userService.createUser(userDTO);
		responseUserDTO.setPort(environment.getProperty(Constants.LOCAL_SERVER_PORT));
		return new ResponseEntity<UserDTO>(responseUserDTO,HttpStatus.CREATED);
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<UserDTO>> getUsers(){
		return new ResponseEntity<List<UserDTO>>(userService.getUsers(),HttpStatus.OK);
	}
	
	@GetMapping("/users/{username}")
	public ResponseEntity<UserDTO> getBasedOnUserName(@PathVariable ("username") String userName) throws UserNameDoesNotExistException {
		UserDTO responseUserDTO = userService.getUserBasedOnUserName(userName);
		responseUserDTO.setPort(environment.getProperty(Constants.LOCAL_SERVER_PORT));
		return new ResponseEntity<UserDTO>(responseUserDTO,HttpStatus.OK);
	}
	
	@PutMapping("/users/{username}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable ("username") String userName,@RequestBody @Valid UserDTO userDTO) throws UserNameDoesNotExistException{
		UserDTO responseUserDTO = userService.updateUser(userName, userDTO);
		responseUserDTO.setPort(environment.getProperty(Constants.LOCAL_SERVER_PORT));
		return new ResponseEntity<UserDTO>(responseUserDTO,HttpStatus.OK);
	}
	
	@DeleteMapping("/users/{username}")
	public ResponseEntity<UserDTO> deleteUser(@PathVariable ("username") String userName) throws UserNameDoesNotExistException{
		UserDTO responseUserDTO = userService.deleteUser(userName);
		responseUserDTO.setPort(environment.getProperty(Constants.LOCAL_SERVER_PORT));
		return new ResponseEntity<UserDTO>(responseUserDTO,HttpStatus.OK);
	}
	
}
