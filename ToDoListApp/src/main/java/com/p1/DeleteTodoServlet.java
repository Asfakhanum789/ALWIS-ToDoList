 package com.p1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import com.db.Database;

 
 
@WebServlet("/DeleteTodoServlet")
public class DeleteTodoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        HttpSession session=req.getSession(false);
		 
		 int userId= (int) session.getAttribute("userid");
        try {
           
        	 Connection con=Database.getconnection();
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM todos WHERE id = ? ");
            pstmt.setInt(1, id);

            pstmt.executeUpdate();
           

        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.sendRedirect("dashboard.jsp");
    }
}
