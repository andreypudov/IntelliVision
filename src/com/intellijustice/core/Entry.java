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

    private final short rank;
    private final short bib;
    private final short line;
    private final short reaction;

    private final int   personal;
    private final int   season;

    /**
     * Constructs the athlete entry for the competition.
     *
     * @param athlete  the athlete entry.
     * @param rank     the rank of the athlete (the number of the result).
     * @param bib      the bib number of the athlete for the championship.
     * @param line     the line of the athlete for the competition.
     * @param reaction the reaction time of the athlete.
     * @param personal the personal result of the athlete in the discipline.
     * @param season   the season result of the athlete in the discipline.
     */
    public Entry(final Athlete athlete, final short rank,
            final short bib, final short line, final short reaction,
            final int personal, final int season) {
        this.athlete  = athlete;

        this.resultList     = new LinkedList<>();

        this.rank     = rank;
        this.bib      = bib;
        this.line     = line;
        this.reaction = reaction;
        this.personal = personal;
        this.season   = season;
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
     * Returns the reaction time of the athlete.
     *
     * @return the reaction time of the athlete.
     */
    public short getReaction() {
        return reaction;
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
     * Updates exist result to the new value.
     *
     * @param old     the exist result of the athlete.
     * @param updated the new value for to update.
     *
     * @throws IllegalArgumentException
     *                the old result value is not exists in the entry.
     */
    public void updateResult(final Result old, final Result updated)
            throws IllegalArgumentException {
        int index = resultList.indexOf(old);

        /* the old result not found exception */
        if (index == -1) {
            throw new IllegalArgumentException("The result value [" + old
                    + "] is not exists in the entry and can not be updated.");
        }

        resultList.remove(index);
        resultList.set(index, updated);
    }

    /**
     * Returns the list of athlete results.
     *
     * @return the resultList of athlete results.
     */
    public List<Result> getResults() {
        return Collections.unmodifiableList(resultList);
    }
}