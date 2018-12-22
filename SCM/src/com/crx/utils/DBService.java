package com.crx.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBService {
	private final static String DRIVER_NAME ="com.mysql.jdbc.Driver";
	private final static String URL = "jdbc:mysql://localhost:3306/scm?autoReconnect=true";
	private final static String USERNAME = "root";
	private final static String PASSWORD = "root";
  
	
	private static  Connection getConn(){
		Connection conn=null;
		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	
	public  static Statement getStmt(){
		Statement stmt=null;
		try {
		    stmt = getConn().createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stmt;
	}
    
}
