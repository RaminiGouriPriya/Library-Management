package com.epam.library.proxy;

import java.util.List;

import javax.validation.Valid;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.epam.library.dto.UserDTO;

@FeignClient(name = "user-service"/*, fallback = UserProxyImpl.class*/)
@LoadBalancerClient(name = "user-service"/*, configuration = UserProxyImpl.class*/)
public interface UserProxy {
	
	@PostMapping("/users")
	public UserDTO createUser(@RequestBody @Valid UserDTO userDTO);
	
	@GetMapping("/users")
	public List<UserDTO> getUsers();
	
	@GetMapping("/users/{username}")
	public UserDTO getByUserName(@PathVariable ("username") String userName);
	
	@PutMapping("/users/{username}")
	public UserDTO updateUser(@PathVariable ("username") String userName,@RequestBody @Valid UserDTO userDTO) ;
	
	@DeleteMapping("/users/{username}")
	public UserDTO deleteUser(@PathVariable ("username") String userName) ;
	
}
