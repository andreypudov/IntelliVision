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
    R_100_METRES,  R_200_METRES,  R_300_METRES,  R_400_METRES,   R_600_METRES,
    R_800_METRES,  R_1000_METRES, R_1500_METRES, R_MILE,         R_2000_METRES,
    R_3000_METRES, R_2_MILES,     R_5000_METRES, R_10_000_METRES,
    R_100_METRES_HURDLES,         R_110_METRES_HURDLES,
    R_400_METRES_HURDLES,         R_2000_METRES_STEEPLECHASE,
    R_3000_METRES_STEEPLECHASE,   HIGH_JUMP,     POLE_VAULT,     LONG_JUMP,
    TRIPPLE_JUMP,  SHOT_PUT,      DISCUS_THROW,  HAMMER_THROW,   JAVELIAN_THROW,
    R_10_KILOMETRES,              R_15_KILOMETRES,               R_10_MILES,
    R_20_KILOMETRES,              HALF_MARATHON, MARATHON,
    W_3000_METRES_RACE_WALK,      W_5000_METRES_RACE_WALK,
    W_5_KILOMETRES_RCAE_WALK,     W_10_000_METRES_RCAE_WALK,
    W_10_KILOMETRES_RCAE_WALK,    W_20_000_METRES_RCAE_WALK,
    W_20_KILOMETRES_RCAE_WALK,    W_35_KILOMETRES_RCAE_WALK,
    W_50_KILOMETRES_RCAE_WALK,    HEPTATHLON,    DECATHLON,
    R_4_100_METRES_RELAY,         R_4_400_METRES_RELAY
}
