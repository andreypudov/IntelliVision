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
 * The competition discipline entity representation.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Discipline.java
 * %date      05:20:00 PM, Aug 14, 2013
 */
public enum Discipline {

    R_100_METRES("100m"),
    R_200_METRES("200m"),
    R_300_METRES("300m"),
    R_400_METRES("400m"),
    R_600_METRES("600m"),
    R_800_METRES("800m"),
    R_1000_METRES("1000m"),
    R_1500_METRES("1500m"),
    R_MILE("Mile"),
    R_2000_METRES("2000m"),
    R_3000_METRES("3000m"),
    R_2_MILES("2 Miles"),
    R_5000_METRES("5000m"),
    R_10_000_METRES("10,000m"),
    R_100_METRES_HURDLES("100mH"),
    R_110_METRES_HURDLES("110mH"),
    R_400_METRES_HURDLES("400mH"),
    R_2000_METRES_STEEPLECHASE("2000mSC"),
    R_3000_METRES_STEEPLECHASE("3000mSC"),
    HIGH_JUMP("High Jump"),
    POLE_VAULT("Pole Vault"),
    LONG_JUMP("Long Jump"),
    TRIPPLE_JUMP("Triple Jump"),
    SHOT_PUT("Shot Put"),
    DISCUS_THROW("Discus Throw"),
    HAMMER_THROW("Hammer Throw"),
    JAVELIAN_THROW("Javelian Throw"),
    R_10_KILOMETRES("10 km"),
    R_15_KILOMETRES("15 km"),
    R_10_MILES("10 Miles"),
    R_20_KILOMETRES("20 km"),
    HALF_MARATHON("Half Marathon"),
    MARATHON("Marathon"),
    W_3000_METRES_RACE_WALK("3000mW"),
    W_5000_METRES_RACE_WALK("5000mW"),
    W_5_KILOMETRES_RCAE_WALK("5kmW"),
    W_10_000_METRES_RCAE_WALK("10,000mW"),
    W_10_KILOMETRES_RCAE_WALK("10kmW"),
    W_20_000_METRES_RCAE_WALK("20,000mW"),
    W_20_KILOMETRES_RCAE_WALK("20kmW"),
    W_35_KILOMETRES_RCAE_WALK("35kmW"),
    W_50_KILOMETRES_RCAE_WALK("50kmW"),
    HEPTATHLON("Heptathlon"),
    DECATHLON("Decathlon"),
    R_4_100_METRES_RELAY("4x100m"),
    R_4_400_METRES_RELAY("4x400m"),
    UNDEFINED("Undefined");

    private final String value;

    Discipline(final String value) {
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
     * Return the discipline representation of the string.
     *
     * @param value the string value of discipline.
     * @return      the discipline representation of the string.
     */
    public static Discipline getDiscipline(final String value) {
        for (Discipline discipline : Discipline.values()) {
            if (discipline.toString().equals(value)) {
                return discipline;
            }
        }

        return UNDEFINED;
    }
}
