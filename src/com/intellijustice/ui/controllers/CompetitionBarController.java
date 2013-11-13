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

package com.intellijustice.ui.controllers;

import com.intellijustice.core.Competition;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class implements competition bar control UI logic.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      CompetitionBarController.java
 * %date      06:50:00 PM, Aug 06, 2013
 */
public class CompetitionBarController implements Initializable {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
              com.intellijustice.core.Manifest.NAME);

    /* the competition representation */
    private Competition competition;

    @FXML private VBox      competitionBar;
    @FXML private ImageView competitionImageView;
    @FXML private Label     competitionName;
    @FXML private Label     competitionType;

    /**
     * Initializes the controller class.
     *
     * @param url the location used to resolve relative paths for the root
     *            object, or null if the location is not known.
     * @param rb  the resources used to localize the root object, or null if the
     *            root object was not localized.
     */
    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
        competitionImageView.fitWidthProperty().bind(
                competitionBar.widthProperty());
    }

    /**
     * Returns competition representation.
     *
     * @return the competition representation.
     */
    public Competition getCompetition() {
        return competition;
    }

    /**
     * Sets competition representation.
     *
     * @param competition the competition representation.
     */
    public void setCompetition(final Competition competition) {
        this.competition = competition;

        competitionName.setText(competition.getDiscipline().toString());
        competitionType.setText(competition.getRound().toString());
    }

    /**
     * Sets selected control property to change competition image size.
     *
     * @param selected the control selected property.
     */
    public void setSelected(final boolean selected) {
        double size = selected ? 160.0 : 150.0;
        //competitionBar.setPrefSize(size, size);
    }
}
