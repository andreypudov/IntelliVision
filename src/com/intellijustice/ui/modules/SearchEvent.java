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

package com.intellijustice.ui.modules;

import java.util.EventObject;

/**
 * An event represents search action.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      SearchEvent.java
 * %date      12:40:00 AM, Feb 09, 2013
 */
public class SearchEvent extends EventObject {

    private static final long serialVersionUID = 0x6db9_9ae3_2d75_b68cL;
    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
              com.intellijustice.core.Manifest.NAME);

    /* the search pattern */
    private final String pattern;

    public SearchEvent(final String pattern) {
        super(pattern);

        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
