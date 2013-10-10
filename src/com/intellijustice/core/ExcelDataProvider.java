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

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * The Excel data provider adds support to use Excel worksheet as a source data
 * for retrieving championship information.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      ExcelDataProvider.java
 * %date      05:00:00 PM, Aug 14, 2013
 */
public class ExcelDataProvider implements DefaultDataProvider {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellijustice.core.Manifest.NAME);

    private final ObjectProperty<Championship> championshipProperty
            = new SimpleObjectProperty<>();

    /* the Excel worksheets */
    private final File worksheet;

    /* the most newest championship data */
    private Championship lastState;

    /**
     * Constructs the Excel data provider for specified worksheet file.
     *
     * @param worksheet the Excel worksheet file.
     */
    public ExcelDataProvider(final File worksheet) {
        this.worksheet = worksheet;
    }

    /**
     * Returns current championship representation.
     *
     * @return the championship representation.
     */
    @Override
    public Championship getChampionship() {
        update();

        return lastState;
    }

    /**
     * Updates the championship representation and set new value to
     * the @championshipProperty property.
     */
    @Override
    public void update() {
        final Championship championship = readChampionship();

        /* fire competition changed event in case of new data */
        if (championship.equals(lastState) != true) {
            lastState = championship;
            championshipProperty.set(championship);
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

    /**
     * Reads championship representation.
     *
     * @return the championship representation.
     */
    private Championship readChampionship() {
        try {
            final ExcelDataReader reader = new ExcelDataReader(worksheet);
            final Championship championship = reader.readChampionship();

            return championship;
        } catch (IOException | IncorrectFormatException e) {
            LOG.log(Level.WARNING,
                    "Provided Excel workbook is in incorrect format. {0}",
                    e.getMessage());
        } catch (Exception e) {
            LOG.log(Level.WARNING,
                    "Excel workbook parse exception. {0}",
                    e.getMessage());
        }

        return new Championship(-1, "Untitled Championship",
                "Unnamed Country", "Unnamed City", Format.OUTDOOR);
    }
}
