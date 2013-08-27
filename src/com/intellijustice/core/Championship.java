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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The championship entity representation.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Championship.java
 * %date      04:30:00 PM, Aug 14, 2013
 */
public class Championship {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellijustice.core.Manifest.NAME);

    private final List<Competition>  list;

    private final int                id;
    private final String             name;
    private final String             city;
    private final ChampionshipFormat format;

    /**
     * Constructs new championship object.
     *
     * @param id     the identification number for the championship.
     * @param name   the name of the championship.
     * @param city   the city of the championship.
     * @param format the format of the championship (indoor, outdoor).
     */
    public Championship(final int id, final String name, final String city,
            final ChampionshipFormat format) {
        this.list   = new ArrayList<>(16);
        this.id     = id;
        this.name   = name;
        this.city   = city;
        this.format = format;
    }

    /**
     * Returns the identification number for the championship.
     *
     * @return the identification number for the championship.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of the championship.
     *
     * @return the name of the championship.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the city of the championship.
     *
     * @return the city of the championship.
     */
    public String getCity() {
        return city;
    }

    /**
     * Returns the format of the championship.
     *
     * @return the format of the championship (indoor, outdoor).
     */
    public ChampionshipFormat getFormat() {
        return format;
    }

    /**
     * Return the list of championship events.
     *
     * @return the competition list.
     */
    public List<Competition> getCompetitionList() {
        return Collections.unmodifiableList(list);
    }
}
