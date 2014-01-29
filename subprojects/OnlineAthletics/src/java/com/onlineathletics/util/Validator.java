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

package com.onlineathletics.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

/**
 * The collection of validation methods.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Validator.java
 * %date      10:15:00 PM, Jan 12, 2014
 */
public class Validator {
    
    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.onlineathletics.core.Manifest.NAME);
    
    public static final int CONTACT_NAME_MAX_LENGTH    = 255;
    public static final int CONTACT_EMAIL_MAX_LENGTH   = 255;
    public static final int CONTACT_MESSAGE_MAX_LENGTH = 4096;
    
    private static final String EMAIL_PATTERN = 
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Z-a-z]{2,})$";
    
    /* do not let anyone instantiate this class */
    private Validator() {
    }
    
    /**
     * Validates given text field, and returns true if a text field value is not
     * a null and contains at least one character. Otherwise returns false and 
     * adds an error message for the text field.
     * 
     * @param field    the text field to validate.
     * @param length   the maximum length of the text field value.
     * @param missing  the error message for missing value.
     * @param overlong the error message for message excited maximum length.
     * @param context  the execution context.
     * 
     * @return         true if text field value is not empty, false otherwise.
     */
    public static boolean validateText(final UIInput field, final int length,
            final FacesMessage missing, final FacesMessage overlong, 
            final FacesContext context) {
        
        return !(stringIsEmpty(field, missing, context)
                || stringIsOverlong(field, length, overlong, context));
    }
    
    /**
     * Validates given text field, and returns true if a text field value is 
     * a correct email address. Otherwise returns false and 
     * adds an error message for the text field.
     * 
     * @param field    the text field to validate.
     * @param length   the maximum length of the text field value.
     * @param missing  the error message for missing value.
     * @param overlong the error message for message excited maximum length.
     * @param invalid  the error message for invalid value.
     * @param context  the execution context.
     * 
     * @return         true if text field value is a correct email address,
     *                 false otherwise.
     */
    public static boolean validateEmail(final UIInput field, final int length,
            final FacesMessage missing, final FacesMessage overlong,
            final FacesMessage invalid, final FacesContext context) {
        
        return !(stringIsEmpty(field, missing, context)
                || !isValidEmail(field, invalid, context)
                || stringIsOverlong(field, length, overlong, context));
    }
    
    /**
     * Validates specified input field and returns true if input value is empty. 
     * 
     * @param field   the text field to validate.
     * @param message the error message.
     * @param context the execution context.
     * 
     * @return        true if value is empty and false otherwise.
     */
    private static boolean stringIsEmpty(final UIInput field, 
            final FacesMessage message, final FacesContext context) {
        final String value = (String) field.getLocalValue();
        
        if ((value == null) || (value.isEmpty())) {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(field.getClientId(), message);
            
            return true;
        }
        
        return false;
    }
    
    /**
     * Validates specified input field and returns true if input value string is
     * longer than specified length.
     * 
     * @param field    the text field to validate.
     * @param length   the maximum length of the text field value.
     * @param message  the error message.
     * @param context  the execution context.
     * 
     * @return         true if value is longer than specified length, 
     *                 and false otherwise.
     */
    private static boolean stringIsOverlong(final UIInput field, 
            final int length, final FacesMessage message, 
            final FacesContext context) {
        final String value = (String) field.getLocalValue();
        
        if (value.length() > length) {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(field.getClientId(), message);
            
            return true;
        }
        
        return false;
    }
    
    /**
     * Validates specified input field and returns true if input value string is
     * valid email address. 
     * 
     * @param field   the text field to validate.
     * @param message the error message.
     * @param context the execution context.
     * 
     * @return        true if value is valid email address, 
     *                and false otherwise.
     */
    private static boolean isValidEmail(final UIInput field, 
            final FacesMessage message, final FacesContext context) {
        final String  value   = (String) field.getLocalValue();
        
        final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        final Matcher matcher = pattern.matcher(value);
        
        if (matcher.matches() == false) {
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(field.getClientId(), message);
            
            return false;
        }
        
        return true;
    }
}
