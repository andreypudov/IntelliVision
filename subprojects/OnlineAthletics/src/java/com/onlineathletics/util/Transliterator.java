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

import com.intellijustice.iso.LanguageCodes;
import java.util.Collections;
import java.util.List;

/**
 * The wrapper for ICU transliteration library. Provides ability to create 
 * transliterator by specifying source and target language names.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      AthletesInputForm.java
 * %date      05:50:00 PM, Apr 05, 2014
 */
public class Transliterator {
    
    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellijustice.core.Manifest.NAME);
    
    private static final List<String> TRANSFORMS;
    
    private final com.ibm.icu.text.Transliterator transliterator;
    
    static {
        TRANSFORMS = Collections.list(com.ibm.icu.text.Transliterator.getAvailableIDs());
    }
    
    /**
     * Constructs an instance of transliterator.
     * 
     * @param source the short name of the source language.
     * @param target the short name of the target language.
     * 
     * @throws Exception a source of the exception.
     */
    private Transliterator(final String source, final String target) throws Exception {
        final String transform = getTransform(source, target);
        if (transform == null) {
            throw new Exception("Transform doesn't exists for given source and target languages.");
        }
        
        transliterator = com.ibm.icu.text.Transliterator.getInstance(transform);
    }
    
    /**
     * Returns an instance of transliterator or null value if transliterator 
     * can no be created for specified source and target languages.
     * 
     * @param source the short name of the source language.
     * @param target the short name of the target language.
     * 
     * @return       the instance of transliterator.
     */
    public static Transliterator getInstance(final String source, final String target) {
        try {
            return new Transliterator(source, target);
        } catch (final Exception e) {
            return null;
        }
    }
    
    /**
     * Transliterates given string value.
     * 
     * @param value the string value to transliterate.
     * 
     * @return      the value of transliterated string.
     */
    public String transliterate(final String value) {
        return transliterator.transliterate(value);
    }
    
    /**
     * Returns an ID of transform for given source and target languages.
     * 
     * @param source the short name of the source language.
     * @param target the short name of the target language.
     * 
     * @return       the ID of the transform.
     */
    private String getTransform(final String source, final String target) {
        final String sourceFull = LanguageCodes.getName(source);
        final String targetFull = LanguageCodes.getName(target);

        String transform;

        /* search for full name transform */
        transform = sourceFull + "-" + targetFull;
        transform = transformIsExists(transform);
        if (transform != null) {
            return transform;
        }

        /* search for full name with Latin replacement */
        transform = (sourceFull.equals("English") ? "Latin" : sourceFull) + "-" +
            (targetFull.equals("English") ? "Latin" : targetFull);
        transform = transformIsExists(transform);
        if (transform != null) {
            return transform;
        }

        return null;
    }
    
    /**
     * Returns a value of transform for given transform pattern or null, if
     * transform is not present.
     * 
     * @param value the value of transform pattern to search.
     * 
     * @return      the value of transform or null, if transform is not present.
     */
    private String transformIsExists(final String value) {
        for (final String entry : TRANSFORMS) {
            if (entry.startsWith(value)) {
                return entry;
            }
        }

        return null;
    }
}
