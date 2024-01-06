package com.example.scipubwatch.servlet;

import com.example.scipubwatch.entities.Publication;
import com.example.scipubwatch.entities.Teacher;
import com.example.scipubwatch.logic.PublicationManipulation;
import com.example.scipubwatch.logic.TeacherLogic;
import com.example.scipubwatch.logic.TeacherLogicImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/viewpublications")
public class ViewPublicationsServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        // read form fields
        String id = request.getParameter("id");
        String type = request.getParameter("type");

        // do some processing here...
        TeacherLogic teacherLogic = new TeacherLogicImpl();

        if( teacherLogic.getTeacherById(Integer.parseInt(id)) != null && (type.equals("fast") || type.equals("slow")) )
        {
            Teacher teacher = teacherLogic.getTeacherById(Integer.parseInt(id));

            // Записать что бы показывать последнего просмотренного преподавателя
            request.getSession().setAttribute("lastteacher", teacher);
            request.setAttribute("teacher", teacher);

            String scrapeType = request.getSession().getAttribute("scrapetype").toString();
            PublicationManipulation publicationManipulation = new PublicationManipulation();
            List<Publication> publications = null;
            if(type.equals("fast"))
            {
                publications = publicationManipulation.findPublicationsFast(teacher, scrapeType);
            }
            else if(type.equals("slow"))
            {
                publications = publicationManipulation.findPublications(teacher, scrapeType);
            }
            request.setAttribute("publications", publications);
            request.getRequestDispatcher("/WEB-INF/jsp/viewpublications.jsp").forward(request, response);
        }
        else
        {
            request.getRequestDispatcher("/WEB-INF/jsp/viewpublicationserror.jsp").forward(request, response);
        }
    }

}