package com.epam.user.servicetest;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import com.epam.user.dao.UserDAO;
import com.epam.user.dto.UserDTO;
import com.epam.user.exception.UserNameDoesNotExistException;
import com.epam.user.model.User;
import com.epam.user.service.UserServiceImpl;


@RunWith(MockitoJUnitRunner.class)
@ExtendWith(value = {MockitoExtension.class})
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceImplTest {
	@Mock
    private ModelMapper modelMapper;
	
	@Mock
	private UserDAO userRepo;
	
	@InjectMocks
	UserServiceImpl userService;
	
	@InjectMocks
	List<User> userList = new ArrayList<>();
	List<UserDTO> userDTOList = new ArrayList<>();
	UserDTO testUserDTO = new UserDTO();
	User testUser = new User();
	{
		testUser.setUserName("username");
		testUserDTO.setUserName("username");
		userList.add(new User());
	}
	
	@BeforeEach
	public void testDataPreparation1() {
		UserDTO user1 = new UserDTO("username2", "email2", "name2","1010");
		UserDTO user2 = new UserDTO("username3", "email3", "name3","1010");
		UserDTO user3 = new UserDTO("username4", "email4", "name4","1010");
		UserDTO user4 = new UserDTO("username5", "email5", "name5","1010");
		userDTOList.add(user1);
		userDTOList.add(user2);		
		userDTOList.add(user3);		
		userDTOList.add(user4);
	}
	
	@Test
	void testCreateUser() {
		when(modelMapper.map(Mockito.any(UserDTO.class), Mockito.any())).thenReturn(testUser);
		when(userRepo.save(Mockito.any(User.class))).thenReturn(testUser);
		when(modelMapper.map(Mockito.any(User.class), Mockito.any())).thenReturn(testUserDTO);
		assertEquals(testUserDTO.getUserName(),userService.createUser(new UserDTO("username", "email", "name","1010")).getUserName());
	}
	
	@Test
	void testReadUsers() {
		when(modelMapper.map(userRepo.findAll(), new TypeToken<List<UserDTO>>() {}.getType())).thenReturn(userDTOList);
		List<UserDTO> listOfTasks = userService.getUsers();
		assertEquals(listOfTasks,userDTOList);
	}
	
	@Test
	void testReadUserBasedOnUserName() throws UserNameDoesNotExistException {
		when(userRepo.findByUserName(Mockito.any(String.class))).thenReturn(userList);
		assertEquals(modelMapper.map(testUser, UserDTO.class),userService.getUserBasedOnUserName("username"));
	}
	
	@Test
	void testReadUserNotPresent() throws UserNameDoesNotExistException {
		when(userRepo.findByUserName(Mockito.any(String.class))).thenReturn(List.of());
		assertThrows(UserNameDoesNotExistException.class,()->{
			userService.getUserBasedOnUserName("username");
		});
	}
	
	
	@Test
	void updateUser() throws UserNameDoesNotExistException {
		when(userRepo.findByUserName(Mockito.any(String.class))).thenReturn(userList);
		when(userRepo.save(Mockito.any(User.class))).thenReturn(testUser);
		assertEquals(modelMapper.map(testUser, UserDTO.class),userService.updateUser("username", testUserDTO));
	}
	
	@Test
	void testUpdateUserNotPresent() throws UserNameDoesNotExistException {
		when(userRepo.findByUserName(Mockito.any(String.class))).thenReturn(List.of());
		assertThrows(UserNameDoesNotExistException.class,()->{
			userService.updateUser("username", testUserDTO);
		});
	}
	
	@Test
	void testDeleteUser() {
		when(userRepo.findByUserName(Mockito.any(String.class))).thenReturn(userList);
		doNothing().when(userRepo).delete(Mockito.any(User.class));
		when(modelMapper.map(new UserDTO("username", "email", "name","1010"), UserDTO.class)).thenReturn(testUserDTO);
		assertDoesNotThrow(()->{
			userService.deleteUser("username");
		});
	}
	
	@Test
	void testDeleteUserNotPresent() throws UserNameDoesNotExistException {
		when(userRepo.findByUserName(Mockito.any(String.class))).thenReturn(List.of());
		assertThrows(UserNameDoesNotExistException.class,()->{
			userService.deleteUser("username");
		});
	}
}
