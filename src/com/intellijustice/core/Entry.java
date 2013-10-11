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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * The result entry representation.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Entry.java
 * %date      02:30:00 PM, Aug 28, 2013
 */
public class Entry {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellijustice.core.Manifest.NAME);

    /* the athlete representation */
    private final Athlete athlete;

    /* the resultList of result of the athlete */
    private final List<Result> resultList;

    private final int   id;

    private final short rank;
    private final short bib;
    private final short line;

    private final int   personal;
    private final int   season;

    /**
     * Constructs the athlete entry for the competition.
     *
     * @param id       the identification number for the entry.
     * @param athlete  the athlete entry.
     * @param rank     the rank of the athlete (the number of the result).
     * @param bib      the bib number of the athlete for the championship.
     * @param line     the line of the athlete for the competition.
     * @param personal the personal result of the athlete in the discipline.
     * @param season   the season result of the athlete in the discipline.
     */
    public Entry(final int id, final Athlete athlete, final short rank,
            final short bib, final short line,
            final int personal, final int season) {
        this.athlete    = athlete;
        this.resultList = new LinkedList<>();

        this.id       = id;

        this.rank     = rank;
        this.bib      = bib;
        this.line     = line;
        this.personal = personal;
        this.season   = season;
    }

    /**
     * Returns the identification number for the entry.
     *
     * @return the identification number for the entry.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the athlete entry.
     *
     * @return the athlete entry.
     */
    public Athlete getAthlete() {
        return athlete;
    }

    /**
     * Returns the rank of the athlete (the number of the result).
     *
     * @return the rank of the athlete (the number of the result).
     */
    public short getRank() {
        return rank;
    }

    /**
     * Returns the bib number of the athlete for the championship.
     *
     * @return the bib number of the athlete for the championship.
     */
    public short getBib() {
        return bib;
    }

    /**
     * Returns the line of the athlete for the competition.
     *
     * @return the line of the athlete for the competition.
     */
    public short getLine() {
        return line;
    }

    /**
     * Returns the personal result of the athlete in the discipline.
     *
     * @return the personal result of the athlete in the discipline.
     */
    public int getPersonal() {
        return personal;
    }

    /**
     * Returns the season result of the athlete in the discipline.
     *
     * @return the season result of the athlete in the discipline.
     */
    public int getSeason() {
        return season;
    }

    /**
     * Adds the specified result to the results resultList.
     *
     * @param result the result of the athlete.
     */
    public void addResult(final Result result) {
        resultList.add(result);
    }

    /**
     * Returns the list of athlete results.
     *
     * @return the resultList of athlete results.
     */
    public List<Result> getResults() {
        return Collections.unmodifiableList(resultList);
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

        final Entry other = (Entry) obj;
        if (!Objects.equals(this.athlete, other.athlete)) {
            return false;
        }

        if (!Objects.equals(this.resultList, other.resultList)) {
            return false;
        }

        if (this.id != other.id) {
            return false;
        }

        if (this.rank != other.rank) {
            return false;
        }

        if (this.bib != other.bib) {
            return false;
        }

        if (this.line != other.line) {
            return false;
        }

        if (this.personal != other.personal) {
            return false;
        }

        if (this.season != other.season) {
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
        /*int hash = 3;

        hash = 67 * hash + Objects.hashCode(this.athlete);
        hash = 67 * hash + Objects.hashCode(this.resultList);
        hash = 67 * hash + this.id;
        hash = 67 * hash + this.rank;
        hash = 67 * hash + this.bib;
        hash = 67 * hash + this.line;
        hash = 67 * hash + this.personal;
        hash = 67 * hash + this.season;*/

        return id;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        final StringBuilder builder   = new StringBuilder(126);

        /* 01 S FistName LastName 01-01-1970 */
        builder.append(rank).append(" "
                ).append(bib).append(" "
                ).append(athlete).append(" "
                ).append(line).append(" "
                ).append(personal).append(" "
                ).append(season);

        return builder.toString();
    }
}