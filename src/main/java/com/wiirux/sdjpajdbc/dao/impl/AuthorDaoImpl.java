package com.wiirux.sdjpajdbc.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wiirux.sdjpajdbc.dao.AuthorDao;
import com.wiirux.sdjpajdbc.domain.Author;

@Component
public class AuthorDaoImpl implements AuthorDao {
	
	private final DataSource source;
	
	public AuthorDaoImpl(DataSource source) {
		this.source = source;
	}

	@Override
	public Author getById(Long id) {
		// TODO Auto-generated method stub
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = source.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM author where id = " + id);
			
			if(resultSet.next()) {
				Author author = new Author();
				author.setId(id);
				author.setFirst_name(resultSet.getString("first_name"));
				author.setLast_name(resultSet.getString("last_name"));
				
				return author;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
