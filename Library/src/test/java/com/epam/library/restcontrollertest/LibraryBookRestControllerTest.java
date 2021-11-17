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

import com.epam.library.controller.LibraryBookRestController;
import com.epam.library.dto.BookDTO;
import com.epam.library.proxy.BookProxy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(LibraryBookRestController.class)
class LibraryBookRestControllerTest {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	BookProxy bookProxy;

	BookDTO bookDTO1 = new BookDTO();
	BookDTO bookDTO2 = new BookDTO();
	BookDTO bookDTO3 = new BookDTO();
	List<BookDTO> bookDTOList = new ArrayList<>();

	@BeforeEach
	void testDataPreparation() {
		bookDTO1.setId(101);
		bookDTOList.add(bookDTO1);
		bookDTOList.add(bookDTO2);
		bookDTOList.add(bookDTO3);
	}

	@Test
	void testCreateBook() throws JsonProcessingException, Exception {
		when(bookProxy.createBook(Mockito.any(BookDTO.class)))
				.thenReturn(new BookDTO(101, "name", "publisher", "author","1010"));
		mockMvc.perform(MockMvcRequestBuilders.post("/library/books/101").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new BookDTO(101, "name", "publisher", "author","1010"))))
				.andExpect(status().isCreated()).andDo(print()).andExpect(jsonPath("$", notNullValue()));
	}

	@Test
	void testreadBooks() throws Exception {
		when(bookProxy.getBooks()).thenReturn(bookDTOList);
		mockMvc.perform(MockMvcRequestBuilders.get("/library/books").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void testReadBookBasedOnId() throws Exception {
		when(bookProxy.getBookBasedOnId(Mockito.anyInt()))
				.thenReturn(new BookDTO(101, "name", "publisher", "author","1010"));
		mockMvc.perform(MockMvcRequestBuilders.get("/library/books/588").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}	

	@Test
	void testupdateBook() throws Exception {
		when(bookProxy.updateBook(Mockito.anyInt(), Mockito.any(BookDTO.class)))
				.thenReturn(new BookDTO(101, "name", "publisher", "author","1010"));
		mockMvc.perform(MockMvcRequestBuilders.put("/library/books/588").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new BookDTO(101, "name", "publisher", "author","1010"))))
				.andExpect(status().isOk());

	}

	@Test
	void testdeleteBook() throws Exception {
		when(bookProxy.deleteBook(Mockito.anyInt())).thenReturn(new BookDTO(101, "name", "publisher", "author","1010"));
		mockMvc.perform(delete("/library/books/588").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());
	}
}
