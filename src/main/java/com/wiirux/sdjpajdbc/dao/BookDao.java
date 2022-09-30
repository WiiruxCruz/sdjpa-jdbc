package com.wiirux.sdjpajdbc.dao;

import com.wiirux.sdjpajdbc.domain.Book;

public interface BookDao {
	Book findBookById(Long id);
	Book findBookByTitle(String title);
	Book saveNewBook(Book book);
	Book updateBook(Book book);
	Book deleteBookById(Long id);
}
