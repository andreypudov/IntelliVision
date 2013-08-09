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

import com.intellijustice.util.StatusCodes;
import java.io.IOException;
import java.util.Properties;

/**
 * The general application manifest.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Manifest.java
 * %date      04:30:00 PM, Jan 12, 2012
 */
public class Manifest {

    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String VERSION;
    public static final String RELEASE;

    private static final java.util.logging.Logger LOG;

        /* initialize application level variables */
    static {
        String name        = "IntelliJustice";
        String description = "IntelliJustice Intelligent Referee Assistant System";
        String version     = "0.00.00";
        String release     = "Alpha";

        LOG = java.util.logging.Logger.getLogger(name);

        try {

            final Properties manifest = new Properties();
            manifest.loadFromXML(Manifest.class.getResource(
                    "/com/intellijustice/resources/schemas/Manifest.xml"
                    ).openStream());
            
            name        = manifest.getProperty("NAME");
            description = manifest.getProperty("DESCRIPTION");
            version     = manifest.getProperty("VERSION");
            release     = manifest.getProperty("RELEASE");
        } catch (IOException e) {
            LOG.severe("Could not load manifest file.");
            System.exit(StatusCodes.EXIT_FAILURE);
        }

        NAME        = name;
        DESCRIPTION = description;
        VERSION     = version;
        RELEASE     = release;
    }

    /* do not let anyone instantiate this class */
    private Manifest() {
    }
}
