package com.wiirux.sdjpajdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.hibernate.loader.ast.internal.Preparable;
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
		//Statement statement = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		try {
			connection = source.getConnection();
			//statement = connection.createStatement();
			ps = connection.prepareStatement("SELECT * FROM author where id = ?");
			ps.setLong(1, id);
			//resultSet = statement.executeQuery("SELECT * FROM author where id = " + id);
			resultSet = ps.executeQuery();
			
			if(resultSet.next()) {
				return getAuthorFromRS(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeAll(resultSet, ps, connection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}

	@Override
	public Author findAuthorByName(String firstName, String lastName) {
		// TODO Auto-generated method stub
		Connection connection = null;
		//Statement statement = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		try {
			connection = source.getConnection();
			//statement = connection.createStatement();
			ps = connection.prepareStatement("SELECT * FROM author where first_name = ? and last_name = ?");
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			//resultSet = statement.executeQuery("SELECT * FROM author where id = " + id);
			resultSet = ps.executeQuery();
			
			if(resultSet.next()) {
				return getAuthorFromRS(resultSet);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeAll(resultSet, ps, connection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	@Override
	public Author saveNewAuthor(Author author) {
		// TODO Auto-generated method stub
		Connection connection = null;
		//Statement statement = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		try {
			connection = source.getConnection();
			ps = connection.prepareStatement("INSERT INTO author (first_name, last_name) values (?, ?)");
			ps.setString(1, author.getFirst_name());
			ps.setString(2, author.getLast_name());
			ps.execute();
			
			Statement statement = connection.createStatement();
			
			resultSet = ps.executeQuery("SELECT LAST_INSERT_ID()");
			
			if(resultSet.next()) {
				Long savedId = resultSet.getLong(1);
				return this.getById(savedId);
			}
			
			statement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeAll(resultSet, ps, connection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	

	@Override
	public Author updateAuthor(Author author) {
		// TODO Auto-generated method stub
		Connection connection = null;
		//Statement statement = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		try {
			connection = source.getConnection();
			ps = connection.prepareStatement("UPDATE author set first_name = ?, last_name = ? where id = ?");
			ps.setString(1, author.getFirst_name());
			ps.setString(2, author.getLast_name());
			ps.setLong(3, author.getId());
			ps.execute();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeAll(resultSet, ps, connection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return this.getById(author.getId());
	}

	private Author getAuthorFromRS(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		Author author = new Author();
		author.setId(resultSet.getLong("id"));
		author.setFirst_name(resultSet.getString("first_name"));
		author.setLast_name(resultSet.getString("last_name"));
		return author;
	}

	private void closeAll(ResultSet resultSet, PreparedStatement ps, Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		if(resultSet != null) {
			resultSet.close();
		}
		
		//if(statement != null) {
			//statement.close();
		//}
		
		if(ps != null) {
			ps.close();
		}
		
		if(connection != null) {
			connection.close();
		}
	}

}
