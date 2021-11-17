package com.epam.library.proxytest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.epam.library.constant.Constants;
import com.epam.library.dto.UserDTO;
import com.epam.library.proxy.UserProxyImpl;


@RunWith(MockitoJUnitRunner.class)
@ExtendWith(value = { MockitoExtension.class })
@MockitoSettings(strictness = Strictness.LENIENT)
class UserProxyImplTest {
	
	@InjectMocks
	UserProxyImpl userProxyImpl;
	
	UserDTO mockedUserDTO = new UserDTO();
	UserDTO mockedResponseUserDTO = new UserDTO();
	List<UserDTO> mockedUserDTOList = new ArrayList<>();
	{
		mockedResponseUserDTO.setEmail(Constants.FROM_FALLBACK);
		mockedResponseUserDTO.setName(Constants.FROM_FALLBACK);
		mockedResponseUserDTO.setUserName(Constants.FROM_FALLBACK);
		mockedResponseUserDTO.setPort(Constants.FROM_FALLBACK);
		mockedUserDTO.setEmail("email");
		mockedUserDTO.setName("name");
		mockedUserDTO.setUserName("from Fallback");
		mockedUserDTO.setPort("1010");
		mockedUserDTOList.add(mockedResponseUserDTO);
	}
	
	@Test
	void testCreateUser(){
		UserDTO userDTO = userProxyImpl.createUser(mockedUserDTO);
		assertEquals(userDTO.getUserName(), mockedResponseUserDTO.getUserName());
	}
	
	@Test
	void testGetUsers() {
		List<UserDTO> userDTOList = userProxyImpl.getUsers();
		assertEquals(userDTOList.get(0).getUserName(), mockedUserDTOList.get(0).getUserName());
	}
	
	@Test
	void testGetByUserName() {
		UserDTO userDTO = userProxyImpl.getByUserName("userName");
		assertEquals(userDTO.getUserName(), mockedResponseUserDTO.getUserName());
	}
	
	@Test
	void testUpdateUser() {
		UserDTO userDTO = userProxyImpl.updateUser("userName", mockedUserDTO);
		assertEquals(userDTO.getUserName(), mockedResponseUserDTO.getUserName());
	}
	
	@Test
	void testDeleteUser() {
		UserDTO userDTO = userProxyImpl.deleteUser("userName");
		assertEquals(userDTO.getUserName(), mockedResponseUserDTO.getUserName());
	}
	
	
}
