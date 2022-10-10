package com.wiirux.sdjpajdbc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import com.wiirux.sdjpajdbc.dao.BookDao;
import com.wiirux.sdjpajdbc.domain.Author;
import com.wiirux.sdjpajdbc.domain.Book;





@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"com.wiirux.sdjpajdbc.dao"})
//@Import(AuthorDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookDaoIntegrationTest {
	
	@Autowired
	BookDao bd;
	
	@Test
	void testDeleteBook() {
		Book book = new Book();
		book.setIsbn("test");
		book.setPublisher("test2");
		book.setTitle("test3");
		
		Book saved = bd.saveNewBook(book);
		
		bd.deleteBookById(saved.getId());
		Book deleted = bd.findBookById(saved.getId());
		
		assertThat(deleted).isNull();
	}
	
	@Test
	void testUpdateBook() {
		Book book = new Book();
		book.setIsbn("ISBN");
		book.setPublisher("PUBLISHER");
		book.setTitle("Titulo");
		
		Author author = new Author();
		author.setId(3L);
		
		book.setAuthor(author);
		
		Book saved = bd.saveNewBook(book);
		saved.setPublisher("PUBLICISTA");
		Book updated = bd.updateBook(saved);
		
		assertThat(updated.getPublisher()).isEqualTo("PUBLICISTA");
	}
	
	@Test
	void testSaveBook() {
		Book book = new Book();
		book.setIsbn("ISBN2");
		book.setPublisher("PUBLISHER2");
		book.setTitle("Titulo2");
		
		Author author = new Author();
		author.setId(3L);
		
		book.setAuthor(author);
		Book saved = bd.saveNewBook(book);
		
		assertThat(saved).isNotNull();
		
	}
	
	@Test
	void testGetBook() {
		Book book = bd.findBookById(1L);
		assertThat(book.getId()).isNotNull();
	}
	
	@Test
	void testGetBookByName() {
		Book book = bd.findBookByTitle("Clean Code");
		assertThat(book).isNotNull();
	}
}
