package com.epam.user.controllertest;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.epam.user.controller.UserRestController;
import com.epam.user.dto.UserDTO;
import com.epam.user.exception.UserNameDoesNotExistException;
import com.epam.user.model.User;
import com.epam.user.service.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(UserRestController.class)
class UserRestControllerTest {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	UserServiceImpl userServiceImpl;

	User user = new User();
	UserDTO userDTO1 = new UserDTO();
	UserDTO userDTO2 = new UserDTO();
	UserDTO userDTO3 = new UserDTO();
	List<UserDTO> userDTOList = new ArrayList<>();

	@BeforeEach
	void testDataPreparation() {
		user.setUserName("username");
		userDTO1.setUserName("username");
		userDTOList.add(userDTO1);
		userDTOList.add(userDTO2);
		userDTOList.add(userDTO3);
	}

	@Test
	void testCreateUser() throws JsonProcessingException, Exception {
		when(userServiceImpl.createUser(Mockito.any(UserDTO.class)))
				.thenReturn(new UserDTO("username", "email", "name","1010"));
		mockMvc.perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new UserDTO("username", "email", "name","1010"))))
				.andExpect(status().isCreated()).andDo(print()).andExpect(jsonPath("$", notNullValue()));
	}
	
	@Test
    void testreadUsers() throws Exception {
        when(userServiceImpl.getUsers()).thenReturn(userDTOList);
        mockMvc.perform(MockMvcRequestBuilders.get(("/users"))
        		.contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

	@Test
	void testReadUserBasedOnUserName() throws Exception {
		when(userServiceImpl.getUserBasedOnUserName(Mockito.any(String.class)))
				.thenReturn(new UserDTO("username", "email", "name","1010"));
		mockMvc.perform(MockMvcRequestBuilders.get("/users/username").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void testDisplayUsersBasedUserNameNotPresent() throws JsonProcessingException, Exception {
		when(userServiceImpl.getUserBasedOnUserName(Mockito.any(String.class))).thenThrow(UserNameDoesNotExistException.class);
		mockMvc.perform(MockMvcRequestBuilders.post("/users/username").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new UserDTO("username", "email", "name","1010"))))
				.andExpect(status().is(405)).andDo(print());
	}

	@Test
	void testupdateUser() throws Exception {
		when(userServiceImpl.updateUser(Mockito.any(String.class), Mockito.any(UserDTO.class)))
				.thenReturn(new UserDTO("username", "email", "name","1010"));
		mockMvc.perform(MockMvcRequestBuilders.put("/users/username").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new UserDTO("username", "email", "name","1010"))))
				.andExpect(status().isOk());

	}

	@Test
	void testupdateUserWithInvalidUserName() throws Exception {
		when(userServiceImpl.updateUser(Mockito.any(String.class), Mockito.any(UserDTO.class)))
				.thenThrow(UserNameDoesNotExistException.class);
		mockMvc.perform(MockMvcRequestBuilders.put("/users/username").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new UserDTO("username", "email", "name","1010"))))
				.andExpect(status().is(400));
	}
	
	@Test
	void testupdateUserWithInvalidArgs() throws Exception {
		when(userServiceImpl.updateUser(Mockito.any(String.class), Mockito.any(UserDTO.class)))
				.thenThrow(UserNameDoesNotExistException.class);
		mockMvc.perform(MockMvcRequestBuilders.put("/users/username").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new UserDTO("username", "", "name","1010"))))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testdeleteUser() throws Exception {
		when(userServiceImpl.deleteUser(Mockito.any(String.class)))
				.thenReturn(new UserDTO("username", "email", "name","1010"));
		mockMvc.perform(delete("/users/username")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());
	}
	
	

}
