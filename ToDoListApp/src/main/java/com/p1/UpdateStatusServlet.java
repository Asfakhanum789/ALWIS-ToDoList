 package com.p1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import com.db.Database;
@WebServlet("/UpdateStatusServlet")
public class UpdateStatusServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String title = req.getParameter("title");
        String discription = req.getParameter("description");
        String duedate = req.getParameter("dueDate");

        try {
                 	 Connection con=Database.getconnection();
            PreparedStatement pstmt = con.prepareStatement("UPDATE todos SET title=?, discription=?, duedate=? WHERE id=?");
            pstmt.setString(1, title);
            pstmt.setString(2, discription);
            pstmt.setString(3, duedate);
            pstmt.setInt(4, id);

            pstmt.executeUpdate();
             

        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.sendRedirect("dashboard.jsp");
    }
}