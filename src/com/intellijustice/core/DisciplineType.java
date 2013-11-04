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
 * The competition discipline type representation.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      DisciplineType.java
 * %date      03:00:00 PM, Nov 03, 2013
 */
public enum DisciplineType {

    RUNNING, UNDEFINED;

    /**
     * Returns discipline type for provided discipline entity.
     *
     * @param discipline the discipline entity representation.
     * @return           the discipline type.
     */
    public static DisciplineType getDisciplineType(final Discipline discipline) {
        switch (discipline) {
            case R_100_METRES:
            case R_200_METRES:
            case R_300_METRES:
            case R_400_METRES:
            case R_600_METRES:
            case R_800_METRES:
            case R_1000_METRES:
            case R_1500_METRES:
            case R_MILE:
            case R_2000_METRES:
            case R_3000_METRES:
            case R_2_MILES:
            case R_5000_METRES:
            case R_10_000_METRES:
            case R_100_METRES_HURDLES:
            case R_110_METRES_HURDLES:
            case R_400_METRES_HURDLES:
            case R_2000_METRES_STEEPLECHASE:
            case R_3000_METRES_STEEPLECHASE:
                return RUNNING;
            case HIGH_JUMP:
            case POLE_VAULT:
            case LONG_JUMP:
            case TRIPPLE_JUMP:
            case SHOT_PUT:
            case DISCUS_THROW:
            case HAMMER_THROW:
            case JAVELIAN_THROW:
            case R_10_KILOMETRES:
            case R_15_KILOMETRES:
            case R_10_MILES:
            case R_20_KILOMETRES:
            case HALF_MARATHON:
            case MARATHON:
            case W_3000_METRES_RACE_WALK:
            case W_5000_METRES_RACE_WALK:
            case W_5_KILOMETRES_RCAE_WALK:
            case W_10_000_METRES_RCAE_WALK:
            case W_10_KILOMETRES_RCAE_WALK:
            case W_20_000_METRES_RCAE_WALK:
            case W_20_KILOMETRES_RCAE_WALK:
            case W_35_KILOMETRES_RCAE_WALK:
            case W_50_KILOMETRES_RCAE_WALK:
            case HEPTATHLON:
            case DECATHLON:
            case R_4_100_METRES_RELAY:
            case R_4_400_METRES_RELAY:
                return RUNNING;
            case UNDEFINED:
                return UNDEFINED;
        }

        return UNDEFINED;
    }
}
