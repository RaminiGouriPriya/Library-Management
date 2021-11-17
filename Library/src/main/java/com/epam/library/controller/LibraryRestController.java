package com.epam.library.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.library.dto.LibraryDTO;
import com.epam.library.exception.BookAlreadyIssuedException;
import com.epam.library.exception.BookCanNotBeDeleted;
import com.epam.library.exception.InvalidParameterException;
import com.epam.library.exception.LimitReachedException;
import com.epam.library.service.LibraryService;

@RestController
@RequestMapping("library")
public class LibraryRestController {
	@Autowired
	LibraryService libraryService;

	@PostMapping("/users/{username}/books/{bookId}")
	public ResponseEntity<LibraryDTO> issueBook(@PathVariable("username") String userName, @PathVariable Integer bookId,
			@RequestBody @Valid LibraryDTO libraryDTO) throws BookAlreadyIssuedException, LimitReachedException, InvalidParameterException {
		return new ResponseEntity<LibraryDTO>(libraryService.issueBook(userName, bookId, libraryDTO), HttpStatus.OK);
	}

	@DeleteMapping("/users/{username}/books/{bookId}")
	public ResponseEntity<LibraryDTO> releaseBook(@PathVariable("username") String userName,
			@PathVariable Integer bookId) throws BookCanNotBeDeleted {
		return new ResponseEntity<LibraryDTO>(libraryService.releaseBook(userName, bookId), HttpStatus.OK);
	}

}
