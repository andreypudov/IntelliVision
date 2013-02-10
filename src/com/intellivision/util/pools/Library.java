/*
 * IntelliVision Intelligence Image Processing System
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

package com.intellivision.util.pools;

import com.intellivision.util.logs.Log;
import com.intellivision.util.logs.Product;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 * Product logs library interface to work with archived log files.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Library.java
 * %date      10:40:00 PM, Jan 29, 2013
 */
public enum Library {
    INSTANCE;

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellivision.core.Manifest.NAME);

    /* the product entries list */
    private static final List<Product> list = new ArrayList<>(10);

    static {
        /*
         * Inithialize the product logs library.
         */

        /* .IntelliVision/.library */
        final File library = new File(System.getProperty("user.home")
                + System.getProperty("file.separator")
                + "." + com.intellivision.core.Manifest.NAME
                + System.getProperty("file.separator")
                + ".library");

        if (!(library.exists()) || !(library.isDirectory())) {
            library.delete();
            library.mkdir();
        }

        try {
            for (File productDir : library.listFiles()) {
                final String  name    = productDir.getName();
                final Product product = new Product(name);

                for (File logFile : productDir.listFiles()) {
                    product.add(new Log(logFile, name));
                }

                list.add(product);
            }
        } catch (FileNotFoundException e) {
            LOG.warning(new StringBuffer(256
                        ).append("Could not read log file. "
                        ).append(e.getMessage()).toString());
        }
    }

    /* do not let anyone instantiate this class */
    private Library() {
    }

    /**
     * Returns an instance of library.
     *
     * @return the product logs library.
     */
    public static synchronized Library getLibrary() {
        return Library.INSTANCE;
    }

    /**
     * Adds product log entry to library. If library doesn't contains product
     * entry for specified log entry, new product will be generated.
     *
     * @param log the product log entry.
     */
    public synchronized void add(final Log log) {
        for (Product product : list) {
            if (product.getName().equals(log.getProductName())) {
                product.add(log);

                return;
            }
        }

        /* add new product and specified log file */
        final Product product = new Product(log.getProductName());
        product.add(log);
        list.add(product);
    }

    /**
     * Removes product log entry from library.
     *
     * @param log the product log entry.
     */
    public synchronized void remove(final Log log) {
        for (Product product : list) {
            product.remove(log);
        }
    }

    /**
     * Returns the list iterator over the products in library.
     *
     * @return the list iterator over the products in library.
     */
    public synchronized ListIterator<Product> getIterator() {
        return list.listIterator();
    }

    /**
     * Return an unmodifiable view of the products in library.
     *
     * @return an unmodifiable view of the products in library.
     */
    public synchronized List<Product> getList() {
        return Collections.unmodifiableList(list);
    }
}
