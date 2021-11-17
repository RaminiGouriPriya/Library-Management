package com.epam.library.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.library.dto.BookDTO;
import com.epam.library.proxy.BookProxy;

@RestController
@RequestMapping("library")
public class LibraryBookRestController {	
	@Autowired
	BookProxy bookProxy;
	
	@PostMapping("/books/{bookId}")
	public ResponseEntity<BookDTO> createBook(@RequestBody @Valid BookDTO bookDTO){
		return new ResponseEntity<BookDTO>(bookProxy.createBook(bookDTO),HttpStatus.CREATED);
	}
	
	@GetMapping("/books")
	public ResponseEntity<List<BookDTO>> getBooks(){
		return new ResponseEntity<List<BookDTO>>(bookProxy.getBooks(),HttpStatus.OK);
	}
	
	@GetMapping("/books/{bookId}")
	public ResponseEntity<BookDTO> getBookBasedOnId(@PathVariable ("bookId") Integer id){
		return new ResponseEntity<BookDTO>(bookProxy.getBookBasedOnId(id),HttpStatus.OK);
	}
	
	@PutMapping("/books/{bookId}")
	public ResponseEntity<BookDTO> updateBook(@PathVariable ("bookId") Integer id, @RequestBody @Valid BookDTO bookDTO){
		return new ResponseEntity<BookDTO>(bookProxy.updateBook(id, bookDTO),HttpStatus.OK);
	}
	
	@DeleteMapping("/books/{bookId}")
	public ResponseEntity<BookDTO> deleteBook(@PathVariable ("bookId") Integer id){
		return new ResponseEntity<BookDTO>(bookProxy.deleteBook(id),HttpStatus.OK);
	}

}
