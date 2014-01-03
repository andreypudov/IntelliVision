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

package com.intellijustice.util.pools;

import com.intellijustice.core.Competition;

/**
 * Scoreboard pool implements separated window with ability to show competition
 * information and messages from judges.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Scoreboard.java
 * %date      01:50:00 PM, Nov 12, 2013
 */
public enum Scoreboard {

    INSTANCE;
    
    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellijustice.core.Manifest.NAME);
    
    /* the list of application properties */
    private static final Settings SETTINGS = Settings.getSettings();
    
    /* the scoreboard window represenation */
    private final ScoreboardWindow window = new ScoreboardWindow();

    static {
    }

    /* do not let anyone instantiate this class */
    private Scoreboard() {
    }

    /**
     * Returns an instance of scoreboard pool.
     *
     * @return the scoreboard pool.
     */
    public static synchronized Scoreboard getScoreboard() {
        return Scoreboard.INSTANCE;
    }

    /**
     * Sets scoreboard visible property.
     *
     * @param state the scoreboard visible property.
     */
    public void setVisible(final boolean state) {
        window.setVisible(state);
    }
    
    /**
     *  Closes the scoreboard window.
     */
    public void close() {
        window.close();
    }
    
    /**
     * Display given competition on the window.
     * 
     * @param competition the competition to show.
     */
    public void showCompetition(final Competition competition) {
        setVisible(true);
    }
}