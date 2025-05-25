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

 
@WebServlet("/status")
public class status extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String statusParam = req.getParameter("status");
        boolean status = "true".equalsIgnoreCase(statusParam);

        try {
           /* Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ToDoList", "root", "Asfakhanum@2002");
*/
        	 Connection con=Database.getconnection();
            PreparedStatement pstmt = con.prepareStatement("UPDATE todos SET status=? WHERE id=?");
            pstmt.setBoolean(1, status);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();

             

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        resp.sendRedirect("dashboard.jsp");
    }
}