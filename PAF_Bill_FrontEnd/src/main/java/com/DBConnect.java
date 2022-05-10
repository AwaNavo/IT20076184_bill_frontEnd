package com;
import java.sql.*;

public class DBConnect {
	
	private static String url = "jdbc:mysql://localhost:3306/eg_db";
	private static String uname = "PAF_user1";
	private static String pwd = "paf_user1";
	
	public static Connection getDBConnection(){
		
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con =  DriverManager.getConnection(url, uname, pwd);
			System.out.println("Sucessfully connected");
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error while connecting");
		}
		return con;
					
	}

}
