package com.epam.book.servicetest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import com.epam.book.dao.BookDAO;
import com.epam.book.dto.BookDTO;
import com.epam.book.exception.IdNotFoundException;
import com.epam.book.model.Book;
import com.epam.book.service.BookServiceImpl;
@RunWith(MockitoJUnitRunner.class)
@ExtendWith(value = {MockitoExtension.class})
@MockitoSettings(strictness = Strictness.LENIENT)
class BookServiceImplTest {
	@Mock
    private ModelMapper modelMapper;
	
	@Mock
	private BookDAO bookRepo;
	
	@InjectMocks
	private BookServiceImpl bookService;	
	List<Book> BookList = new ArrayList<>();
	List<BookDTO> BookDTOList = new ArrayList<>();
	BookDTO testBookDTO = new BookDTO();
	Book testBook = new Book();
	{
		testBook.setId(101);
		testBookDTO.setId(101);
	}
	
	@BeforeEach
	public void testDataPreparation1() {
		BookDTO Book1 = new BookDTO(101, "name", "publisher", "author","1010");
		BookDTO Book2 = new BookDTO(101, "name2", "publisher2", "author2","1010");
		BookDTO Book3 = new BookDTO(101, "name3", "publisher3", "author3","1010");
		BookDTO Book4 = new BookDTO(101, "name4", "publisher4", "author4","1010");
		BookDTOList.add(Book1);
		BookDTOList.add(Book2);		
		BookDTOList.add(Book3);		
		BookDTOList.add(Book4);
	}
	
	@Test
	void testCreateBook() {
		when(modelMapper.map(Mockito.any(BookDTO.class), Mockito.any())).thenReturn(testBook);
		when(bookRepo.save(Mockito.any(Book.class))).thenReturn(testBook);
		when(modelMapper.map(Mockito.any(Book.class), Mockito.any())).thenReturn(testBookDTO);
		assertEquals(testBookDTO.getId(),bookService.createBook(new BookDTO(101, "name", "publisher", "author","1010")).getId());
	}
	
	@Test
	void testReadBooks() {
		when(modelMapper.map(bookRepo.findAll(), new TypeToken<List<BookDTO>>() {}.getType())).thenReturn(BookDTOList);
		List<BookDTO> listOfTasks = bookService.getBooks();
		assertEquals(listOfTasks,BookDTOList);
	}
	
	@Test
	void testReadBookBasedOnId() throws IdNotFoundException {
		when(bookRepo.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(testBook));
		assertEquals(bookService.getBookBasedOnId(101),modelMapper.map(testBook, BookDTO.class));
	}
	
	@Test
	void testReadIdNotPresent() throws IdNotFoundException {
		when(bookRepo.findById(Mockito.any(Integer.class))).thenReturn(Optional.ofNullable(null));
		assertThrows(IdNotFoundException.class,()->{
			bookService.getBookBasedOnId(101);
		});
	}
	
	
	@Test
	void updateBook() throws IdNotFoundException {
		when(bookRepo.findById(Mockito.any(Integer.class))).thenReturn(Optional.of(testBook));
		when(bookRepo.save(Mockito.any(Book.class))).thenReturn(testBook);
		assertEquals(modelMapper.map(testBook, BookDTO.class),bookService.updateBook(101, testBookDTO));
	}
	
	@Test
	void testUpdateIdNotPresent() throws IdNotFoundException {
		when(bookRepo.findById(Mockito.any(Integer.class))).thenReturn(Optional.ofNullable(null));
		assertThrows(IdNotFoundException.class,()->{
			bookService.updateBook(101, testBookDTO);
		});
	}
	
	@Test
	void testDeleteBook() {
		when(bookRepo.findById(Mockito.anyInt())).thenReturn(Optional.of(testBook));
		doNothing().when(bookRepo).deleteById(Mockito.anyInt());
		when(modelMapper.map(new BookDTO(101, "name", "publisher", "author","1010"), BookDTO.class)).thenReturn(testBookDTO);
		assertDoesNotThrow(()->{
			bookService.deleteBook(101);
		});
	}
	
	@Test
	void testDeleteIdNotPresent() throws IdNotFoundException {
		when(bookRepo.findById(Mockito.any(Integer.class))).thenReturn(Optional.ofNullable(null));
		assertThrows(IdNotFoundException.class,()->{
			bookService.deleteBook(101);
		});
	}
}
