package com.example.scipubwatch.servlet;

import com.example.scipubwatch.logic.DocumentLogic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/download3")
public class Download3Servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Boolean wait = DocumentLogic.GetDataCreateDocument(3);

        // Get PrintWriter object
        PrintWriter out = response.getWriter();
        // File name
        String docxName = "report.docx";
        // File path
        String docxPath = "C://MyTemp/";

        // Set the content type and header of the response.
        response.setContentType("application/msword");
        response.setHeader("Content-Disposition",
                "attachment; filename=\""
                        + docxName + "\"");

        // Get FileInputStream object to identify the path
        FileInputStream inputStream
                = new FileInputStream(docxPath + docxName);

        // Loop through the document and write into the
        // output.
        int in;
        while ((in = inputStream.read()) != -1) {
            out.write(in);
        }

        // Close FileInputStream and PrintWriter object
        inputStream.close();
        out.close();

        //////////////////////////////

        //private final int ARBITARY_SIZE = 1048;

        //@Override
        //protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        //    throws ServletException, IOException {

        //resp.setContentType("application/msword");
        //resp.setHeader("Content-disposition", "attachment; filename=report.docx");

        //try(InputStream in = req.getServletContext().getResourceAsStream("C://MyTemp/report.docx");
        //    OutputStream out = resp.getOutputStream()) {
        //
        //    byte[] buffer = new byte[ARBITARY_SIZE];
        //
        //    int numBytesRead;
        //    while ((numBytesRead = in.read(buffer)) > 0) {
        //        out.write(buffer, 0, numBytesRead);
        //    }
        //}
    }
}