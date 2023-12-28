/*
 * package com.lic.epgs.jdbconnection;
 * 
 * import java.sql.Connection; import java.sql.DriverManager; import
 * java.sql.SQLException;
 * 
 * public class CreateConnection {
 * 
 * public Connection getConnection() throws SQLException, ClassNotFoundException
 * { Class.forName("oracle.jdbc.driver.OracleDriver");
 * 
 * //step2 create the connection object Connection
 * con=DriverManager.getConnection(
 * "jdbc:oracle:thin:@10.7.18.234:1523:LICEPGSDEVN","LICCommon","LICCommon");
 * return con; }
 * 
 * }
 * 
 */