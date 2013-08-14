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

package com.intellijustice.util;

import com.intellijustice.core.Manifest;
import java.util.List;
import java.util.Map;
import javafx.application.Application;

/**
 * The application parameters parser.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      ParametersParser.java
 * %date      04:30:00 PM, Aug 09, 2013
 */
public class ParametersParser {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellijustice.core.Manifest.NAME);

    private static final String help
            = "Usage: java -jar IntelliJustice [parameters]\n"
            + "    --help\tprint this message and exit\n"
            + "    --version\tprint product version and exit\n";

    /* do not let anyone instantiate this class */
    private ParametersParser() {
    }

    /**
     * Parses application parameters.
     *
     * @param app the application instance.
     */
    public static void parse(final Application app) {
        final Map<String, String> named   = app.getParameters().getNamed();
        final List<String>        unnamed = app.getParameters().getUnnamed();

        for (Map.Entry<String, String> arg : named.entrySet()) {
            switch (arg.getKey()) {
                default:
                    LOG.info(help);
                    System.exit(StatusCodes.EXIT_SUCCESS);
                    break;
            }
        }

        for (String arg : unnamed) {
            switch (arg) {
                case "-v":
                case "-version":
                case "--version":
                    LOG.info(("Version: " + Manifest.VERSION));
                    System.exit(StatusCodes.EXIT_SUCCESS);
                    break;
                case "-h":
                case "-help":
                case "--help":
                    /* fall through */
                default:
                    LOG.info(help);
                    System.exit(StatusCodes.EXIT_SUCCESS);
                    break;
            }
        }
    }
}
