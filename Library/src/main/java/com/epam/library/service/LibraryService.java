package com.epam.library.service;


import javax.validation.Valid;

import com.epam.library.dto.LibraryDTO;
import com.epam.library.exception.BookAlreadyIssuedException;
import com.epam.library.exception.BookCanNotBeDeleted;
import com.epam.library.exception.InvalidParameterException;
import com.epam.library.exception.LimitReachedException;

public interface LibraryService {
	LibraryDTO issueBook(String userName, Integer bookId, @Valid LibraryDTO libraryDTO)
			throws BookAlreadyIssuedException, LimitReachedException, InvalidParameterException;
	LibraryDTO releaseBook(String userName, Integer bookId) throws BookCanNotBeDeleted;
}
