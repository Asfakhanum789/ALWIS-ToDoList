 package com.p1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

 
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	 
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 
		 HttpSession session=req.getSession(false);
		 
			 int userId= (int) session.getAttribute("userid");
			 System.out.println(userId +"Removed");
			  
			 resp.sendRedirect("Login.html");
			 
		 
		  
	}

	 
 

}
