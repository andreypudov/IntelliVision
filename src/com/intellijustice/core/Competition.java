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
 * The competition entity representation.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Athlete.java
 * %date      01:50:00 PM, Aug 03, 2013
 */
public class Competition {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellijustice.core.Manifest.NAME);

    private final List<Athlete>   list;

    private final int             id;
    private final CompetitionType type;
    private final String          name;
    private final long            time;
    private final boolean         sex; /* true(1) is male, false(0) is female */

    /**
     * Constructs new competition object.
     *
     * @param id   the identification number for the competition.
     * @param type the type of competition (the discipline).
     * @param name the name of the competition.
     * @param time the starting time of the competition.
     * @param sex  the sex of athletes in the competition.
     */
    public Competition(final int id, final CompetitionType type,
            final String name, final long time, final boolean sex) {
        this.list = new ArrayList<>(16);
        this.id   = id;
        this.type = type;
        this.name = name;
        this.time = time;
        this.sex  = sex;
    }

    /**
     * Returns the identification number for the competition.
     *
     * @return the identification number for the competition.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the type of competition (the discipline).
     *
     * @return the type of competition (the discipline).
     */
    public CompetitionType getType() {
        return type;
    }

    /**
     * Returns the name of the competition.
     *
     * @return the name of the competition.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the starting time of the competition.
     *
     * @return the starting time of the competition.
     */
    public long getTime() {
        return time;
    }

    /**
     * Returns the sex of athletes in the competition.
     *
     * @return the sex of athletes in the competition.
     */
    public boolean isSex() {
        return sex;
    }

    /**
     * Returns the list of athletes in this competition.
     *
     * @return the list of athletes.
     */
    public List<Athlete> getAthleteList() {
        return Collections.unmodifiableList(list);
    }
}
