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

import com.onlineathletics.util.Messages;
import com.onlineathletics.util.Validator;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * The authentication servlet provides ability to authenticate a user.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      LoginForm.java
 * %date      09:40:00 PM, Feb 11, 2014
 */
@Named("loginForm")
@SessionScoped
public class LoginForm implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.onlineathletics.core.Manifest.NAME);
    
    private static final String HOME_PAGE_URL = "faces/index.xhtml";
    
    private boolean usernameValid = true;
    private boolean passwordValid = true;
    
    private String username;
    private String password;
    private String webpage;
    
    @PostConstruct
    public void init() {
        final FacesContext context = FacesContext.getCurrentInstance();
        
        webpage = (String) context.getExternalContext().getRequestMap()
                .get(RequestDispatcher.FORWARD_REQUEST_URI);
    }
    
    public void login() {
        final FacesContext       context = FacesContext.getCurrentInstance();
        final HttpServletRequest request = (HttpServletRequest) 
                context.getExternalContext().getRequest();
        
        try {
            request.login(username, password);
            context.getExternalContext().redirect(webpage);
        } catch (final ServletException | IOException e) {
            //context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Bad login! " + webpage, null));
        }
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public void setPassword(final String password) {
        this.password = password;
    }
    
    /**
     * Returns the account name validation status.
     * 
     * @return the account name validation status.
     */
    public boolean isUsernameValid() {
        return usernameValid;
    }
    
    /**
     * Returns the account password validation status.
     * 
     * @return the account password validation status.
     */
    public boolean isPasswordValid() {
        return passwordValid;
    }
    
    /**
     * Validates the contact form elements and returns the form to the browser
     * if any elements contains invalid values.
     * 
     * @param event the source of a system event.
     */
    public void validate(final ComponentSystemEvent event) {
        final FacesContext context   = FacesContext.getCurrentInstance();
        final UIComponent  component = event.getComponent();
        
        /* contact name field */
        final UIInput username = (UIInput) component.findComponent("j_username");
        final UIInput password = (UIInput) component.findComponent("j_password");
        
        final FacesMessage nameMissing   = Messages.getMessage("name_is_missing");
        final FacesMessage nameOverlong  = Messages.getMessage("name_is_overlong");
        
        final FacesMessage emailMissing  = Messages.getMessage("email_is_missing");
        final FacesMessage emailOverlong = Messages.getMessage("email_is_overlong");
        
        usernameValid  = Validator.validateText(username, 
                Validator.CONTACT_NAME_MAX_LENGTH, nameMissing, nameOverlong, 
                context);
        passwordValid = Validator.validateText(password,
                Validator.CONTACT_NAME_MAX_LENGTH, emailMissing, emailOverlong, 
                context);
        
        /* return submit request with an error description */
        if ((usernameValid == false) || (passwordValid == false)) {
            context.renderResponse();
        }
    }
}
