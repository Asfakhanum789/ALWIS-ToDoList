package com.p1;
import java.io.*;
import java.sql.*;

import com.db.Database;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Login1")
public class Login1 extends HttpServlet {
   protected void doPost(HttpServletRequest req, HttpServletResponse resp)
           throws ServletException, IOException {

       resp.setContentType("text/html");
       PrintWriter writer = resp.getWriter();

        
       String email = req.getParameter("email");
       String password = req.getParameter("pwd");
    



       try {
          
    	   
    	   Connection con=Database.getconnection();

           PreparedStatement pstmt = con.prepareStatement(
               "Select * from user where email=? and password=?"
           );

           
           pstmt.setString(1, email);
           pstmt.setString(2, password); 

           ResultSet res = pstmt.executeQuery();

           if(res.next()==true) {
           	HttpSession session=req.getSession();
           	session.setAttribute("userid", res.getInt("id"));
           	 
           	writer.println("<script>alert('Login Sucessful'); </script>"); 
           	resp.sendRedirect("dashboard.jsp");
                
           }  
           else {
           	
           	writer.println("<script>alert('Invalid Username or password'); window.location='Login.html';</script>");
               
           	
           }
           con.close();
       } catch (Exception e) {
           e.printStackTrace();
           writer.println("Error: " + e.getMessage());
       }
   }
}
