 package com.p1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import java.io.*;
import java.sql.*;

import com.db.Database;

@WebServlet("/AddTodoServlet")
public class AddTodoServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

         
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String dueDate = req.getParameter("dueDate");

        try {
            
          
        	 Connection con=Database.getconnection();
            HttpSession session = req.getSession(false);
            int userId= (int) session.getAttribute("userid");
            String check="select count(*) from todos where user_id=? and title=?";
            PreparedStatement checkstmt=con.prepareStatement(check);
            checkstmt.setInt(1, userId);
            checkstmt.setString(2, title);
           ResultSet rs=checkstmt.executeQuery();
           if(rs.next() &&rs.getInt(1)>0) {
        	   out.println("<script>alert('ToDo with List Already exists'); window.location='dashboard.jsp';</script>");
               
           }
           
           else {
            PreparedStatement pstmt = con.prepareStatement(
                "INSERT INTO todos (user_id, title, discription, duedate, status) VALUES (?, ?, ?, ?, ?)");

            pstmt.setInt(1, userId);
            pstmt.setString(2, title);
            pstmt.setString(3, description);
            pstmt.setString(4, dueDate);
            pstmt.setBoolean(5, false);  

            int x = pstmt.executeUpdate();

            if (x > 0) {
              
            	out.println("<script>alert('Sucessfully Added'); window.location='dashboard.jsp';</script>");
                
                 
            } else {
                out.println("<h3>Failed to add todo item.</h3>");
            }

            con.close();
        } 
        }catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
            e.printStackTrace();
        }
     
    }
}
