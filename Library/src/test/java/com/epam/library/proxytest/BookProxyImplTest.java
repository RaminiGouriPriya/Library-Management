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
import com.epam.library.dto.BookDTO;
import com.epam.library.proxy.BookProxyImpl;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(value = { MockitoExtension.class })
@MockitoSettings(strictness = Strictness.LENIENT)
class BookProxyImplTest {
	
	@InjectMocks
	BookProxyImpl bookProxyImpl;
	
	BookDTO mockedResponseBookDTO = new BookDTO();
	BookDTO mockedBookDTO = new BookDTO();
	BookDTO mockedBookDTO2 = new BookDTO();
	List<BookDTO> mockedBookDtoList= new ArrayList<>();
	{
		mockedResponseBookDTO.setId(101);
		mockedResponseBookDTO.setAuthor(Constants.FROM_FALLBACK);
		mockedResponseBookDTO.setName(Constants.FROM_FALLBACK);
		mockedResponseBookDTO.setPublisher(Constants.FROM_FALLBACK);
		mockedResponseBookDTO.setPort(Constants.FROM_FALLBACK);
		mockedBookDTO.setId(101);
		mockedBookDTO.setAuthor("author");
		mockedBookDTO.setName("name");
		mockedBookDTO.setPublisher("publisher");
		mockedBookDTO.setPort("1010");
		mockedBookDtoList.add(mockedResponseBookDTO);
		mockedBookDTO2.setId(100);
	}
	@Test
	void testCreateTask() {
		BookDTO bookDTO= bookProxyImpl.createBook(mockedBookDTO);
		assertEquals(mockedResponseBookDTO.getId(),bookDTO.getId());
	}
	
	@Test
	void testGetTasks() {
		List<BookDTO> bookDtoList = bookProxyImpl.getBooks();
		assertEquals(bookDtoList.get(0).getId(), mockedBookDtoList.get(0).getId());
	}
	
	@Test
	void testGetBasedOnId() {
		BookDTO bookDTO = bookProxyImpl.getBookBasedOnId(101);
		assertEquals(bookDTO.getId(), mockedResponseBookDTO.getId());
	}
	
	@Test
	void testUpdateBook() {
		BookDTO bookDTO = bookProxyImpl.updateBook(101, mockedBookDTO);
		assertEquals(bookDTO.getId(), mockedResponseBookDTO.getId());
	}
	
	@Test
	void testDeleteBook() {
		BookDTO bookDTO = bookProxyImpl.deleteBook(100);
		assertEquals(bookDTO.getId(), mockedBookDTO2.getId());
	}
	
}
