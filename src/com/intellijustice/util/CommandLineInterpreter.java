/*
 * IntelliJustice Intelligent Referee Assistant System
 *
 * The MIT License
 *
 * Copyright 2011-2015 Andrey Pudov.
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

package com.intellijustice.util;

import com.intellijustice.util.pools.Core;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;

/**
 * The command line interpreter.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      CommandLineInterpreter.java
 * %date      08:50:00 AM, Mar 15, 2015
 */
public class CommandLineInterpreter {
    
    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
              com.intellijustice.core.Manifest.NAME);
    
    /**
     * Constructs interpreter object.
     */
    public CommandLineInterpreter() {
    }
    
    /**
     * Launch command line interpreter.
     */
    public void launch() {
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String string;

            System.out.print("> ");
            while ((string = reader.readLine()) != null) {
                if (string.equals("exit")) {
                    Core.closeWindow();
                    System.exit(StatusCodes.EXIT_SUCCESS);
                }
                
                System.out.print("> ");
            }
        } catch (final IOException e) {
            LOG.log(Level.WARNING,
                "Could not read command line input. {0}",
                e.getMessage());
        }
    }
}
