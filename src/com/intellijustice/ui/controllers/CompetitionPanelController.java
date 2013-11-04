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

import com.intellijustice.core.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.intellijustice.ui.controls.RunningDataModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

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

    @FXML private VBox      competitionPanel;
    @FXML private Label     disciplineLabel;
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
        this.competition = competition;

        disciplineLabel.setText(competition.getDiscipline().toString());

        switch (DisciplineType.getDisciplineType(competition.getDiscipline())) {
            case RUNNING:
                fillRunning(competition);
                break;
            case UNDEFINED:
            default:
                return;
        }
    }

    /**
     * Construct table with a competition information.
     *
     * @param competition the competition entity represents running
     *                    type of discipline.
     */
    private void fillRunning(final Competition competition) {
        final TableColumn rankColumn     = new TableColumn();
        final TableColumn bibColumn      = new TableColumn();
        final TableColumn athleteColumn  = new TableColumn();
        final TableColumn birthdayColumn = new TableColumn();
        final TableColumn countryColumn  = new TableColumn();
        final TableColumn personalColumn = new TableColumn();
        final TableColumn seasonColumn   = new TableColumn();
        final TableColumn lineColumn     = new TableColumn();
        final TableColumn resultColumn   = new TableColumn();
        final TableColumn reactionColumn = new TableColumn();

        final List<RunningDataModel> competitionData = new ArrayList<>();

        for (final Entry entry : competition.getEntryList()) {
            final Athlete athlete = entry.getAthlete();
            final Result  result  = entry.getResults().get(0);

            competitionData.add(new RunningDataModel(entry.getRank(), entry.getBib(),
                    athlete.getFirstName() + " " + athlete.getSecondName(),
                    athlete.getBirthday(), athlete.getCountry(),
                    (short) entry.getPersonal(), (short) entry.getSeason(),
                    entry.getLine(), result.getResult(), result.getReaction()));
        }

        rankColumn.setText("Rank");
        bibColumn.setText("Bib");
        athleteColumn.setText("Athlete");
        birthdayColumn.setText("Birthday");
        countryColumn.setText("Country");
        personalColumn.setText("Personal");
        seasonColumn.setText("Season");
        lineColumn.setText("L");
        resultColumn.setText("Result");
        reactionColumn.setText("Reaction");

        rankColumn.setMinWidth(40.0);
        bibColumn.setMinWidth(40.0);
        athleteColumn.setMinWidth(200.0);
        birthdayColumn.setMinWidth(60.0);
        countryColumn.setMinWidth(60.0);
        personalColumn.setMinWidth(60.0);
        seasonColumn.setMinWidth(60.0);
        lineColumn.setMinWidth(20.0);
        resultColumn.setMinWidth(60.0);
        reactionColumn.setMinWidth(60.0);

        rankColumn.setCellValueFactory(new PropertyValueFactory<RunningDataModel, Integer>("rank"));
        bibColumn.setCellValueFactory(new PropertyValueFactory<RunningDataModel, Integer>("bib"));
        athleteColumn.setCellValueFactory(new PropertyValueFactory<RunningDataModel, String>("athlete"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<RunningDataModel, Long>("birthday"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<RunningDataModel, String>("country"));
        personalColumn.setCellValueFactory(new PropertyValueFactory<RunningDataModel, String>("personal"));
        seasonColumn.setCellValueFactory(new PropertyValueFactory<RunningDataModel, String>("season"));
        lineColumn.setCellValueFactory(new PropertyValueFactory<RunningDataModel, String>("line"));
        resultColumn.setCellValueFactory(new PropertyValueFactory<RunningDataModel, String>("result"));
        reactionColumn.setCellValueFactory(new PropertyValueFactory<RunningDataModel, String>("reaction"));

        disciplineTable.getColumns().clear();
        disciplineTable.setItems(FXCollections.observableList(competitionData));
        disciplineTable.getColumns().addAll(rankColumn, bibColumn,
                athleteColumn, birthdayColumn, countryColumn, personalColumn,
                seasonColumn, lineColumn, resultColumn, reactionColumn);
    }
}
