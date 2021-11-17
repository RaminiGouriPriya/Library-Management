package com.epam.book.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.book.model.Book;

@Repository
public interface BookDAO extends JpaRepository<Book, Integer>{
	

}
