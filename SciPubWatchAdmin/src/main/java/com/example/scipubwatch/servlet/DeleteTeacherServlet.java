package com.example.scipubwatch.servlet;

import com.example.scipubwatch.logic.TeacherLogic;
import com.example.scipubwatch.logic.TeacherLogicImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/adminmenu/delete")
public class DeleteTeacherServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        int id = 0;
        try { id = Integer.parseInt(request.getParameter("id")); }
        catch (Exception exception)
        {
            exception.printStackTrace();
            request.getRequestDispatcher("/WEB-INF/jsp/fail.jsp").forward(request, response);
        }
        TeacherLogic teacherLogic = new TeacherLogicImpl();
        if(teacherLogic.deleteTeacher(id) == true)
        {
            request.getRequestDispatcher("/WEB-INF/jsp/success.jsp").forward(request, response);
        }
        else { request.getRequestDispatcher("/WEB-INF/jsp/fail.jsp").forward(request, response); }
    }
}
