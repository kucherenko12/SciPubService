package com.example.scipubwatch.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/loginpost")
public class LoginServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        // read form fields
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("username: " + username);
        System.out.println("password: " + password);

        // do some processing here...
        //if(LoginLogic.IsUserValid(username, password) == true)
        if(username.equals("a") && password.equals("a"))
        {
            //RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/forwarded");
            //dispatcher.forward(request, response);

            HttpSession session=request.getSession();
            session.setAttribute("username", username);

            response.sendRedirect(request.getContextPath() + "/adminmenu");
        }
        else
        {
            request.getRequestDispatcher("/WEB-INF/jsp/invalidlogin.jsp").forward(request, response);
        }
    }
}