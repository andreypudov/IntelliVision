/*
 * IntelliJustice Intelligent Referee Assistant System
 *
 * The MIT License
 *
 * Copyright 2011-2014 Andrey Pudov.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.onlineathletics.web;

import com.sun.enterprise.security.ee.auth.login.ProgrammaticLogin;
import java.io.IOException;
import java.util.logging.Level;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The authentication servlet provides ability to authenticate a user.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      LoginForm.java
 * %date      09:40:00 PM, Feb 11, 2014
 */
@Named("loginForm")
public class LoginForm extends HttpServlet {
    
    private static final long serialVersionUID = 0x638c_498a_a92e_12adL;
    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.onlineathletics.core.Manifest.NAME);
    
    @Override
    protected void doGet(final HttpServletRequest request, 
            final HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
    }
    
    @Override
    protected void doPost(final HttpServletRequest request, 
            final HttpServletResponse response)
            throws ServletException, IOException {
        final ProgrammaticLogin login = new ProgrammaticLogin();
        
        try {
            final Boolean authneticated = login.login(
                    request.getParameter("j_username"), 
                    request.getParameter("j_password").toCharArray(), 
                    "OnlineAthleticsRealm", request, response, true);
            
            if ((authneticated != null) && (authneticated.booleanValue())) {
                response.sendRedirect("index.xhtml");
                return;
            }
        } catch (final Exception e) {
            LOG.log(Level.SEVERE, 
                    "Authentication servlet failed with follwing error: {0}", 
                    e.getMessage());
        }
        
        response.sendRedirect("login.xhtml?auth=failed");
    }
    
    @Override
    public String getServletInfo() {
        return "Login servlet.";
    }
}
