/*
 * IntelliJustice Intelligence Referee Assistant System
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
            = java.util.logging.Logger.getLogger(
              com.intellivision.core.Manifest.NAME);

    /* the current log file object */
    private final File file;

    /* the product name for this log */
    private final String product;

    /**
     * Constructs log file representation.
     *
     * @param fileName    the log file name.
     * @param product     the product name for this log.
     *
     * @throws FileNotFoundException an exception thrown if file is not exists.
     */
    public Log(final String fileName, String product)
            throws FileNotFoundException {
        this(new File(fileName), product);
    }

    /**
     * Constructs log file representation.
     *
     * @param file    the log file name.
     * @param product the product name for this log.
     *
     * @throws FileNotFoundException an exception thrown if file is not exists.
     */
    public Log(final File file,  String product)
            throws FileNotFoundException {
        this.file    = file;
        this.product = product;

        if (file.exists() == false) {
            throw new java.io.FileNotFoundException(
                    new StringBuilder(30 + file.getAbsolutePath().length()
                    ).append("Log file <"
                    ).append(file.getAbsolutePath()
                    ).append("> doesn't exists!").toString());
        }
    }

    /**
     * Returns the product name for this log file.
     *
     * @return the product name for this log file
     */
    public String getProductName() {
        return product;
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
        final FileInputStream stream = new FileInputStream(file);

        /* the buffer into which the data is read */
        final byte[] byteArray = new byte[(int) file.length()];

        /* the total number of bytes read into the buffer */
        int read = 0;

        while (read < byteArray.length) {
            read += stream.read(byteArray, read, byteArray.length - read);
        }

        return new String(byteArray);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param object the reference object with which to compare.
     * @return       {@code true} if this log is the same as the object
     *               argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(final Object object) {
        return file.equals(object);
    }

    /**
     * Returns a hash code value for the machine.
     *
     * @return a hash code value for this machine.
     */
    @Override
    public int hashCode() {
        return file.hashCode();
    }
}
