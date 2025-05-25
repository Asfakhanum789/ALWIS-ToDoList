package com.db;

import java.sql.Connection;
import java.sql.DriverManager;
  
 
public class Database {
	 
	public static Connection getconnection() throws Exception {
		 
		Class.forName("com.mysql.cj.jdbc.Driver");
 
		return  DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/ToDoList", "root", "Asfakhanum@2002");
		 
	}
}
