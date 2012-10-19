/*
 * IntelliVision Intelligence Image Processing System
 *
 * The MIT License
 *
 * Copyright 2011-2012 Andrey Pudov.
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

package com.intellivision.util.logs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * The log file representation provides general read/write methods.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Log.java
 * %date      01:10:00 PM, Oct 14, 2012
 */
public class Log {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger("IntelliVision");

    /* the current log file object */
    private final File logFile;

    /* default constructor */
    public Log(final String fileName) throws FileNotFoundException {
        this(new File(fileName));
    }

    /* overloaded constructor */
    public Log(final File file) throws FileNotFoundException {
        logFile = file;

        if (logFile.exists() == false) {
            throw new java.io.FileNotFoundException(
                    new StringBuilder(30 + logFile.getAbsolutePath().length()
                    ).append("Log file <"
                    ).append(logFile.getAbsolutePath()
                    ).append("> doesn't exists!").toString());
        }
    }

    /**
     * Returns entire content of the log file.
     *
     * @return the entire content of the log file.
     *
     * @throws FileNotFoundException
     *         the log file doesn't exists.
     * @throws IOException
     *         error occurred during reading of the log file.
     */
    public String getLogContent() throws FileNotFoundException, IOException {
        final FileInputStream stream = new FileInputStream(logFile);

        /* the buffer into which the data is read */
        final byte[] byteArray = new byte[(int) logFile.length()];

        /* the total number of bytes read into the buffer */
        int read = 0;

        while (read < byteArray.length) {
            read += stream.read(byteArray, read, byteArray.length - read);
        }

        return new String(byteArray);
    }
}
