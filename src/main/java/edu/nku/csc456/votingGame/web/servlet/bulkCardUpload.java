// BulkCardUpload.java
// for The Voting Game

package edu.nku.csc456.votingGame.web.servlet;

import java.io.*;

import javax.servlet.annotation.*;
import javax.servlet.http.*;
import javax.servlet.ServletException;

/**
 * Created by Angel on 11/4/15.
 */

@WebServlet("/upload")
@MultipartConfig
public class BulkCardUpload extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String question = request.getParameter("question"); // Retrieves <input type="text" name="question">
        Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
        String fileName = filePart.getSubmittedFileName();
        InputStream fileContent = filePart.getInputStream();
        // ... (do your job here)
    }
}
