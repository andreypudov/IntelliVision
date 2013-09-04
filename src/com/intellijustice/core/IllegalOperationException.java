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

package com.intellijustice.core;

/**
 * Signals that illegal operation on data occurred.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      IllegalOperationException.java
 * %date      07:00:00 PM, Sep 03, 2013
 */
public class IllegalOperationException extends Exception {

    private static final long serialVersionUID = 0x8a7c_9375_e0c5_30eaL;
    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellijustice.core.Manifest.NAME);

    /**
     * Constructs an {@code IllegalOperationException} with {@code null}
     * as its error detail message.
     */
    public IllegalOperationException() {
        super();
    }

    /**
     * Constructs an {@code IllegalOperationException} with the specified detail
     * message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method)
     */
    public IllegalOperationException(final String message) {
        super(message);
    }
}
