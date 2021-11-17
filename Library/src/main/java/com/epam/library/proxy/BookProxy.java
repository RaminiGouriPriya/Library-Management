package com.epam.library.proxy;

import java.util.List;

import javax.validation.Valid;

import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.epam.library.dto.BookDTO;
@FeignClient(name = "book-service"/*, fallback = BookProxyImpl.class*/)
@LoadBalancerClient(name="book-service"/*, configuration = BookProxyImpl.class*/)
public interface BookProxy {
	
	@PostMapping("/books")
	public BookDTO createBook(@RequestBody @Valid BookDTO bookDTO);
	
	@GetMapping("/books")
	public List<BookDTO> getBooks();
	
	@GetMapping("/books/{book_id}")
	public BookDTO getBookBasedOnId(@PathVariable ("book_id") Integer id);
	
	@PutMapping("/books/{book_id}")
	public BookDTO updateBook(@PathVariable ("book_id") Integer id, @RequestBody @Valid BookDTO bookDTO);
	
	@DeleteMapping("/books/{book_id}")
	public BookDTO deleteBook(@PathVariable ("book_id") Integer id);

}
