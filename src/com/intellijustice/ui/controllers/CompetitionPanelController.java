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

import com.intellijustice.core.DisciplineType;
import com.intellijustice.core.Entry;
import com.intellijustice.core.Result;
import com.intellijustice.core.Competition;
import com.intellijustice.core.Athlete;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.intellijustice.ui.controls.RunningDataModel;
import com.intellijustice.ui.controls.RunningCellFactory;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class implements competition panel control UI logic.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      CompetitionPanelController.java
 * %date      03:10:00 PM, Oct 18, 2013
 */
public class CompetitionPanelController implements Initializable {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
              com.intellijustice.core.Manifest.NAME);

    @FXML private Label disciplineLabel;
    @FXML private Label roundLabel;

    @FXML private TableView disciplineTable;

    /* the competition representation */
    private Competition competition;

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
    }

    /**
     * Returns the competition representation.
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
        final double TABLE_HEADER_HEIGHT = 25.0;
        final double TABLE_ROW_HEIGHT    = 40.0;
        final double TABLE_BORDER_HEIGHT = 2.0;

        this.competition = competition;

        disciplineLabel.setText(competition.getDiscipline().toString());
        roundLabel.setText(competition.getRound().toString());

        switch (DisciplineType.getDisciplineType(competition.getDiscipline())) {
            case RUNNING:
                fillRunning(competition);
                break;
            case UNDEFINED:
            default:
                return;
        }

        /* set preferred table height to hide an empty rows */
        disciplineTable.setPrefHeight(TABLE_HEADER_HEIGHT + TABLE_BORDER_HEIGHT
                + (disciplineTable.getItems().size() * TABLE_ROW_HEIGHT));
    }

    /**
     * Construct table with a competition information.
     *
     * @param competition the competition entity represents running
     *                    type of discipline.
     */
    private void fillRunning(final Competition competition) {
        final TableColumn<RunningDataModel, Short>   rankColumn     = new TableColumn<>();
        final TableColumn<RunningDataModel, Short>   bibColumn      = new TableColumn<>();
        final TableColumn<RunningDataModel, String>  athleteColumn  = new TableColumn<>();
        final TableColumn<RunningDataModel, Long>    birthdayColumn = new TableColumn<>();
        final TableColumn<RunningDataModel, String>  countryColumn  = new TableColumn<>();
        final TableColumn<RunningDataModel, Integer> personalColumn = new TableColumn<>();
        final TableColumn<RunningDataModel, Integer> seasonColumn   = new TableColumn<>();
        final TableColumn<RunningDataModel, Short>   lineColumn     = new TableColumn<>();
        final TableColumn<RunningDataModel, Integer> resultColumn   = new TableColumn<>();
        final TableColumn<RunningDataModel, Short>   reactionColumn = new TableColumn<>();

        final List<RunningDataModel> competitionData = new ArrayList<>(16);

        for (final Entry entry : competition.getEntryList()) {
            final Athlete athlete = entry.getAthlete();
            final Result  result  = entry.getResults().get(0);

            competitionData.add(new RunningDataModel(entry.getRank(), entry.getBib(),
                    String.format("%s %s\n%s %s",
                            athlete.getFirstName(), athlete.getSecondName(),
                            athlete.getFirstNameLocalized(),
                            athlete.getSecondNameLocalized()),
                    athlete.getBirthday(), athlete.getCountry(),
                    entry.getPersonal(), entry.getSeason(),
                    entry.getLine(), result.getResult(), result.getReaction()));
        }

        rankColumn.setText("R");
        bibColumn.setText("Bib");
        athleteColumn.setText("Athlete");
        birthdayColumn.setText("Birthday");
        countryColumn.setText("Country");
        personalColumn.setText("Personal");
        seasonColumn.setText("Season");
        lineColumn.setText("L");
        resultColumn.setText("Result");
        reactionColumn.setText("Reaction");

        /* disable elements sorting for table columns */
        rankColumn.setSortable(false);
        bibColumn.setSortable(false);
        athleteColumn.setSortable(false);
        birthdayColumn.setSortable(false);
        countryColumn.setSortable(false);
        personalColumn.setSortable(false);
        seasonColumn.setSortable(false);
        lineColumn.setSortable(false);
        resultColumn.setSortable(false);
        reactionColumn.setSortable(false);

        rankColumn.setMinWidth(30.0);
        bibColumn.setMinWidth(30.0);
        athleteColumn.setMinWidth(160.0);
        birthdayColumn.setMinWidth(60.0);
        countryColumn.setMinWidth(60.0);
        personalColumn.setMinWidth(60.0);
        seasonColumn.setMinWidth(60.0);
        lineColumn.setMinWidth(30.0);
        resultColumn.setMinWidth(60.0);
        reactionColumn.setMinWidth(60.0);

        rankColumn.setCellValueFactory(new PropertyValueFactory<RunningDataModel, Short>("rank"));
        bibColumn.setCellValueFactory(new PropertyValueFactory<RunningDataModel, Short>("bib"));
        athleteColumn.setCellValueFactory(new PropertyValueFactory<RunningDataModel, String>("athlete"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<RunningDataModel, Long>("birthday"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<RunningDataModel, String>("country"));
        personalColumn.setCellValueFactory(new PropertyValueFactory<RunningDataModel, Integer>("personal"));
        seasonColumn.setCellValueFactory(new PropertyValueFactory<RunningDataModel, Integer>("season"));
        lineColumn.setCellValueFactory(new PropertyValueFactory<RunningDataModel, Short>("line"));
        resultColumn.setCellValueFactory(new PropertyValueFactory<RunningDataModel, Integer>("result"));
        reactionColumn.setCellValueFactory(new PropertyValueFactory<RunningDataModel, Short>("reaction"));

        athleteColumn.setCellFactory(RunningCellFactory.leftAlignedName());
        birthdayColumn.setCellFactory(RunningCellFactory.rightAlignedDate());
        countryColumn.setCellFactory(RunningCellFactory.centerAlignedImage());

        personalColumn.setCellFactory(RunningCellFactory.rightAlignedIntegerDate());
        seasonColumn.setCellFactory(RunningCellFactory.rightAlignedIntegerDate());
        resultColumn.setCellFactory(RunningCellFactory.rightAlignedIntegerDate());

        rankColumn.setCellFactory(RunningCellFactory.centralAlignedShort());
        bibColumn.setCellFactory(RunningCellFactory.centralAlignedShort());
        lineColumn.setCellFactory(RunningCellFactory.centralAlignedShort());

        reactionColumn.setCellFactory(RunningCellFactory.rightAlignedShort());

        disciplineTable.getColumns().clear();
        disciplineTable.setItems(FXCollections.observableList(competitionData));
        disciplineTable.getColumns().addAll(rankColumn, bibColumn,
                athleteColumn, birthdayColumn, countryColumn, personalColumn,
                seasonColumn, lineColumn, resultColumn, reactionColumn);
    }

    /**
     * Removes focus from selected table row when free area clicked.
     *
     * @param event the source of the event.
     */
    @FXML
    private void competitionPanelOnMouseClicked(final MouseEvent event) {
        disciplineTable.getSelectionModel().clearSelection();

        event.consume();
    }
}
