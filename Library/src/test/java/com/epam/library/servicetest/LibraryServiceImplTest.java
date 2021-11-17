package com.epam.library.servicetest;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

import com.epam.library.constant.Constants;
import com.epam.library.dao.LibraryDAO;
import com.epam.library.dto.BookDTO;
import com.epam.library.dto.LibraryDTO;
import com.epam.library.dto.UserDTO;
import com.epam.library.exception.BookAlreadyIssuedException;
import com.epam.library.exception.BookCanNotBeDeleted;
import com.epam.library.exception.InvalidParameterException;
import com.epam.library.exception.LimitReachedException;
import com.epam.library.model.Library;
import com.epam.library.proxy.BookProxy;
import com.epam.library.proxy.UserProxy;
import com.epam.library.service.LibraryServiceImpl;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(value = { MockitoExtension.class })
@MockitoSettings(strictness = Strictness.LENIENT)
class LibraryServiceImplTest {
	@Mock
	ModelMapper modelMapper;

	@Mock
	LibraryDAO libraryRepo;

	@Mock
	UserProxy userProxy;

	@Mock
	BookProxy bookProxy;

	@InjectMocks
	LibraryServiceImpl libraryServiceImpl;
	String userName = "username";
	Integer bookId = 101;
	LibraryDTO testLibraryDTO = new LibraryDTO();
	Library testLibrary = new Library();
	Library testLibrary2 = new Library();
	BookDTO bookDTO = new BookDTO();
	UserDTO userDTO = new UserDTO();
	List<LibraryDTO> libraryDTOList = new ArrayList<>();
	List<Library> libraryList = new ArrayList<>();
	{
		bookDTO.setId(101);
		userDTO.setUserName("username");
		testLibrary.setId(101);
		testLibraryDTO.setBookId(101);
		testLibraryDTO.setId(101);
		testLibraryDTO.setUserName("username");
		testLibrary2.setId(101);
		testLibrary2.setBookId(101);
		testLibrary2.setUserName("username");
		libraryList.add(new Library());
		libraryList.add(new Library());
		libraryList.add(new Library());
	}

	@BeforeEach
	public void testDataPreparation1() {
		LibraryDTO library1 = new LibraryDTO(101, "username2", 101);
		LibraryDTO library2 = new LibraryDTO(101, "username2", 101);
		LibraryDTO library3 = new LibraryDTO(101, "username2", 101);
		LibraryDTO library4 = new LibraryDTO(101, "username2", 101);
		libraryDTOList.add(library1);
		libraryDTOList.add(library2);
		libraryDTOList.add(library3);
		libraryDTOList.add(library4);
	}

	@Test
	void testIssueBook() throws InvalidParameterException, BookAlreadyIssuedException, LimitReachedException {
		when(bookProxy.getBookBasedOnId(Mockito.anyInt())).thenReturn(bookDTO);
		when(userProxy.getByUserName(Mockito.anyString())).thenReturn(userDTO);
		when(bookProxy.getBookBasedOnId(Mockito.anyInt())).thenReturn(bookDTO);
		when(userProxy.getByUserName(Mockito.anyString())).thenReturn(userDTO);
		when(libraryRepo.isIssued(Mockito.anyString(), Mockito.anyInt())).thenReturn(null);
		when(libraryRepo.findByUserName(Mockito.anyString())).thenReturn(List.of());
		when(modelMapper.map(testLibraryDTO, Library.class)).thenReturn(testLibrary);
		assertEquals(modelMapper.map(testLibrary, LibraryDTO.class),
				libraryServiceImpl.issueBook(userName, bookId, testLibraryDTO));
	}

