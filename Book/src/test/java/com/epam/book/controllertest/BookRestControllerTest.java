package com.epam.book.controllertest;

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

import com.epam.book.controller.BookRestController;
import com.epam.book.dto.BookDTO;
import com.epam.book.exception.IdNotFoundException;
import com.epam.book.model.Book;
import com.epam.book.service.BookServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(BookRestController.class)
class BookRestControllerTest {
	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	BookServiceImpl bookServiceImpl;

	Book book = new Book();
	BookDTO bookDTO1 = new BookDTO();
	BookDTO bookDTO2 = new BookDTO();
	BookDTO bookDTO3 = new BookDTO();
	List<BookDTO> bookDTOList = new ArrayList<>();

	@BeforeEach
	void testDataPreparation() {
		book.setId(101);
		bookDTO1.setId(101);
		bookDTOList.add(bookDTO1);
		bookDTOList.add(bookDTO2);
		bookDTOList.add(bookDTO3);
	}

	@Test
	void testCreateBook() throws JsonProcessingException, Exception {
		when(bookServiceImpl.createBook(Mockito.any(BookDTO.class)))
				.thenReturn(new BookDTO(101, "name", "publisher", "author","1010"));
		mockMvc.perform(MockMvcRequestBuilders.post("/books").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new BookDTO(101, "name", "publisher", "author","1010"))))
				.andExpect(status().isCreated()).andDo(print()).andExpect(jsonPath("$", notNullValue()));
	}
	
	@Test
    void testreadBooks() throws Exception {
        when(bookServiceImpl.getBooks()).thenReturn(bookDTOList);
        mockMvc.perform(MockMvcRequestBuilders.get("/books")
        		.contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

	@Test
	void testReadBookBasedOnId() throws Exception {
		when(bookServiceImpl.getBookBasedOnId(Mockito.anyInt()))
				.thenReturn(new BookDTO(101, "name", "publisher", "author","1010"));
		mockMvc.perform(MockMvcRequestBuilders.get("/books/588").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	void testDisplayNotesBasedIdNotPresent() throws JsonProcessingException, Exception {
		when(bookServiceImpl.getBookBasedOnId(Mockito.anyInt())).thenThrow(IdNotFoundException.class);
		mockMvc.perform(MockMvcRequestBuilders.post("/books/588").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new BookDTO(101, "name", "publisher", "author","1010"))))
				.andExpect(status().is(405)).andDo(print());
	}

	@Test
	void testupdateBook() throws Exception {
		when(bookServiceImpl.updateBook(Mockito.anyInt(), Mockito.any(BookDTO.class)))
				.thenReturn(new BookDTO(101, "name", "publisher", "author","1010"));
		mockMvc.perform(MockMvcRequestBuilders.put("/books/588").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new BookDTO(101, "name", "publisher", "author","1010"))))
				.andExpect(status().isOk());
	}
	

	@Test
	void testupdateBookWithInvalidId() throws Exception {
		when(bookServiceImpl.updateBook(Mockito.anyInt(), Mockito.any(BookDTO.class)))
				.thenThrow(IdNotFoundException.class);
		mockMvc.perform(MockMvcRequestBuilders.put("/books/588").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new BookDTO(101, "name", "publisher", "author","1010"))))
				.andExpect(status().isNotFound());
	}
	
	@Test
	void testupdateBookWithInvalidArgs() throws Exception {
		when(bookServiceImpl.updateBook(Mockito.any(Integer.class), Mockito.any(BookDTO.class)))
				.thenThrow(IdNotFoundException.class);
		mockMvc.perform(MockMvcRequestBuilders.put("/books/588").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(new BookDTO(101, "name", "", "author","1010"))))
				.andExpect(status().isBadRequest());
	}

	@Test
	void testdeleteBook() throws Exception {
		when(bookServiceImpl.deleteBook(Mockito.anyInt()))
				.thenReturn(new BookDTO(101, "name", "publisher", "author","1010"));
		mockMvc.perform(delete("/books/588")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(print());
	}

}
