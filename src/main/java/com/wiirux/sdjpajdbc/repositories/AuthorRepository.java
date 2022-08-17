package com.wiirux.sdjpajdbc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wiirux.sdjpajdbc.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long>{

}
