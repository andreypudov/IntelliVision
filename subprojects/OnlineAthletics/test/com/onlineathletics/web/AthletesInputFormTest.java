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

package com.onlineathletics.web;

import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * The unit validation test of AthletesInputForm class.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      AthletesInputFormTest.java
 * %date      09:10:00 PM, Jul 01, 2014
 */
public class AthletesInputFormTest {
    
    public AthletesInputFormTest() {
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
     * Test of getRequest method, of class AthletesInputForm.
     */
    @Test
    public void testGetRequest() {
        System.out.println("getRequest");
        
        AthletesInputForm instance = new AthletesInputForm();
        String expResult = Long.toString(new Date().getTime());
        instance.setRequest(expResult);
        
        String result = instance.getRequest();
        assertEquals(expResult, result);
    }

    /**
     * Test of setRequest method, of class AthletesInputForm.
     */
    @Test
    public void testSetRequest() {
        System.out.println("getRequest");
        
        AthletesInputForm instance = new AthletesInputForm();
        String expResult = Long.toString(new Date().getTime());
        instance.setRequest(expResult);
        
        String result = instance.getRequest();
        assertEquals(expResult, result);
    }

    /**
     * Test of submit method, of class AthletesInputForm.
     */
    @Test
    public void testSubmit() {
        System.out.println("submit");
        AthletesInputForm instance = new AthletesInputForm();
        instance.submit();
    }

    /**
     * Test of validate method, of class AthletesInputForm.
     */
    @Test
    public void testValidate() {
        System.out.println("validate");
        AthletesInputForm instance = new AthletesInputForm();
        instance.validate();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
