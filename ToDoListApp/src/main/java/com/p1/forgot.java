package com.p1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import com.db.Database;
 
@WebServlet("/forgot")
public class forgot extends HttpServlet {
	 protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 resp.setContentType("text/html");
	        PrintWriter writer = resp.getWriter();

	         
	        String email = req.getParameter("email");
	        String password = req.getParameter("pwd");
	          
	        
	        try {
	           
	        	 Connection con=Database.getconnection();

	            PreparedStatement pstmt = con.prepareStatement(
	                "Update user Set password=? where email=?"
	            );

	   
	            pstmt.setString(1, password);
	      
	            pstmt.setString(2,email); 

	            int x = pstmt.executeUpdate();

	            if (x > 0) {
	            	writer.println("<script>alert('Reset Password Sucessful'); window.location='Login.html';</script>");
	            } else {
	                writer.println("Reset is failed.");
	            }

	            con.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	            writer.println("Error: " + e.getMessage());
	        }
		 
	}

}
