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

package com.intellijustice.ui.controls;

import com.intellijustice.core.Competition;
import com.intellijustice.ui.controllers.CompetitionPanelController;
import com.intellijustice.util.StatusCodes;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

/**
 * Competition panel provides competition details.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      CompetitionPanel.java
 * %date      03:00:00 PM, Oct 18, 2013
 */
public class CompetitionPanel extends VBox {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
              com.intellijustice.core.Manifest.NAME);

    /* loads an object hierarchy from an XML document */
    private final FXMLLoader fxmlLoader;

    /* controller initialization interface */
    private final CompetitionPanelController controller;

    public CompetitionPanel(final Competition competition) {
        fxmlLoader = new FXMLLoader(getClass().getResource(
            "/com/intellijustice/resources/schemas/CompetitionPanel.fxml"));

        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (java.io.IOException e) {
            LOG.severe(e.getMessage());
            System.exit(StatusCodes.EXIT_FAILURE);
        }

        controller = fxmlLoader.getController();
        controller.setCompetition(competition);
    }

    /**
     * Returns the competition representation.
     *
     * @return the competition representation.
     */
    public Competition getCompetition() {
        return controller.getCompetition();
    }

    /**
     * Sets competition representation.
     *
     * @param competition the competition representation.
     */
    public void setCompetition(final Competition competition) {
        controller.setCompetition(competition);
    }
}
