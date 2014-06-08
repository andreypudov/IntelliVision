/*
 * IntelliJustice Intelligent Referee Assistant System
 *
 * The MIT License
 *
 * Copyright 2011-2013 Andrey Pudov.
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

package com.onlineathletics.services;

import java.sql.SQLException;

/**
 * Represents the exception that is thrown when an alert method is called with 
 * an invalid argument.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      DatabaseException.java
 * %date      11:30:00 PM, Dec 25, 2013
 */
public class DatabaseException extends SQLException {
    
    private static final long serialVersionUID = 0xa5c3_e9ca_0d63_9a6dL;
    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.onlineathletics.core.Manifest.NAME);
    
    /* the type of the exception */
    private final DatabaseExceptionType type;
    
    /**
     * Constructs an {@code InvalidArgumentException} with {@code message}
     * as its error detail message.
     * 
     * @param code    the error code of the exception.
     * @param message the exception description information.
     */
    public DatabaseException(final int code, 
            final String message) {
        super(message);
        
        this.type = DatabaseExceptionType.getType(code);
    }
    
    /**
     * Return the type of the exception.
     * 
     * @return the type of the exception.
     */
    public DatabaseExceptionType getType() {
        return type;
    }
}