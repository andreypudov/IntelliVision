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

/**
 * Database exception numeration provides correlation between database signal
 * code to specified type of exception.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      DatabaseExceptionType.java
 * %date      09:20:00 PM, Mar 26, 2014
 */
public enum DatabaseExceptionType {
    INVALID_ARGUMENT_EXCEPTION(60_001),
    
    AUTHENTICATION_FAILED_EXCEPTION(60_021),
    ACCOUNT_DOES_NOT_EXISTS_EXCEPTION(60_022),
    PERMISSIONS_DENIED_EXCEPTION(60_031),
    
    ATHLETE_ENTRY_ALREADY_EXISTS_EXCEPTION(60_101),
    ATHLETE_ENTRY_DOES_NOT_EXISTS_EXCEPTION(60_102),
    ATHLETE_THE_SAME_AS_REQUESTED_TO_CHANGE_EXCEPTION(60_103),
    
    UNDEFINED_DATABASE_EXCEPTION(0);
    
    /* the value of the error code */
    private final int code;

    /**
     * Creates database exception representation by given error code.
     * 
     * @param code the value of the error code.
     */
    DatabaseExceptionType(final int code) {
        this.code = code;
    }
    
    /**
     * Returns the supported version number.
     *
     * @param code the value of the error code.
     * @return     the database exception type.
     */
    public static DatabaseExceptionType getType(final int code) {
        for (DatabaseExceptionType entry : DatabaseExceptionType.values()) {
            if (entry.code == code) {
                return entry;
            }
        }

        return UNDEFINED_DATABASE_EXCEPTION;
    }
}
