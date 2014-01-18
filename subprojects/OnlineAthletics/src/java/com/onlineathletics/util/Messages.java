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

import java.util.Locale;
import java.util.ResourceBundle;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 * The collection of validation methods.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Messages.java
 * %date      09:50:00 PM, Jan 16, 2014
 */
public class Messages {
    
    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.onlineathletics.core.Manifest.NAME);
    
    private static final String DEFAULT_MESSAGE_BUNDLE 
            = "com.onlineathletics.resources.properties.messages";
    
    /* do not let anyone instantiate this class */
    private Messages() {
    }
    
    /**
     * Returns the message value for specified message name.
     * 
     * @param key the name of the message to return.
     * @return    the message text.
     */
    public static FacesMessage getMessage(final String key) {
        final FacesContext   context = FacesContext.getCurrentInstance();
        final Locale         locale  = getLocale(context);
        final ResourceBundle bundle  = ResourceBundle.getBundle(
                DEFAULT_MESSAGE_BUNDLE, locale);

        return new FacesMessage(bundle.getString(key));
    }
    
    /**
     * Returns locale for rendering the message. If context doesn't provides
     * locale, default locale is returned.
     * 
     * @param context the current session context.
     * @return        the locale for rendering the message.
     */
    public static Locale getLocale(final FacesContext context) {
        final UIViewRoot root   = context.getViewRoot();
        final Locale     locale;
        
        if (root != null) {
            locale = root.getLocale();
            return (locale == null) ? Locale.getDefault() : locale;
        }
        
        return Locale.getDefault();
    }
}
