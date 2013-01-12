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

import com.intellivision.util.logs.Machine;
import java.util.ArrayList;
import java.util.List;

/**
 * Remote machine list pool.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Machines.java
 * %date      04:00:00 PM, Jan 12, 2013
 */
public enum Machines {
    INSTANCE;

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellivision.core.Manifest.NAME);

    /* remote machine list */
    private static final List<Machine> list = new ArrayList<>(10);

    static {
        /*
         * Inithialize machine list with entries from application configuration.
         */
    }

    /* do not let anyone instantiate this class */
    private Machines() {
    }

    /**
     * Returns an instance of remote machine list.
     *
     * @return the remote machine list.
     */
    public static Machines getMachines() {
        return Machines.INSTANCE;
    }

    /**
     * Initialize machine list.
     */
    private static void init() {

    }
}
