package com.example.scipubwatch.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/findteacher")
public class FindTeacherServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        if(request.getSession().getAttribute("scrapetype") == null)
        {request.getSession().setAttribute("scrapetype", "citations");}
        request.getRequestDispatcher("/WEB-INF/jsp/findteacher.jsp").forward(request, response);
    }
    /// науковий ступінь і без апострофа, фільтпція за роками h індекс
}
