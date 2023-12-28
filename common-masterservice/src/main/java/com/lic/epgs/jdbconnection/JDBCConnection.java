package com.lic.epgs.jdbconnection;
	 
	import java.sql.Connection;
	import java.sql.SQLException;
	 
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.jdbc.core.JdbcTemplate;
	import org.springframework.stereotype.Repository;
	 

	@Repository
	public class JDBCConnection implements IJDBCConnection {

		@Autowired
	    private JdbcTemplate jdbcTemplate;

	    private Connection connection;
	 
	    @Override
	    public Connection getConnection() throws SQLException {
	        if(this.connection != null && !this.connection.isClosed()) {
	                return this.connection;
	        }else {

	            if(null != connection && !connection.isClosed()) {
	                connection.close();
	            }
	             connection = jdbcTemplate.getDataSource().getConnection();
	                return connection;
	        }
	      }     

	}

