package com.epam.library.service;

import java.util.Objects;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.library.constant.Constants;
import com.epam.library.dao.LibraryDAO;
import com.epam.library.dto.LibraryDTO;
import com.epam.library.exception.BookAlreadyIssuedException;
import com.epam.library.exception.BookCanNotBeDeleted;
import com.epam.library.exception.InvalidParameterException;
import com.epam.library.exception.LimitReachedException;
import com.epam.library.model.Library;
import com.epam.library.proxy.BookProxy;
import com.epam.library.proxy.UserProxy;

@Service
public class LibraryServiceImpl implements LibraryService {
	@Autowired
	ModelMapper modelMapper;

	@Autowired
	LibraryDAO libraryRepo;

	@Autowired
	UserProxy userProxy;

	@Autowired
	BookProxy bookProxy;

	@Override
	public LibraryDTO issueBook(String userName, Integer bookId, @Valid LibraryDTO libraryDTO)
			throws BookAlreadyIssuedException, LimitReachedException, InvalidParameterException{
		bookProxy.getBookBasedOnId(libraryDTO.getBookId());
		userProxy.getByUserName(libraryDTO.getUserName());
		bookProxy.getBookBasedOnId(bookId);
		userProxy.getByUserName(userName);
		Library library = modelMapper.map(checkForValidParameters(userName, bookId, libraryDTO), Library.class);
		libraryRepo.save(library);
		return modelMapper.map(library, LibraryDTO.class);
	}

	@Override
	public LibraryDTO releaseBook(String userName, Integer bookId) throws BookCanNotBeDeleted{
		bookProxy.getBookBasedOnId(bookId);
		userProxy.getByUserName(userName);
		if (Objects.isNull(libraryRepo.isIssued(userName, bookId))) {
			throw new BookCanNotBeDeleted(
					Constants.THE_BOOK_CANNOT_BE_DELETED_AS_THE_USERNAME_OR_BOOK_ID_IS_NOT_PRESENT);
		}
		Library library = new Library();
		library = libraryRepo.isIssued(userName, bookId);
		libraryRepo.delete(library);
		return modelMapper.map(library, LibraryDTO.class);
	}

	public LibraryDTO checkForMaxLimit(LibraryDTO libraryDTO) throws LimitReachedException {
		if (libraryRepo.findByUserName(libraryDTO.getUserName()).size() >= 3) {
			throw new LimitReachedException(Constants.YOU_HAVE_REACHED_THE_MAXIMUM_LIMIT_THE_BOOK_CANNOT_BE_ISSUED);
		}
		return libraryDTO;
	}

	public LibraryDTO checkForIssued(LibraryDTO libraryDTO) throws BookAlreadyIssuedException, LimitReachedException {
		if (Objects.nonNull(libraryRepo.isIssued(libraryDTO.getUserName(), libraryDTO.getBookId()))) {
			throw new BookAlreadyIssuedException(Constants.THE_BOOK_HAS_ALREADY_BEEN_ISSUED_IT_CANNOT_BE_ISSUED_AGAIN);
		}
		return checkForMaxLimit(libraryDTO);
	}

	public LibraryDTO checkForValidParameters(String userName, Integer bookId, LibraryDTO libraryDTO)
			throws InvalidParameterException, BookAlreadyIssuedException, LimitReachedException {
		if (!(libraryDTO.getUserName().equals(userName) && bookId.equals(libraryDTO.getBookId()))) {
			throw new InvalidParameterException(Constants.PLEASE_ENTER_CORRECT_VALUES);
		}
		return checkForIssued(libraryDTO);
	}

}
