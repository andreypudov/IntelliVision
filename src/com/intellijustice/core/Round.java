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
 * The competition round entity representation.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Round.java
 * %date      09:30:00 PM, Aug 28, 2013
 */
public enum Round {

    PRELIMINARY_ROUND("Preliminary Round"),
    HEATS("Heats"),
    SEMI_FINAL("Semi-Final"),
    FINAL("Final"),
    DECATHLON("Decathlon"),
    HEPTATHLON("Heptathlon"),
    QUALIFICATION_GROUP_A("Qualification Group A"),
    QUALIFICATION_GROUP_B("Qualification Group B"),
    DECATHLON_GROUP_A("Decathlon Group A"),
    DECATHLON_GROUP_B("Decathlon Group B"),
    HEPTATHLON_GROUP_A("Heptathlon Group A"),
    HEPTATHLON_GROUP_B("Heptathlon Group B"),
    UNDEFINED("Undefined");

    private final String value;

    Round(final String value) {
        this.value = value;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Return the round representation of the string.
     *
     * @param value the string value of round.
     * @return      the round representation of the string.
     */
    public static Round getRound(final String value) {
        for (Round round : Round.values()) {
            if (round.toString().equals(value)) {
                return round;
            }
        }

        return UNDEFINED;
    }
}
