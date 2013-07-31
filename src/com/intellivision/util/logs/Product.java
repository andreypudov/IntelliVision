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

package com.intellivision.util.logs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 * The product container for the list of product log files.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Product.java
 * %date      11:45:00 PM, Feb 09, 2013
 */
public class Product {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellivision.core.Manifest.NAME);

    private final List<Log> list = new ArrayList<>(16);

    /* the product name */
    private final String name;

    /**
     * Constructs product container.
     *
     * @param name the product name.
     */
    public Product(final String name) {
        this.name = name;
    }

    /**
     * Returns product name.
     *
     * @return the product name.
     */
    public String getName() {
        return name;
    }

    /**
     * Adds product log entry to product.
     *
     * @param log the product log entry.
     */
    public void add(final Log log) {
        list.add(log);
    }

    /**
     * Removes product log entry from product.
     *
     * @param log the product log entry.
     */
    public void remove(final Log log) {
        list.remove(log);
    }

    /**
     * Returns the list iterator over the elements in product log files list.
     *
     * @return the list iterator over the elements in product log files list.
     */
    public ListIterator<Log> getIterator() {
        return list.listIterator();
    }

    /**
     * Return an unmodifiable view of the product log files list.
     *
     * @return an unmodifiable view of the product log files list.
     */
    public List<Log> getList() {
        return Collections.unmodifiableList(list);
    }
}
