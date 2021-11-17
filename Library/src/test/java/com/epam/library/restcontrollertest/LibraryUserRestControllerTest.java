package com.epam.library.restcontrollertest;

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

import com.epam.library.controller.LibraryUserRestController;
import com.epam.library.dto.UserDTO;
import com.epam.library.proxy.UserProxy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(LibraryUserRestController.class)
class LibraryUserRestControllerTest {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	UserProxy userProxy;

	UserDTO userDTO1 = new UserDTO();
	UserDTO userDTO2 = new UserDTO();
	UserDTO userDTO3 = new UserDTO();
	List<UserDTO> userDTOList = new ArrayList<>();

	@BeforeEach
	void testDataPreparation() {
		userDTO1.setUserName("username");
		userDTOList.add(userDTO1);
		userDTOList.add(userDTO2);
		userDTOList.add(userDTO3);
	}

	@Test
	void testCreateUser() throws JsonProcessingException, Exception {
		when(userProxy.createUser(Mockito.any(UserDTO.class)))
				.thenReturn(new UserDTO("username", "email", "name","1010"));
		mockMvc.perform(MockMvcRequestBuilders.post("/library/users/username").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new UserDTO("username", "email", "name","1010"))))
				.andExpect(status().isCreated()).andDo(print()).andExpect(jsonPath("$", notNullValue()));
	}

	@Test
	void testGetUsers() throws Exception {
		when(userProxy.getUsers()).thenReturn(userDTOList);
		mockMvc.perform(MockMvcRequestBuilders.get(("/library/users")).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void testGetByUserName() throws Exception {
		when(userProxy.getByUserName(Mockito.any(String.class)))
				.thenReturn(new UserDTO("username", "email", "name","1010"));
		mockMvc.perform(MockMvcRequestBuilders.get("/library/users/username").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void testupdateUser() throws Exception {
		when(userProxy.updateUser(Mockito.any(String.class), Mockito.any(UserDTO.class)))
				.thenReturn(new UserDTO("username", "email", "name","1010"));
		mockMvc.perform(MockMvcRequestBuilders.put("/library/users/username").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new UserDTO("username", "email", "name","1010"))))
				.andExpect(status().isOk());

	}


	@Test
	void testdeleteUser() throws Exception {
		when(userProxy.deleteUser(Mockito.any(String.class)))
				.thenReturn(new UserDTO("username", "email", "name","1010"));
		mockMvc.perform(
				delete("/library/users/username").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());
	}

}
