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

package com.intellijustice.iso;

import java.util.Map;
import java.util.TreeMap;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class LanguageCodesTest {
    
    public LanguageCodesTest() {
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
     * Test of getIndex method, of class LanguageCodes.
     */
    @Test
    public void testGetIndex() {
        System.out.println("getIndex");
        
        Map<String, Integer> languages = new TreeMap<>();
        languages.put("ab", 0);
        languages.put("ar", 6);
        languages.put("cv", 30);
        languages.put("en", 40);
        languages.put("fr", 47);
        languages.put("el", 52);
        languages.put("pl", 126);
        languages.put("ru", 133);
        languages.put("sv", 152);
        languages.put("uk", 169);
        
        languages.entrySet().stream().forEach((entry) -> {
            String code   = entry.getKey();
            int    index  = entry.getValue();
            int    result = LanguageCodes.getIndex(code);
            
            assertEquals(index, result);
        });

        int expResult = 0;
    }

    /**
     * Test of getName method, of class LanguageCodes.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        
        Map<String, String> languages = new TreeMap<>();
        languages.put("ab", "Abkhaz");
        languages.put("ar", "Arabic");
        languages.put("cv", "Chuvash");
        languages.put("en", "English");
        languages.put("fr", "French");
        languages.put("el", "Greek");
        languages.put("pl", "Polish");
        languages.put("ru", "Russian");
        languages.put("sv", "Swedish");
        languages.put("uk", "Ukrainian");
        
        languages.entrySet().stream().forEach((entry) -> {
            String code   = entry.getKey();
            String name   = entry.getValue();
            String result = LanguageCodes.getName(code);
            
            assertEquals(name, result);
        });
    }

    /**
     * Test of getScript method, of class LanguageCodes.
     */
    @Test
    public void testGetScript() {
        System.out.println("getScript");
        
        Map<String, String> languages = new TreeMap<>();
        languages.put("ab", "Cyrillic");
        languages.put("ar", "Arabic");
        languages.put("cv", "Cyrillic");
        languages.put("en", "Latin");
        languages.put("fr", "Latin");
        languages.put("el", "Greek");
        languages.put("pl", "Latin");
        languages.put("ru", "Cyrillic");
        languages.put("sv", "Latin");
        languages.put("uk", "Cyrillic");
        
        languages.entrySet().stream().forEach((entry) -> {
            String code   = entry.getKey();
            String script = entry.getValue();
            String result = LanguageCodes.getScript(code);
            
            assertEquals(script, result);
        });
    }
    
}
