package com.epam.library.proxy;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.epam.library.constant.Constants;
import com.epam.library.dto.BookDTO;

@Service
public class BookProxyImpl implements BookProxy{
	@Override
	public BookDTO createBook(@Valid BookDTO bookDTO) {
		BookDTO responseBookDTO = new BookDTO();
		responseBookDTO.setId(101);
		responseBookDTO.setAuthor(bookDTO.getAuthor());
		responseBookDTO.setName(bookDTO.getName());
		responseBookDTO.setPublisher(bookDTO.getPublisher());
		responseBookDTO.setPort(Constants.FROM_FALLBACK);
		return responseBookDTO;
	}

	@Override
	public List<BookDTO> getBooks() {
		List<BookDTO> bookList = new ArrayList<>();
		BookDTO responseBookDTO = new BookDTO();
		responseBookDTO.setId(101);
		responseBookDTO.setAuthor(Constants.FROM_FALLBACK);
		responseBookDTO.setName(Constants.FROM_FALLBACK);
		responseBookDTO.setPublisher(Constants.FROM_FALLBACK);
		responseBookDTO.setPort(Constants.FROM_FALLBACK);
		bookList.add(responseBookDTO);
		return bookList;
	}

	@Override
	public BookDTO getBookBasedOnId(Integer id){
		BookDTO responseBookDTO = new BookDTO();
		responseBookDTO.setId(101);
		responseBookDTO.setAuthor(Constants.FROM_FALLBACK);
		responseBookDTO.setName(Constants.FROM_FALLBACK);
		responseBookDTO.setPublisher(Constants.FROM_FALLBACK);
		responseBookDTO.setPort(Constants.FROM_FALLBACK);
		return responseBookDTO;
	}

	@Override
	public BookDTO updateBook(Integer id, @Valid BookDTO bookDTO) {
		BookDTO responseBookDTO = new BookDTO();
		responseBookDTO.setId(id);
		responseBookDTO.setAuthor(bookDTO.getAuthor());
		responseBookDTO.setName(bookDTO.getName());
		responseBookDTO.setPublisher(bookDTO.getPublisher());
		responseBookDTO.setPort(Constants.FROM_FALLBACK);
		return responseBookDTO;
	}

	@Override
	public BookDTO deleteBook(Integer id) {
		BookDTO responseBookDTO = new BookDTO();
		responseBookDTO.setId(100);
		responseBookDTO.setAuthor(Constants.FROM_FALLBACK);
		responseBookDTO.setName(Constants.FROM_FALLBACK);
		responseBookDTO.setPublisher(Constants.FROM_FALLBACK);
		responseBookDTO.setPort(Constants.FROM_FALLBACK);
		return responseBookDTO;
	}

}
