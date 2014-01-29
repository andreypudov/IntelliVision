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
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;

/**
 * The managed bean implements contact us form functionality.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      ContactForm.java
 * %date      05:50:00 PM, Jan 03, 2014
 */
@Named("contactForm")
@RequestScoped
public class ContactForm {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.onlineathletics.core.Manifest.NAME);
    
    private String name;
    private String email;
    private String message;
    
    private boolean nameValid    = true;
    private boolean emailValid   = true;
    private boolean messageValid = true;
    
    /**
     * The form submit handler.
     * 
     * @return the thank you page address.
     */
    public String submit() {
        return "thankyou";
    }

    /**
     * Returns the contact name value.
     * 
     * @return the contact name value.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the contact e-mail address.
     * 
     * @return the contact e-mail address.
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Returns the contact message text.
     * 
     * @return the contact message text.
     */
    public String getMessage() {
        return message;
    }
    
    public boolean isNameValid() {
        return nameValid;
    }
    
    public boolean isEmailValid() {
        return emailValid;
    }
    
    public boolean isMessageValid() {
        return messageValid;
    }

    /**
     * Sets the contact name value.
     * 
     * @param name the contact name value.
     */
    public void setName(final String name) {
        this.name = name;
    }
    
    /**
     * Sets the contact e-mail address.
     * 
     * @param email the contact e-mail address.
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Sets the contact message text.
     * 
     * @param message the contact message text.
     */
    public void setMessage(final String message) {
        this.message = message;
    }
    
    public void setNameValid(final boolean status) {
        nameValid = status;
    }
    
    public void setEmailValid(final boolean status) {
        emailValid = status;
    }
    
    public void setMessagealid(final boolean status) {
        messageValid = status;
    }
    
    public void validate(final ComponentSystemEvent event) {
        final FacesContext context   = FacesContext.getCurrentInstance();
        final UIComponent  component = event.getComponent();
        
        /* contact name field */
        final UIInput contactInputName    = (UIInput) component.findComponent("contactInputName");
        final UIInput contactInputEmail   = (UIInput) component.findComponent("contactInputEmail");
        final UIInput contactInputMessage = (UIInput) component.findComponent("contactTextArea");
        
        final FacesMessage nameMissing   = Messages.getMessage("name_is_missing");
        final FacesMessage nameOverlong  = Messages.getMessage("name_is_overlong");
        
        final FacesMessage emailMissing  = Messages.getMessage("email_is_missing");
        final FacesMessage emailOverlong = Messages.getMessage("email_is_overlong");
        final FacesMessage emailInvalid  = Messages.getMessage("email_is_invalid"); 
        
        final FacesMessage messageMissing  = Messages.getMessage("message_is_missing");
        final FacesMessage messageOverlong = Messages.getMessage("message_is_overlong");
        
        nameValid  = Validator.validateText(contactInputName, 
                Validator.CONTACT_NAME_MAX_LENGTH, nameMissing, nameOverlong, 
                context);
        emailValid = Validator.validateEmail(contactInputEmail,
                Validator.CONTACT_EMAIL_MAX_LENGTH, emailMissing, emailOverlong, 
                emailInvalid, context);
        messageValid = Validator.validateText(contactInputMessage, 
                Validator.CONTACT_MESSAGE_MAX_LENGTH, messageMissing, 
                messageOverlong, context);
        
        /* return submit request with an error description */
        if ((nameValid == false) || (emailValid == false) 
                || (messageValid == false)) {
            context.renderResponse();
        }
    }
}