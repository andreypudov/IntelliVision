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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    /* the list of result entries */
    private final List<Entry> list;

    private final int        id;
    private final Discipline discipline;
    private final Round      round;
    private final boolean    sex; /* true(1) is male, false(0) is female */
    private final long       startTime;
    private final long       endTime;
    private final short      temperature;
    private final short      humidity;

    /**
     * Constructs new competition object.
     *
     * @param id          the identification number for the competition.
     * @param discipline  the discipline of the competition.
     * @param round       the round of the competition.
     * @param sex         the sex of athletes in the competition.
     * @param startTime   the starting time of the competition.
     * @param endTime     the ending time of the competition.
     * @param temperature the temperature of the competition.
     * @param humidity    the humidity of the competition.
     */
    public Competition(final int id, final Discipline discipline,
            final Round round, final boolean sex, final long startTime,
            final long endTime, final short temperature, final short humidity) {
        this.list        = new ArrayList<>(16);

        this.id          = id;
        this.discipline  = discipline;
        this.round       = round;
        this.sex         = sex;
        this.startTime   = startTime;
        this.endTime     = endTime;
        this.temperature = temperature;
        this.humidity    = humidity;
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
     * Returns the discipline of the competition.
     *
     * @return the discipline for the competition.
     */
    public Discipline getDiscipline() {
        return discipline;
    }

    /**
     * Returns the round of the competition.
     *
     * @return the round of the competition.
     */
    public Round getRound() {
        return round;
    }

    /**
     * Returns the sex of athletes in the competition.
     *
     * @return the sex of athletes in the competition.
     */
    public boolean getSex() {
        return sex;
    }

    /**
     * Returns the starting time of the competition.
     *
     * @return the starting time of the competition.
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Returns the ending time of the competition.
     *
     * @return the ending time of the competition.
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * Returns the temperature of the competition.
     *
     * @return the temperature of the competition.
     */
    public short getTemperature() {
        return temperature;
    }

    /**
     * Returns the humidity of the competition.
     *
     * @return the humidity of the competition.
     */
    public short getHumidity() {
        return humidity;
    }

    /**
     * Adds entry representation to the list.
     *
     * @param entry the entry representation.
     */
    public void addEntry(final Entry entry) {
        list.add(entry);
    }

    /**
     * Return the list of competition results.
     *
     * @return the competition result entries.
     */
    public List<Entry> getCompetitionList() {
        return Collections.unmodifiableList(list);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj the reference object with which to compare.
     * @return    true if this object is the same as the obj argument;
     *            false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final Competition other = (Competition) obj;
        if (!Objects.equals(this.list, other.list)) {
            return false;
        }

        if (this.id != other.id) {
            return false;
        }

        if (this.discipline != other.discipline) {
            return false;
        }

        if (this.round != other.round) {
            return false;
        }

        if (this.sex != other.sex) {
            return false;
        }

        if (this.startTime != other.startTime) {
            return false;
        }

        if (this.endTime != other.endTime) {
            return false;
        }

        if (this.temperature != other.temperature) {
            return false;
        }

        if (this.humidity != other.humidity) {
            return false;
        }

        return true;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 7;

        hash = 83 * hash + Objects.hashCode(this.list);
        hash = 83 * hash + this.id;
        hash = 83 * hash + Objects.hashCode(this.discipline);
        hash = 83 * hash + Objects.hashCode(this.round);
        hash = 83 * hash + (this.sex ? 1 : 0);
        hash = 83 * hash + (int) (this.startTime ^ (this.startTime >>> 32));
        hash = 83 * hash + (int) (this.endTime ^ (this.endTime >>> 32));
        hash = 83 * hash + this.temperature;
        hash = 83 * hash + this.humidity;

        return hash;
    }


    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        final StringBuilder builder  = new StringBuilder(128);
        final DateFormat    formater = new SimpleDateFormat("hh:mm");


        builder.append(discipline).append(" "
                ).append(round).append(" "
                ).append((sex) ? "M" : "F").append(" "
                ).append(formater.format(new Date(startTime))).append(" "
                ).append(formater.format(new Date(endTime))).append(" "
                ).append(temperature).append(" "
                ).append(humidity);

        return builder.toString();
    }
}
