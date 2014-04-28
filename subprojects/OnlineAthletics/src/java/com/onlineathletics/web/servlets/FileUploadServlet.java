
package com.onlineathletics.web.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/upload")
@MultipartConfig
public class FileUploadServlet extends HttpServlet {
    
    private static final long serialVersionUID = 0xc9ae_e934_0a4d_ee34L;
    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.onlineathletics.core.Manifest.NAME);
    
    @Override
    protected void doPost(final HttpServletRequest request, 
            final HttpServletResponse response) 
                throws ServletException, IOException {
        /* retrieves <input type="text" name="description"> */
        final String description = request.getParameter("description");
        
        /* retrieves <input type="file" name="file"> */
        final Part part = request.getPart("file");
        
        final String      name   = getFileName(part);
        final InputStream stream = part.getInputStream();
        // ... (do your job here)
        
        final String content = new BufferedReader(new InputStreamReader(stream)).readLine();
        LOG.severe(content);
    }
    
    private String getFileName(final Part part) {
        for (final String disposition : part.getHeader("content-disposition").split(";")) {
            if (disposition.trim().startsWith("filename")) {
                final String name = disposition.substring(
                        disposition.indexOf('=') + 1).trim().replace("\"", "");
                
                /* workaround for Microsoft Internet Explorer */
                return name.substring(name.lastIndexOf('/') + 1
                    ).substring(name.lastIndexOf('\\') + 1);
            }
        }
        
        return null;
    }
}
