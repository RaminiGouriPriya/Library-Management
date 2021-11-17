package com.epam.book.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.book.constant.Constants;
import com.epam.book.dao.BookDAO;
import com.epam.book.dto.BookDTO;
import com.epam.book.exception.IdNotFoundException;
import com.epam.book.model.Book;

@Service
public class BookServiceImpl implements BookService{
	

	@Autowired
	BookDAO bookRepo;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Override 
	public BookDTO createBook(BookDTO bookDTO) {
		Book book=modelMapper.map(bookDTO, Book.class);
		return modelMapper.map(bookRepo.save(book), BookDTO.class);
	}

	@Override
	public List<BookDTO> getBooks() {
		return modelMapper.map(bookRepo.findAll(), new TypeToken<List<BookDTO>>() {}.getType());
	}

	@Override
	public BookDTO getBookBasedOnId(Integer id) throws IdNotFoundException {
		Book book = findBookById(id);
		return modelMapper.map(book, BookDTO.class);
	}

	@Override
	public BookDTO updateBook(Integer id, BookDTO bookDTO) throws IdNotFoundException {
		Book book = findBookById(id);
		book.setName(bookDTO.getName());
		book.setPublisher(bookDTO.getPublisher());
		book.setAuthor(bookDTO.getAuthor());
		return modelMapper.map(bookRepo.save(book), BookDTO.class);
	}

	@Override
	public BookDTO deleteBook(Integer id) throws IdNotFoundException {
		Book book = findBookById(id);
		bookRepo.deleteById(id);
		return modelMapper.map(book, BookDTO.class);	
	}
	
	public Book findBookById(Integer id) throws IdNotFoundException {
		return bookRepo.findById(id).orElseThrow(()->new IdNotFoundException(Constants.THE_BOOK_ID_DOES_NOT_EXIST));
	}

}
