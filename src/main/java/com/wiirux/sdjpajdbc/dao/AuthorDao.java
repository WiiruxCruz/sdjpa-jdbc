package com.wiirux.sdjpajdbc.dao;

import com.wiirux.sdjpajdbc.domain.Author;

public interface AuthorDao {
	Author getById(Long id);
	Author findAuthorByName(String firstName, String lastName);
}
