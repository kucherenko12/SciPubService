package com.example.scipubwatch.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/changescrapetype")
public class ChangeScrapeTypeServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        request.getSession().setAttribute("scrapetype", request.getParameter("scrapetype"));
        response.sendRedirect(request.getContextPath() + "/findteacher");
        //if(request.getSession().getAttribute("scrapetype").equals("citations"))
        //{
        //    request.getSession().setAttribute("scrapetype", "data");
        //}

    }
}
