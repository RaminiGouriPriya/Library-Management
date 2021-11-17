package com.epam.library.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.epam.library.model.Library;

@Repository
public interface LibraryDAO extends JpaRepository<Library, String>{
	public List<Library> findByUserName(String userName);
	@Query("select l from Library l WHERE l.userName=:username and l.bookId=:bookid")
	public Library isIssued(@Param("username") String userName, @Param("bookid") Integer bookId);
}
