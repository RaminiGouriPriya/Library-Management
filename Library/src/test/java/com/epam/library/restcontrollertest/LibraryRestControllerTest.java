package com.epam.library.restcontrollertest;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.epam.library.controller.LibraryRestController;
import com.epam.library.dto.LibraryDTO;
import com.epam.library.exception.BookAlreadyIssuedException;
import com.epam.library.exception.BookCanNotBeDeleted;
import com.epam.library.exception.InvalidParameterException;
import com.epam.library.exception.LimitReachedException;
import com.epam.library.service.LibraryServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(LibraryRestController.class)
class LibraryRestControllerTest {
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@MockBean
	LibraryServiceImpl libraryServiceImpl;
	
	LibraryDTO libraryDTO1 = new LibraryDTO();
	LibraryDTO libraryDTO2 = new LibraryDTO();
	LibraryDTO libraryDTO3 = new LibraryDTO();
	
	@BeforeEach
	void testDataPreparation() {
		libraryDTO1.setId(101);
	}
	
	@Test
	void testIssueBook() throws JsonProcessingException, Exception {
		when(libraryServiceImpl.issueBook(Mockito.anyString(), Mockito.anyInt(), Mockito.any(LibraryDTO.class)))
				.thenReturn(new LibraryDTO(1,"username",101));
		mockMvc.perform(MockMvcRequestBuilders.post("/library/users/username/books/101").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new LibraryDTO(1,"username",101))))
				.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$", notNullValue()));
	}
	
	@Test
	void testIssueBookWithInvalidParameter() throws JsonProcessingException, Exception {
		when(libraryServiceImpl.issueBook(Mockito.anyString(), Mockito.anyInt(), Mockito.any(LibraryDTO.class)))
				.thenThrow(InvalidParameterException.class);
		mockMvc.perform(MockMvcRequestBuilders.post("/library/users/user/books/10156").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new LibraryDTO(1,"username",101))))
				.andExpect(status().isBadRequest()).andDo(print());
	}
	
	@Test
	void testIssueBookWithLimitReached() throws JsonProcessingException, Exception {
		when(libraryServiceImpl.issueBook(Mockito.anyString(), Mockito.anyInt(), Mockito.any(LibraryDTO.class)))
				.thenThrow(LimitReachedException.class);
		mockMvc.perform(MockMvcRequestBuilders.post("/library/users/user/books/101").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new LibraryDTO(1,"username",101))))
				.andExpect(status().isBadRequest()).andDo(print());
	}
	
	@Test
	void testIssueBookWithAlreadyIssued() throws JsonProcessingException, Exception {
		when(libraryServiceImpl.issueBook(Mockito.anyString(), Mockito.anyInt(), Mockito.any(LibraryDTO.class)))
				.thenThrow(BookAlreadyIssuedException.class);
		mockMvc.perform(MockMvcRequestBuilders.post("/library/users/user/books/101").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new LibraryDTO(1,"username",101))))
				.andExpect(status().isBadRequest()).andDo(print());
	}
	
	@Test
	void testIssueBookWithInvalidArgs() throws JsonProcessingException, Exception {
		when(libraryServiceImpl.issueBook(Mockito.anyString(), Mockito.anyInt(), Mockito.any(LibraryDTO.class)))
				.thenThrow(BookAlreadyIssuedException.class);
		mockMvc.perform(MockMvcRequestBuilders.post("/library/users/user/books/101").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new LibraryDTO(1," ",101))))
				.andExpect(status().isBadRequest()).andDo(print());
	}
	
//	@Test
//	void testIssueBookWithFeignExcepton() throws JsonProcessingException, Exception {
//		when(libraryServiceImpl.issueBook(Mockito.anyString(), Mockito.anyInt(), Mockito.any(LibraryDTO.class)))
//				.thenThrow(feign.FeignException.class);
//		mockMvc.perform(MockMvcRequestBuilders.post("/library/users/user/books/10156").contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(new LibraryDTO(1,"username",101))))
//				.andExpect(status().isBadRequest()).andDo(print());
//	}
	

	@Test
	void testReleaseBook() throws Exception {
		when(libraryServiceImpl.releaseBook(Mockito.anyString(), Mockito.anyInt()))
				.thenReturn(new LibraryDTO(1,"username",101));
		mockMvc.perform(delete("/library/users/username/books/101")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());
	}
	
	@Test
	void testReleaseBookWithCannotBeDeleted() throws JsonProcessingException, Exception {
		when(libraryServiceImpl.releaseBook(Mockito.anyString(), Mockito.anyInt()))
				.thenThrow(BookCanNotBeDeleted.class);
		mockMvc.perform(delete("/library/users/username/books/1015")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new LibraryDTO(1,"username",101))))
				.andExpect(status().isBadRequest()).andDo(print());
	}
	
	
	
	

}
