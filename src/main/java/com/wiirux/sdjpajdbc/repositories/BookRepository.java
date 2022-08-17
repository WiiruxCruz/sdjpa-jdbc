package com.wiirux.sdjpajdbc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wiirux.sdjpajdbc.domain.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

}
