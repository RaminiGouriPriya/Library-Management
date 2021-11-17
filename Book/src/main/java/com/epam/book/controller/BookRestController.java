package com.epam.book.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.book.constant.Constants;
import com.epam.book.dto.BookDTO;
import com.epam.book.exception.IdNotFoundException;
import com.epam.book.service.BookService;

@RestController
public class BookRestController {
	

	@Autowired
	BookService bookService;
	
	@Autowired
	Environment environment;
	
	@PostMapping("/books")
	public ResponseEntity<BookDTO> createBook(@RequestBody @Valid BookDTO bookDTO){
		BookDTO responseBookDTO = bookService.createBook(bookDTO);
		responseBookDTO.setPort(environment.getProperty(Constants.LOCAL_SERVER_PORT));
		return new ResponseEntity<BookDTO>(responseBookDTO,HttpStatus.CREATED);
	}
	
	@GetMapping("/books")
	public ResponseEntity<List<BookDTO>> getBooks(){
		return new ResponseEntity<List<BookDTO>>(bookService.getBooks(),HttpStatus.OK);
	}
	
	@GetMapping("/books/{book_id}")
	public ResponseEntity<BookDTO> getBookBasedOnId(@PathVariable ("book_id") Integer id) throws IdNotFoundException{
		BookDTO responseBookDTO = bookService.getBookBasedOnId(id);
		responseBookDTO.setPort(environment.getProperty(Constants.LOCAL_SERVER_PORT));
		return new ResponseEntity<BookDTO>(responseBookDTO,HttpStatus.OK);
	}
	
	@PutMapping("/books/{book_id}")
	public ResponseEntity<BookDTO> updateBook(@PathVariable ("book_id") Integer id, @RequestBody @Valid BookDTO bookDTO) throws IdNotFoundException{
		BookDTO responseBookDTO = bookService.updateBook(id, bookDTO);
		responseBookDTO.setPort(environment.getProperty(Constants.LOCAL_SERVER_PORT));
		return new ResponseEntity<BookDTO>(responseBookDTO,HttpStatus.OK);
	}
	
	@DeleteMapping("/books/{book_id}")
	public ResponseEntity<BookDTO> deleteBook(@PathVariable ("book_id") Integer id) throws IdNotFoundException{
		BookDTO responseBookDTO = bookService.deleteBook(id);
		responseBookDTO.setPort(environment.getProperty(Constants.LOCAL_SERVER_PORT));
		return new ResponseEntity<BookDTO>(responseBookDTO,HttpStatus.OK);
	}
}
