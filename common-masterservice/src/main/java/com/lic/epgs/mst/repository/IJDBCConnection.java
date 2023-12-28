package com.lic.epgs.mst.repository;

import java.sql.Connection;
import java.sql.SQLException;
 
public interface IJDBCConnection {
    public Connection getConnection() throws SQLException ;
}