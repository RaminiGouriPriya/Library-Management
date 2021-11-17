package com.epam.book.service;

import java.util.List;

import com.epam.book.dto.BookDTO;
import com.epam.book.exception.IdNotFoundException;

public interface BookService {
	BookDTO createBook(BookDTO bookDTO);
	List<BookDTO> getBooks();
	BookDTO getBookBasedOnId(Integer id) throws IdNotFoundException;
	BookDTO updateBook(Integer id,BookDTO bookDTO) throws IdNotFoundException;
	BookDTO deleteBook(Integer id) throws IdNotFoundException;
}
