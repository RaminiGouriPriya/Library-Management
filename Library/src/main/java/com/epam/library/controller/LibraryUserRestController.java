package com.epam.library.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.library.dto.UserDTO;
import com.epam.library.proxy.UserProxy;

@RestController
@RequestMapping("library")
public class LibraryUserRestController {
	@Autowired
	UserProxy userProxy;
	
	@PostMapping("/users/{username}")
	public ResponseEntity<UserDTO> createUser(@PathVariable ("username") String userName,@RequestBody @Valid UserDTO userDTO){
		return new ResponseEntity<UserDTO>(userProxy.createUser(userDTO),HttpStatus.CREATED);
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<UserDTO>> getUsers() {
		return new ResponseEntity<List<UserDTO>>(userProxy.getUsers(), HttpStatus.OK);
	}
	
	@GetMapping("/users/{username}")
	public ResponseEntity<UserDTO> getByUserName(@PathVariable ("username") String userName){
		return new ResponseEntity<UserDTO>(userProxy.getByUserName(userName),HttpStatus.OK);
	}
	
	@PutMapping("/users/{username}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable ("username") String userName,@RequestBody @Valid UserDTO userDTO){
		return new ResponseEntity<UserDTO>(userProxy.updateUser(userName, userDTO),HttpStatus.OK);
	}
	
	@DeleteMapping("/users/{username}")
	public ResponseEntity<UserDTO> deleteUser(@PathVariable ("username") String userName){
		return new ResponseEntity<UserDTO>(userProxy.deleteUser(userName),HttpStatus.OK);
	}	
}
