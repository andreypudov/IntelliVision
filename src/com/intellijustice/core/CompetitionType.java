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
 * The competition type entity representation.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      CompetitionType.java
 * %date      05:20:00 PM, Aug 14, 2013
 */
public enum CompetitionType {
    _100m,        _200m,          _300m,        _400m,     _600m,
    _800m,        _1000m,         _1500m,       MILE,      _2000m,
    _3000m,       _2_Miles,       _5000m,       _10_000m,  _100mH,
    _110mH,       _400mH,         _2000mSC,     _3000mSC,  HIGH_JUMP,
    POLE_VAULT,   LONG_JUMP,      TRIPPLE_JUMP, SHOT_PUT,  DISCUS_THROW,
    HAMMER_THROW, JAVELIAN_THROW, _10km,        _15km,     _10_Miles,
    _20km,        HALF_MARATHON,  MARATHON,     _3000mW,   _5000mW,
    _5kmW,        _10_000mW,      _10kmW,       _20_000mW, _20kmW,
    _35kmW,       _50kmW,         HEPTATHLON,   DECATHLON, _4_100m,
    _4_400m
}
