<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.sql.Connection" %>
    <%@ page import="java.sql.*" %>
    
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    String title = "", description = "", dueDate = "";

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ToDoList", "root", "Asfakhanum@2002");

        pstmt = con.prepareStatement("SELECT * FROM todos WHERE id = ?");
        pstmt.setInt(1, id);
        rs = pstmt.executeQuery();

        if (rs.next()) {
            title = rs.getString("title");
            description = rs.getString("description");
            dueDate = rs.getString("due_date");
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (rs != null) rs.close();
        if (pstmt != null) pstmt.close();
        if (con != null) con.close();
    }
     
%>

<!DOCTYPE html>
<html>
<head>
    <title>Edit Todo</title>
    <link rel="stylesheet" href="style.css" />
</head>
<body>
<div class="navbar">
    <a href="Login.html">Login</a>
    <a href="register.html">Register</a>
    <a href="dashboard.html">Todo List</a>
    <a href="profile.html">Profile</a>
    <a href="LogoutServlet">Logout</a>
  </div>
     
    <form action="UpdateStatusServlet" method="post">
    <h2>Edit Todo</h2>
        <input type="hidden" name="id" value="<%= id %>">
        Title: <input type="text" name="title" value="<%= title %>"><br><br>
        Description: <input type="text" name="description" value="<%= description %>"><br><br>
        Due Date: <input type="date" name="dueDate" value="<%= dueDate %>"><br>
        <input type="submit" value="Update">
    </form>
</body>
</html>