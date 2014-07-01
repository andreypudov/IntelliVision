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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author andrey
 */
public class TransliteratorTest {
    
    public TransliteratorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class Transliterator.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance");
        String[][] pairs = {{"en", "ru"}, {"ru", "en"}};
        
        for (String[] pair : pairs) {
            Transliterator result = Transliterator.getInstance(pair[0], pair[1]);
            if (result == null) {
                fail(String.format("Can't create transliterator from %1$s to %2$s", pair[0], pair[1]));
            }
        }
    }

    /**
     * Test of transliterate method, of class Transliterator.
     */
    @Test
    public void testTransliterate() {
        System.out.println("transliterate");
        String[][] units = {
            {"en", "ru", "Andrei", "Андреи"},
            {"ru", "en", "Андрей", "Andrey"}};
        
        for (String[] unit : units) {
            Transliterator transliterator = Transliterator.getInstance(unit[0], unit[1]);
            if (transliterator == null) {
                fail(String.format("Can't create transliterator from %1$s to %2$s", unit[0], unit[1]));
            }
            
            String result = transliterator.transliterate(unit[2]);
            assertEquals(unit[3], result);
        }
    }
    
}
