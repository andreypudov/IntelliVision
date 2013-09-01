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
 * The result entry representation.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Result.java
 * %date      02:00:00 PM, Aug 28, 2013
 */
public class Result implements Comparable<Result> {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellijustice.core.Manifest.NAME);

    public static final int SUCCESS = -1;
    public static final int FAILED  = -2;

    public static final int DID_NOT_START  = -3;  /* DNS */
    public static final int DID_NOT_FINISH = -4;  /* DNF */
    public static final int DISQUALIFIED   = -5;  /* DSQ */

    private final int attempt;
    private final int value;
    private final int result;
    private final int wind;

    /**
     * Constructs the result entry for the athlete.
     *
     * @param attempt the number of the attempt during the competition.
     * @param value   the value of an attempt.
     * @param result  the result value.
     * @param wind    the wind speed during an attempt.
     */
    public Result(final int attempt, final int value, final int result,
            final int wind) {
        this.attempt = attempt;
        this.value   = value;
        this.result  = result;
        this.wind    = wind;
    }

    /**
     * Returns the number of the attempt during the competition.
     *
     * @return the number of the attempt during the competition.
     */
    public int getAttempt() {
        return attempt;
    }

    /**
     * Returns the value of an attempt.
     *
     * @return the value of an attempt.
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the result value.
     *
     * @return the result value.
     */
    public int getResult() {
        return result;
    }

    /**
     * Returns the wind speed during an attempt.
     *
     * @return the wind speed during an attempt.
     */
    public int getWind() {
        return wind;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj the reference object with which to compare.
     * @return    true if this object is the same as the obj argument;
     *            false otherwise.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final Result other = (Result) obj;
        if (this.attempt != other.attempt) {
            return false;
        }

        if (this.value != other.value) {
            return false;
        }

        if (this.result != other.result) {
            return false;
        }

        if (this.wind != other.wind) {
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

        hash = 71 * hash + this.attempt;
        hash = 71 * hash + this.value;
        hash = 71 * hash + this.result;
        hash = 71 * hash + this.wind;

        return hash;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(128);

        builder.append(attempt).append(" "
                ).append(value).append(" "
                ).append(result).append(" "
                ).append(wind);

        return builder.toString();
    }

    /**
     * Compares this result entry with the specified result entry for order.
     * Returns a negative integer, zero, or a positive integer as this result
     * entry is less than, equal to, or greater than the specified result entry.
     *
     * @param entry the result entry to be compared.
     * @return      a negative integer, zero, or a positive integer as this
     *              result entry is less than, equal to, or greater than the
     *              specified result entry.
     */
    @Override
    public int compareTo(final Result entry) {
        // TODO
        return 0;
    }
}
