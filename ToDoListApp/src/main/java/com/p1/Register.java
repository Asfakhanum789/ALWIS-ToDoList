package com.p1;
import java.io.*;
import java.sql.*;

import com.db.Database;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Register")
public class Register extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String uname = req.getParameter("uname");
        String password = req.getParameter("pwd");
        String cpwd = req.getParameter("cpwd");

        
       

        try {
         
        	
        	 Connection con=Database.getconnection();
        	 

            PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO user (fullname, email, username, password) VALUES (?, ?, ?, ?)"
            );

            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, uname);
            pstmt.setString(4, password);  

            int x = pstmt.executeUpdate();

            if (x > 0) {
            	writer.println("<script>alert('Registered Sucessful'); window.location='Login.html';</script>");
            } else {
                writer.println("Registration failed.");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            writer.println("Error: " + e.getMessage());
        }
    }
}
