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
import com.intellijustice.ui.controls.CompetitionPanel;
import com.intellijustice.util.pools.Core;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
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
    final static int TILE_MIN_WIDTH      = 90;

    /* championship data provider */
    private final DefaultDataProvider provider = Core.getDataProvider();

    @FXML private FlowPane   competitionList;
    @FXML private ScrollPane competitionScrollPane;

    private CompetitionPanel competitionPanel  = null;
    private int              panelIndex        = 0;

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
        /* aligns competition tiles on the pane */
        competitionList.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(final ObservableValue<? extends Number> ov,
                                final Number t, final Number t1) {
                final int workingWidth = t1.intValue()
                        - (HORIZONTAL_PADDING + HORIZONTAL_PADDING);
                final int tilesPerRow  = workingWidth
                        / (TILE_MIN_WIDTH + HORIZONTAL_GAP);
                final int tileWidth    = (workingWidth
                        - ((tilesPerRow - 1) * HORIZONTAL_GAP)) / tilesPerRow;

                System.err.println("1: " + competitionList.getWidth());

                for (Node node : competitionList.getChildren()) {
                    if (node instanceof CompetitionBar) {
                        CompetitionBar bar = (CompetitionBar) node;
                        bar.setPrefWidth(tileWidth);
                    } else {
                        // TODO
                        CompetitionPanel panel = (CompetitionPanel) node;
                        //panel.setPrefWidth(tileWidth);
                    }
                }
            }
        });

        competitionList.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                if (competitionPanel != null) {
                    competitionList.getChildren().remove(competitionPanel);
                    competitionPanel = null;
                }
            }});

        competitionScrollPane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(final ObservableValue<? extends Number> ov,
                                final Number t, final Number t1) {
                System.err.println("3: " + competitionList.getWidth());
                System.err.println("2: " + competitionScrollPane.getWidth());
            }});

        /* update competition data on change */
        Core.getDataProvider().championshipProperty().addListener(
                new ChangeListener<Championship>() {
            @Override
            public void changed(ObservableValue<? extends Championship> ov,
                                Championship t, Championship t1) {
                /* update the tiles in FX thread when possible */
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        update();
                    }
                });
            }});

        /* update competition data */
        update();
    }

    /**
     * Updates competition list.
     */
    private void update() {
        final Championship championship = provider.getChampionship();
        competitionList.getChildren().clear();

        for (Competition competition : championship.getCompetitionList()) {
            final CompetitionBar bar = new CompetitionBar(competition);

            bar.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    displayPanel(bar);

                    e.consume();
                }
            });

            competitionList.getChildren().add(bar);
        }
    }

    /**
     * Shows or hides information panel for specified competition.
     *
     * Scenarios:
     *  1) Panel is hidden. A tile is clicked.
     *     Show panel.
     *  2) Panel is shown. The same tile is clicked.
     *     Hide panel.
     *  3) Panel is shown. An another tile is clicked.
     *     Show panel with a content of clicked tile.
     *  4) Panel is shown. User clicks at the free space between the tiles.
     *     Hide panel.
     *
     * @param bar the competition bar that should be used.
     */
    private void displayPanel(final CompetitionBar bar) {
        final int tileCount    = competitionList.getChildren().size();
        final int tileIndex    = competitionList.getChildren().indexOf(bar);
        final int workingWidth = (int) competitionList.getWidth()
                - (HORIZONTAL_PADDING + HORIZONTAL_PADDING);
        final int tilesPerRow  = workingWidth
                / (TILE_MIN_WIDTH + HORIZONTAL_GAP);
        final int tileAfter    = (((tileIndex / tilesPerRow) + 1) * tilesPerRow);

        /* Scenario №1. Show panel for a clicked tile. */
        if (competitionPanel == null) {
            competitionPanel = new CompetitionPanel(bar.getCompetition());
            competitionPanel.setPrefWidth(workingWidth);

            competitionList.getChildren().add(
                    ((tileAfter > tileCount) ? tileCount : tileAfter),
                    competitionPanel);
            panelIndex = tileAfter;

            return;
        }

        /* Scenario №2. Hide panel. */
        if (competitionPanel.getCompetition().equals(
                bar.getCompetition())) {
            competitionList.getChildren().remove(competitionPanel);
            competitionPanel = null;

            return;
        }

        /* Scenario №3. Show panel with a content of clicked tile. */
        if (panelIndex == tileAfter) {
            /* Clicked tile locates on the same row. */
            competitionPanel.setCompetition(bar.getCompetition());
        } else {
            /* Clicked tile locates on another row. Hide exists panel and show new one. */
            competitionList.getChildren().remove(competitionPanel);

            competitionPanel = new CompetitionPanel(bar.getCompetition());
            competitionPanel.setPrefWidth(workingWidth);

            /* tileCount = tileCount - 1 because of removed panel */
            competitionList.getChildren().add(
                    ((tileAfter > tileCount) ? (tileCount - 1) : tileAfter),
                    competitionPanel);
            panelIndex = tileAfter;
        }
    }
}
