<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<%
    int userId = (int) session.getAttribute("userid");
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    String username = "";
    String email = "";
    String fullname = "";
    int total = 0;
    int completed = 0;
    int incompleted = 0;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ToDoList", "root", "Asfakhanum@2002");

        // Get user info
        pstmt = con.prepareStatement("SELECT fullname,  email, username  FROM user WHERE id = ?");
        pstmt.setInt(1, userId);
        rs = pstmt.executeQuery();
        if (rs.next()) {
        	fullname=rs.getString("fullname");
            username = rs.getString("username");
            email = rs.getString("email");
             
        }
        rs.close();
        pstmt.close();
 
        pstmt = con.prepareStatement("SELECT COUNT(*) FROM todos WHERE user_id = ?");
        pstmt.setInt(1, userId);
        rs = pstmt.executeQuery();
        if (rs.next()) total = rs.getInt(1);
        rs.close();
        pstmt.close();

        
        pstmt = con.prepareStatement("SELECT COUNT(*) FROM todos WHERE user_id = ? AND status = 1");
        pstmt.setInt(1, userId);
        rs = pstmt.executeQuery();
        if (rs.next()) completed = rs.getInt(1);
        rs.close();
        pstmt.close();

        // Incompleted
        pstmt = con.prepareStatement("SELECT COUNT(*) FROM todos WHERE user_id = ? AND status = 0");
        pstmt.setInt(1, userId);
        rs = pstmt.executeQuery();
        if (rs.next()) incompleted = rs.getInt(1);

    } catch (Exception e) {
        out.println("Error: " + e.getMessage());
    } finally {
        if (rs != null) rs.close();
        if (pstmt != null) pstmt.close();
        if (con != null) con.close();
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>User Profile</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<div class="navbar">
    <a href="dashboard.jsp">Todo List</a>
    <a href="profile.jsp">Profile</a>
    <a href="LogoutServlet">Logout</a>
</div>

 
<form>
<h2>Your Profile</h2><br>
    <p><strong>Username:</strong> <%= username %></p>
    <p><strong>Email:</strong> <%= email %></p>
    <p><strong>Full Name:</strong> <%= fullname %></p>
    
    <p><strong>Total Todos:</strong> <%= total %></p>
    <p><strong>Completed:</strong> <%= completed %></p>
    <p><strong>Incomplete:</strong> <%= incompleted %></p>
</form>

</body>
</html>