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

package com.intellijustice.core;

import com.intellijustice.iso.LanguageCodes;

/**
 * The language entity representation.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Language.java
 * %date      10:30:00 AM, Nov 11, 2014
 */
public class Language {
    
    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellijustice.core.Manifest.NAME);
    
    private final String code;
    private final String name;
    private final String script;
    
    private final int index;
 
    /**
     * Constructs language entity using language code by ISO 639-1.
     * 
     * @param code the language numeric code by ISO 639-1.
     */
    public Language(final String code) {
        this.code   = code;
        this.name   = LanguageCodes.getName(code);
        this.script = LanguageCodes.getScript(code);
        this.index  = LanguageCodes.getIndex(code);
    }
    
    /**
     * Constructs language entity using internal language index.
     * 
     * @param index the internal language index.
     */
    public Language(final int index) {
        this.code   = LanguageCodes.getCode(index);
        this.name   = LanguageCodes.getName(code);
        this.script = LanguageCodes.getScript(code);
        this.index  = index;
    }
    
    /**
     * Returns the two letters language code.
     * 
     * @return the two letters language code.
     */
    public String getCode() {
        return code;
    }
    
    /**
     * Returns the complete language name in English.
     * 
     * @return the complete language name in English.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the script name of the language in English.
     * 
     * @return the script name of the language in English.
     */
    public String getScript() {
        return script;
    }
    
    /**
     * Returns language id in internal/adopted standard.
     * 
     * @return the language id in internal/adopted standard.
     */
    public int getIndex() {
        return index;
    }
    
    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj the reference object with which to compare.
     * @return    true if this object is the same as the obj argument;
     *            false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        return this.code == ((Language) obj).code;
    }
    
    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return code.hashCode();
    } 
    
    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(126);

        /* ru, Russian, CYRILLIC, 133 */
        builder.append(code).append(", "
                ).append(name).append(", "
                ).append(script).append(", "
                ).append(index);
        
        return builder.toString();
    }
}
