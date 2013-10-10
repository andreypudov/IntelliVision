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

import com.intellijustice.core.Championship;
import com.intellijustice.core.Competition;
import com.intellijustice.core.DefaultDataProvider;
import com.intellijustice.ui.controls.CompetitionBar;
import com.intellijustice.util.pools.Core;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;

/**
 * FXML Controller class provides general view functionality.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      HomeModuleController.java
 * %date      12:00:00 AM, Oct 22, 2012
 */
public class HomeModuleController implements Initializable {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
              com.intellijustice.core.Manifest.NAME);

    final static int HORIZONTAL_GAP      = 40;
    final static int HORIZONTAL_PADDING  = 30;
    final static int TILE_MIN_WIDTH      = 124;
    final static int TILE_MAX_WIDTH      = 158;

    /* championship data provider */
    private final DefaultDataProvider provider = Core.getDataProvider();

    @FXML private FlowPane   competitionList;
    @FXML private ScrollPane competitionScrollPane;

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
        /* aligns competition tils on the pane */
        competitionList.widthProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(final ObservableValue<? extends Number> ov,
                                final Number t, final Number t1) {
                final int workingWidth = (int) (t1.doubleValue() - (HORIZONTAL_PADDING + HORIZONTAL_PADDING));
                final int tilesPerRow  = workingWidth / (TILE_MIN_WIDTH + HORIZONTAL_GAP);
                final int tileWidth    = (workingWidth - ((tilesPerRow - 1) * HORIZONTAL_GAP)) / tilesPerRow;

                for (Node node : competitionList.getChildren()) {
                    CompetitionBar bar = (CompetitionBar) node;
                    bar.setPrefWidth(tileWidth);
                }
            }
        });

        /* update competition data */
        update();
    }

    /**
     * Updates competition list.
     */
    private void update() {
        final Championship championship = provider.getChampionship();

        for (Competition competition : championship.getCompetitionList()) {
            final CompetitionBar bar = new CompetitionBar(competition);

            competitionList.getChildren().add(bar);
        }

        competitionList.getChildren().add(new CompetitionBar(championship.getCompetitionList().get(0)));
        competitionList.getChildren().add(new CompetitionBar(championship.getCompetitionList().get(0)));
        competitionList.getChildren().add(new CompetitionBar(championship.getCompetitionList().get(0)));
        competitionList.getChildren().add(new CompetitionBar(championship.getCompetitionList().get(0)));
        competitionList.getChildren().add(new CompetitionBar(championship.getCompetitionList().get(0)));
        competitionList.getChildren().add(new CompetitionBar(championship.getCompetitionList().get(0)));
        competitionList.getChildren().add(new CompetitionBar(championship.getCompetitionList().get(0)));
        competitionList.getChildren().add(new CompetitionBar(championship.getCompetitionList().get(0)));
        competitionList.getChildren().add(new CompetitionBar(championship.getCompetitionList().get(0)));
    }
}
