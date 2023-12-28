package com.lic.epgs.jdbconnection;

import java.sql.Connection;
import java.sql.SQLException;
 
public interface IJDBCConnection {
    public Connection getConnection() throws SQLException ;
}