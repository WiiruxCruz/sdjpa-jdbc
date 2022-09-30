package com.wiirux.sdjpajdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.stereotype.Component;

import com.wiirux.sdjpajdbc.dao.BookDao;
import com.wiirux.sdjpajdbc.domain.Book;

@Component
public class BookDaoImpl implements BookDao {
	
	private final DataSource source;
	
	public BookDaoImpl(DataSource source) {
		this.source = source;
	}
	
	

	@Override
	public Book findBookById(Long id) {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		try {
			connection = source.getConnection();
			ps = connection.prepareStatement("SELECT * FROM book where id = ?");
			ps.setLong(1, id);
			
			resultSet = ps.executeQuery();
			
			if(resultSet.next()) {
				return getBookFromRS(resultSet);
			}
		} catch(SQLException e) {
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
	public Book findBookByTitle(String title) {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		try {
			connection = source.getConnection();
			ps = connection.prepareStatement("SELECT * FROM book where title = ?");
			ps.setString(1, title);
			
			resultSet = ps.executeQuery();
			
			if(resultSet.next()) {
				return getBookFromRS(resultSet);
			}
		} catch(SQLException e) {
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
	public Book saveNewBook(Book book) {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		try {
			connection = source.getConnection();
			ps = connection.prepareStatement("INSERT INTO book(isbn, publisher, title, author_id) values (?,?,?,?)");
			ps.setString(1, book.getIsbn());
			ps.setString(2, book.getPublisher());
			ps.setString(3, book.getTitle());
			ps.setLong(4, book.getAuthorId());
			ps.execute();
			
			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT LAST_INSERT_ID()");
			
			if(resultSet.next()) {
				Long savedId = resultSet.getLong(1);
				return this.findBookById(savedId);
			}
			
			statement.close();
			
		} catch(SQLException e) {
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
	public Book updateBook(Book book) {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		try {
			connection = source.getConnection();
			ps = connection.prepareStatement("UPDATE book set isbn = ?, publisher = ?, title = ?, author_id = ? where id = ?");
			ps.setString(1, book.getIsbn());
			ps.setString(2, book.getPublisher());
			ps.setString(3, book.getTitle());
			ps.setLong(4, book.getAuthorId());
			ps.setLong(5, book.getId());
			ps.execute();
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				closeAll(resultSet, ps, connection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return this.findBookById(book.getId());
	}

	@Override
	public Book deleteBookById(Long id) {
		// TODO Auto-generated method stub
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		
		try {
			connection = source.getConnection();
			ps = connection.prepareStatement("DELETE from book where id = ?");
			ps.setLong(1, id);
			ps.execute();
			
		} catch(SQLException e) {
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
	
	private Book getBookFromRS(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		Book book = new Book();
		book.setId(resultSet.getLong("id"));
		book.setIsbn(resultSet.getString("isbn"));
		book.setPublisher(resultSet.getString("publisher"));
		book.setTitle(resultSet.getString("title"));
		book.setAuthorId(resultSet.getLong("author_id"));
		return book;
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