	@Test
	void testLimitReached() throws InvalidParameterException, BookAlreadyIssuedException, LimitReachedException {
		when(bookProxy.getBookBasedOnId(Mockito.anyInt())).thenReturn(bookDTO);
		when(userProxy.getByUserName(Mockito.anyString())).thenReturn(userDTO);
		when(bookProxy.getBookBasedOnId(Mockito.anyInt())).thenReturn(bookDTO);
		when(userProxy.getByUserName(Mockito.anyString())).thenReturn(userDTO);
		libraryServiceImpl.checkForValidParameters(userName, bookId, testLibraryDTO);
		when(libraryRepo.isIssued(Mockito.anyString(), Mockito.anyInt())).thenReturn(null);
		when(libraryRepo.findByUserName(Mockito.anyString())).thenReturn(libraryList);
		when(modelMapper.map(testLibraryDTO, Library.class)).thenReturn(testLibrary);
		assertThrows(LimitReachedException.class,() -> {
			libraryServiceImpl.issueBook("username2", 101, new LibraryDTO(101, "username2", 101));
		});
	}

	@Test
	void testBookAlreadyIssued() throws InvalidParameterException, BookAlreadyIssuedException, LimitReachedException {
		when(bookProxy.getBookBasedOnId(Mockito.anyInt())).thenReturn(bookDTO);
		when(userProxy.getByUserName(Mockito.anyString())).thenReturn(userDTO);
		when(bookProxy.getBookBasedOnId(Mockito.anyInt())).thenReturn(bookDTO);
		when(userProxy.getByUserName(Mockito.anyString())).thenReturn(userDTO);
		when(libraryRepo.isIssued(Mockito.anyString(), Mockito.anyInt())).thenReturn(testLibrary);
		when(libraryRepo.findByUserName(Mockito.anyString())).thenReturn(libraryList);
		when(modelMapper.map(testLibraryDTO, Library.class)).thenReturn(testLibrary);
		assertThrows(BookAlreadyIssuedException.class,() -> {
			libraryServiceImpl.issueBook("username2", 101, new LibraryDTO(101, "username2", 101));
		});
	}

	@Test
	void testRemoveBook() throws BookCanNotBeDeleted {
		when(bookProxy.getBookBasedOnId(Mockito.anyInt())).thenReturn(bookDTO);
		when(userProxy.getByUserName(Mockito.anyString())).thenReturn(userDTO);
		when(libraryRepo.findByUserName(Mockito.anyString())).thenReturn(libraryList);
		when(libraryRepo.isIssued(Mockito.anyString(), Mockito.anyInt())).thenReturn(testLibrary);
		doNothing().when(libraryRepo).delete(Mockito.any(Library.class));
		libraryServiceImpl.releaseBook("username", 101);
		when(modelMapper.map(testLibrary, LibraryDTO.class)).thenReturn(testLibraryDTO);
		assertDoesNotThrow(() -> {
			libraryServiceImpl.releaseBook("username", 101);
		});
	}

	@Test
	void testRemoveBookNotPresent() throws BookCanNotBeDeleted {
		when(bookProxy.getBookBasedOnId(Mockito.anyInt())).thenReturn(bookDTO);
		when(userProxy.getByUserName(Mockito.anyString())).thenReturn(userDTO);
		when(libraryRepo.isIssued(Mockito.anyString(), Mockito.anyInt())).thenReturn(null);
		when(modelMapper.map(testLibrary, LibraryDTO.class)).thenReturn(testLibraryDTO);
		assertThrows(BookCanNotBeDeleted.class,() -> {
			libraryServiceImpl.releaseBook("username", 101);
		});

	}

	@Test
	void testInvalidcheckparameters() throws BookAlreadyIssuedException, LimitReachedException {
		try {
			libraryServiceImpl.checkForValidParameters("userName", 5896, testLibraryDTO);
		} catch (InvalidParameterException exc) {
			assertEquals(Constants.PLEASE_ENTER_CORRECT_VALUES, exc.getMessage());
		}
	}

	@Test
	void testCheckParameters() throws BookAlreadyIssuedException, LimitReachedException, InvalidParameterException {
		LibraryDTO library= libraryServiceImpl.checkForValidParameters("username", 101, testLibraryDTO);
		assertEquals(library, testLibraryDTO);

	}
}
