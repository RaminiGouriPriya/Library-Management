package com.epam.library.proxy;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.epam.library.constant.Constants;
import com.epam.library.dto.UserDTO;

@Service
public class UserProxyImpl implements UserProxy{



	@Override
	public UserDTO createUser(@Valid UserDTO userDTO) {
		UserDTO responseUserDTO = new UserDTO();
		responseUserDTO.setEmail(userDTO.getEmail());
		responseUserDTO.setName(userDTO.getName());
		responseUserDTO.setUserName(userDTO.getUserName());
		responseUserDTO.setPort(Constants.FROM_FALLBACK);
		return responseUserDTO;
	}

	@Override
	public List<UserDTO> getUsers() {
		List<UserDTO> userList = new ArrayList<>();
		UserDTO responseUserDTO = new UserDTO();
		responseUserDTO.setEmail(Constants.FROM_FALLBACK);
		responseUserDTO.setName(Constants.FROM_FALLBACK);
		responseUserDTO.setUserName(Constants.FROM_FALLBACK);
		responseUserDTO.setPort(Constants.FROM_FALLBACK);
		userList.add(responseUserDTO);
		return userList;
	}

	@Override
	public UserDTO getByUserName(String userName) {
		UserDTO responseUserDTO = new UserDTO();
		responseUserDTO.setEmail(Constants.FROM_FALLBACK);
		responseUserDTO.setName(Constants.FROM_FALLBACK);
		responseUserDTO.setUserName(Constants.FROM_FALLBACK);
		responseUserDTO.setPort(Constants.FROM_FALLBACK);
		return responseUserDTO;
	}

	@Override
	public UserDTO updateUser(String userName, @Valid UserDTO userDTO) {
		UserDTO responseUserDTO = new UserDTO();
		responseUserDTO.setEmail(userDTO.getEmail());
		responseUserDTO.setName(userDTO.getName());
		responseUserDTO.setUserName(userDTO.getUserName());
		responseUserDTO.setPort(Constants.FROM_FALLBACK);
		return responseUserDTO;
	}

	@Override
	public UserDTO deleteUser(String userName) {
		UserDTO responseUserDTO = new UserDTO();
		responseUserDTO.setEmail(Constants.FROM_FALL_BACK);
		responseUserDTO.setName(Constants.FROM_FALLBACK);
		responseUserDTO.setUserName(Constants.FROM_FALLBACK);
		responseUserDTO.setPort(Constants.FROM_FALLBACK);
		return responseUserDTO;
	}

}
