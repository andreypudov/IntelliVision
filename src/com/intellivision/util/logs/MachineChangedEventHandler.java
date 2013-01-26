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

package com.intellivision.util.logs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The machine changed event source.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      MachineChangedEvent.java
 * %date      04:40:00 PM, Jan 26, 2013
 */
public class MachineChangedEventHandler {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
              com.intellivision.core.Manifest.NAME);

    private final List<MachineChangedEventListener> list = new ArrayList<>(2);

    /**
     * Default constructor.
     */
    public MachineChangedEventHandler() {
    }

    public synchronized void addEventListener(
            final MachineChangedEventListener listener) {
        list.add(listener);
    }

    public synchronized void removeEventListener(
            final MachineChangedEventListener listener) {
        list.remove(listener);
    }

    public synchronized void fireEvent() {
        final MachineChangedEvent event = new MachineChangedEvent(this);
        final Iterator<MachineChangedEventListener> iterator = list.iterator();

        while (iterator.hasNext()) {
            MachineChangedEventListener listener = iterator.next();

            listener.handleMachineChangedEvent(event);
        }
    }
}
