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

import com.intellijustice.util.pools.Server;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * The SQL data provider adds support to use MySQL Database as a source data
 * for retrieving championship information.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      SQLDataProvider.java
 * %date      02:40:00 PM, Aug 24, 2013
 */
public class SQLDataProvider implements DefaultDataProvider {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellijustice.core.Manifest.NAME);

    private final ObjectProperty<Championship> championshipProperty
            = new SimpleObjectProperty<>();

    /* the database server connection layer */
    private static final Server SERVER = Server.getDatabaseServer();

    /**
     * Constructs new SQL data provider.
     */
    public SQLDataProvider() {
    }

    /**
     * Returns current championship representation.
     *
     * @return the championship representation.
     */
    @Override
    public Championship getChampionship() {
        return new Championship(-1, "Name", "Country", "City", Format.OUTDOOR);
    }

    /**
     * Updates the championship representation and may fire one of the events.
     * @ChampionshipChangedEvent and @CompetitionChangedEvent in case of the
     * championship properties and the competition values updates respectively.
     */
    @Override
    public void update() {
        if (SERVER.isConnected() == false) {
            LOG.info("Connecting to database server...");
            SERVER.connect();
        }
    }

    /**
     * Returns the championship property.
     *
     * @return the championship property;
     */
    @Override
    public ObjectProperty<Championship> championshipProperty() {
        return championshipProperty;
    }
}