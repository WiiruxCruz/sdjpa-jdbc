package com.wiirux.sdjpajdbc;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.wiirux.sdjpajdbc.dao.AuthorDao;
import com.wiirux.sdjpajdbc.dao.impl.AuthorDaoImpl;
import com.wiirux.sdjpajdbc.domain.Author;

@ActiveProfiles("local")
@DataJpaTest
@ComponentScan(basePackages = {"com.wiirux.sdjpajdbc.dao"})
//@Import(AuthorDaoImpl.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorDaoIntegrationTest {
	
	@Autowired
	AuthorDao ad;
	
	@Test
	void testDeleteAuthor() {
		Author author = new Author();
		author.setFirst_name("john");
		author.setLast_name("t");
		
		Author saved = ad.saveNewAuthor(author);
		
		ad.deleteAuthorById(saved.getId());
		Author deleted = ad.getById(saved.getId());
		
		assertThat(deleted).isNull();
	}
	
	@Test
	void testUpdateAuthor() {
		Author author = new Author();
		author.setFirst_name("john");
		author.setLast_name("t");
		
		Author saved = ad.saveNewAuthor(author);
		saved.setLast_name("Thompson");
		Author updated = ad.updateAuthor(saved);
		
		assertThat(updated.getLast_name()).isEqualTo("Thompson");
	}
	
	@Test
	void testSaveAuthor() {
		Author author = new Author();
		author.setFirst_name("John");
		author.setLast_name("Thompson");
		Author saved = ad.saveNewAuthor(author);
		
		assertThat(saved).isNotNull();
	}
	
	@Test
	void testGetAuthor() {
		Author author = ad.getById(1L);
		assertThat(author).isNotNull();
	}
	
	@Test
	void testGetAuthorByName() {
		Author author = ad.findAuthorByName("Craig", "Walls");
		assertThat(author).isNotNull();
	}

}
