<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.*" %>
<%@ page import="javax.servlet.http.HttpSession" %>  
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
 
<%
 
int userId= (int) session.getAttribute("userid");
String filter=request.getParameter("filter");
String sort=request.getParameter("sort");
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

%>

<html>
<head><title>Your Dashboard</title></head>
<body>
    <h2>Add New Todo</h2>
    <form action="AddTodoServlet" method="post">
        Title: <input type="text" name="title" required><br>
        Description: <input type="text" name="description"><br>
        Due Date: <input type="date" name="dueDate"><br>
        <input type="submit" value="Add Todo">
    </form>

    <hr>

    <h2>Your Todo List</h2>
    <form action="LogoutServlet" method="get">
    <input type="submit" value="logout">
    </form>
    
   <h3>Filter and Sort Todos</h3>
<form method="get" action="dashboard.jsp">
    Show:
    <input type="radio" name="filter" value="all" <%= "all".equals(request.getParameter("filter")) || request.getParameter("filter") == null ? "checked" : "" %>> All
    <input type="radio" name="filter" value="completed" <%= "completed".equals(request.getParameter("filter")) ? "checked" : "" %>> Completed
    <input type="radio" name="filter" value="incompleted" <%= "incompleted".equals(request.getParameter("filter")) ? "checked" : "" %>> Incompleted

    Sort by:
    <input type="radio" name="sort" value="asc" <%= "asc".equals(request.getParameter("sort")) ? "checked" : "" %>> Due Date ↑
    <input type="radio" name="sort" value="desc" <%= "desc".equals(request.getParameter("sort")) ? "checked" : "" %>> Due Date ↓

    <input type="submit" value="Apply">
</form>
<hr>
    
    <table border="1">
        <tr>
            <th>Status</th>
            <th>Title</th>
            <th>Description</th>
            <th>Due Date</th>
            <th>Actions</th>
        </tr>

<%
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ToDoList", "root", "Asfakhanum@2002");
        String sql="SELECT * FROM todos WHERE user_id = ?";
        if ("completed".equals(filter)) {
            sql += " AND status = 1";
        } else if ("incompleted".equals(filter)) {
            sql += " AND status = 0";
        }

        if ("asc".equals(sort)) {
            sql += " ORDER BY duedate ASC";
        } else if ("desc".equals(sort)) {
            sql += " ORDER BY duedate DESC";
        }

        pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, userId);
        rs = pstmt.executeQuery();

        pstmt.setInt(1, userId);
        rs = pstmt.executeQuery();

        while (rs.next()) {
%>
        <tr>
            <td>
            
             <form action="status" method="post">
                <input type="hidden" name="id" value="<%= rs.getInt("id") %>">
                <input type="checkbox" name="status" value="true" <%= rs.getBoolean("status") ? "checked" : "" %> onchange="this.form.submit()">
            </form>
            
                
            </td>
            <td><%= rs.getString("title") %></td>
            <td><%= rs.getString("discription") %></td>
            <td><%= rs.getDate("duedate") %></td>
            <td>
                <form action="DeleteTodoServlet" method="post" style="display:inline;">
                    <input type="hidden" name="id" value="<%= rs.getInt("id") %>">
                    <input type="submit" value="Delete">
                </form>

                <form action="editTodo.jsp" method="get" style="display:inline;">
                    <input type="hidden" name="id" value="<%= rs.getInt("id") %>">
                    <input type="submit" value="Update">
                </form>
            </td>
        </tr>
<%
        }
    } catch (Exception e) {
        out.println("Error: " + e.getMessage());
    } finally {
        if (rs != null) rs.close();
        if (pstmt != null) pstmt.close();
        if (con != null) con.close();
    }
%>
    </table>
</body>
</html>
