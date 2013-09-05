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

/**
 * The record entry representation.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Record.java
 * %date      02:00:00 PM, Aug 28, 2013
 */
public class Record {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellijustice.core.Manifest.NAME);

    private final int world;
    private final int area;
    private final int national;
    private final int junior;
    private final int juniorBest;
    private final int mediterranean;
    private final int season;

    /**
     * Constructs the record entry with record values for the competition.
     *
     * @param world         the world record value.
     * @param area          the area record value.
     * @param national      the national record value.
     * @param junior        the junior record value.
     * @param juniorBest    the junior best record value.
     * @param mediterranean the Mediterranean record value.
     * @param season        the season record value.
     */
    public Record(final int world, final int area, final int national,
            final int junior, final int juniorBest, final int mediterranean,
            final int season) {
        this.world         = world;
        this.area          = area;
        this.national      = national;
        this.junior        = junior;
        this.juniorBest    = juniorBest;
        this.mediterranean = mediterranean;
        this.season        = season;
    }

    /**
     * Returns the world record value.
     *
     * @return the world record value.
     */
    public int getWorld() {
        return world;
    }

    /**
     * Returns the area record value.
     *
     * @return the area record value.
     */
    public int getArea() {
        return area;
    }

    /**
     * Returns the national record value.
     *
     * @return the national record value.
     */
    public int getNational() {
        return national;
    }

    /**
     * Returns the junior record value.
     *
     * @return the junior record value.
     */
    public int getJunior() {
        return junior;
    }

    /**
     * Returns the junior best record value.
     *
     * @return the junior best record value.
     */
    public int getJuniorBest() {
        return juniorBest;
    }

    /**
     * Returns the Mediterranean record value.
     *
     * @return the Mediterranean record value.
     */
    public int getMediterranean() {
        return mediterranean;
    }

    /**
     * Returns the season record value.
     *
     * @return the season record value.
     */
    public int getSeason() {
        return season;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(128);

        builder.append("WR: ").append(world
                ).append(" AR: ").append(area
                ).append(" NR: ").append(national
                ).append(" JR: ").append(junior
                ).append(" JRB: ").append(juniorBest
                ).append(" MR: ").append(mediterranean
                ).append(" SB: ").append(season);

        return builder.toString();
    }
}
