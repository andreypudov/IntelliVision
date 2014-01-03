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

import javax.enterprise.context.RequestScoped;
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
    
    /**
     * The form submit handler
     */
    public void submit() {
        email = email;
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
     * Sets the contact name value.
     * 
     * @param name the contact name value.
     */
    public void setName(final String name) {
        this.name = name;
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
     * Sets the contact e-mail address.
     * 
     * @param email the contact e-mail address.
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * Returns the contact message text.
     * 
     * @return the contact message text.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the contact message text.
     * 
     * @param message the contact message text.
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}
