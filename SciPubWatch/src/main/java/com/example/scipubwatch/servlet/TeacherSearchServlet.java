package com.example.scipubwatch.servlet;

import com.example.scipubwatch.entities.Teacher;
import com.example.scipubwatch.logic.TeacherLogic;
import com.example.scipubwatch.logic.TeacherLogicImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/teachersearch")
public class TeacherSearchServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String name = request.getParameter("name");

        TeacherLogic teacherLogic = new TeacherLogicImpl();
        List<Teacher> teachers = teacherLogic.findTeacher(name);
        request.setAttribute("teachers", teachers);

        request.getRequestDispatcher("/findteacher").forward(request, response);
    }
}
