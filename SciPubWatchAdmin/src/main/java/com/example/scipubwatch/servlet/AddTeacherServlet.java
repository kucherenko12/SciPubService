package com.example.scipubwatch.servlet;

import com.example.scipubwatch.logic.TeacherLogic;
import com.example.scipubwatch.logic.TeacherLogicImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/adminmenu/add")
public class AddTeacherServlet extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        request.setCharacterEncoding("UTF-8");
        String type = request.getParameter("type").toString();
        String[] surnameNamePatronym = new String[]
                {request.getParameter("surname").toString(),
                request.getParameter("name").toString(),
                request.getParameter("patronym").toString()};
                //request.getRequestDispatcher("/index.jsp").forward(request, response);

        String linkgooglescholar = null;
        String linkscopus = null;
        if(request.getParameter("linkgooglescholar") != null)
        {
            linkgooglescholar = request.getParameter("linkgooglescholar").toString();
        }
        if(request.getParameter("linkscopus") != null)
        {
            linkscopus = request.getParameter("linkscopus").toString();
        }
        TeacherLogic teacherLogic = new TeacherLogicImpl();
        if(teacherLogic.addTeacher(type, surnameNamePatronym, linkgooglescholar, linkscopus) == true)
        {
            request.getRequestDispatcher("/WEB-INF/jsp/success.jsp").forward(request, response);
        }
        else { request.getRequestDispatcher("/WEB-INF/jsp/fail.jsp").forward(request, response); }
    }
}
