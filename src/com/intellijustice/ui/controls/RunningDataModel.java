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

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;

/**
 * The data model represents a running type of discipline.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      RunningDataModel.java
 * %date      02:30:00 PM, Nov 03, 2013
 */
public class RunningDataModel {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellijustice.core.Manifest.NAME);

    private final SimpleIntegerProperty rank;
    private final SimpleIntegerProperty bib;
    private final SimpleStringProperty  athlete;
    private final SimpleLongProperty    birthday;
    private final SimpleStringProperty  country;
    private final SimpleIntegerProperty personal;
    private final SimpleIntegerProperty season;
    private final SimpleIntegerProperty line;
    private final SimpleIntegerProperty result;
    private final SimpleIntegerProperty reaction;

    public RunningDataModel(final short rank, final short bib,
            final String athlete, final long birthday,
            final String country, final int  personal,
            final int    season,  final short line,
            final int    result,  final short reaction) {
        this.rank     = new SimpleIntegerProperty(rank);
        this.bib      = new SimpleIntegerProperty(bib);
        this.athlete  = new SimpleStringProperty(athlete);
        this.birthday = new SimpleLongProperty(birthday);
        this.country  = new SimpleStringProperty(country);
        this.personal = new SimpleIntegerProperty(personal);
        this.season   = new SimpleIntegerProperty(season);
        this.line     = new SimpleIntegerProperty(line);
        this.result   = new SimpleIntegerProperty(result);
        this.reaction = new SimpleIntegerProperty(reaction);
    }

    /**
     * Returns the rank of the athlete (the number of the result).
     *
     * @return the rank of the athlete (the number of the result).
     */
    public short getRank() {
        return rank.getValue().shortValue();
    }

    /**
     * Returns the bib number of the athlete for the championship.
     *
     * @return the bib number of the athlete for the championship.
     */
    public short getBib() {
        return bib.getValue().shortValue();
    }

    /**
     * Returns the first and last name of the athlete.
     *
     * @return the first and last name of the athlete
     */
    public String getAthlete() {
        return athlete.getValue();
    }

    /**
     * Returns the birthday of the athlete.
     *
     * @return the birthday of the athlete.
     */
    public long getBirthday() {
        return birthday.getValue();
    }

    /**
     * Returns the country where the athlete from.
     *
     * @return the country where the athlete from.
     */
    public String getCountry() {
        return country.getValue();
    }

    /**
     * Returns the personal result of the athlete in the discipline.
     *
     * @return the personal result of the athlete in the discipline.
     */
    public int getPersonal() {
        return personal.getValue().intValue();
    }

    /**
     * Returns the season result of the athlete in the discipline.
     *
     * @return the season result of the athlete in the discipline.
     */
    public int getSeason() {
        return season.getValue().intValue();
    }

    /**
     * Returns the line of the athlete for the competition.
     *
     * @return the line of the athlete for the competition.
     */
    public short getLine() {
        return line.getValue().shortValue();
    }

    /**
     * Returns the result value.
     *
     * @return the result value.
     */
    public int getResult() {
        return result.getValue().intValue();
    }

    /**
     * Returns the reaction time of the athlete.
     *
     * @return the reaction time of the athlete.
     */
    public short getReaction() {
        return reaction.getValue().shortValue();
    }

    /**
     * Sets the rank of the athlete (the number of the result).
     *
     * @param rank the rank of the athlete (the number of the result).
     */
    public void setRank(final short rank) {
        this.rank.setValue(rank);
    }

    /**
     * Sets the bib number of the athlete for the championship.
     *
     * @param bib the bib number of the athlete for the championship.
     */
    public void setBib(final short bib) {
        this.bib.setValue(bib);
    }

    /**
     * Sets the first and last name of the athlete.
     *
     * @param athlete the first and last name of the athlete
     */
    public void setAthlete(final String athlete) {
        this.athlete.setValue(athlete);
    }

    /**
     * Sets the birthday of the athlete.
     *
     * @param birthday the birthday of the athlete.
     */
    public void setBirthday(final long birthday) {
        this.birthday.setValue(birthday);
    }

    /**
     * Sets the country where the athlete from.
     *
     * @param country the country where the athlete from.
     */
    public void setCountry(final String country) {
        this.country.setValue(country);
    }

    /**
     * Sets the personal result of the athlete in the discipline.
     *
     * @param personal the personal result of the athlete in the discipline.
     */
    public void setPersonal(final int personal) {
        this.personal.setValue(personal);
    }

    /**
     * Sets the season result of the athlete in the discipline.
     *
     * @param season the season result of the athlete in the discipline.
     */
    public void setSeason(final int season) {
        this.season.setValue(season);
    }

    /**
     * Sets the line of the athlete for the competition.
     *
     * @param line the line of the athlete for the competition.
     */
    public void setLine(final short line) {
        this.line.setValue(line);
    }

    /**
     * Sets the result value.
     *
     * @param result the result value.
     */
    public void setResult(final int result) {
        this.result.setValue(result);
    }

    /**
     * Sets the reaction time of the athlete.
     *
     * @param reaction the reaction time of the athlete.
     */
    public void setReaction(final short reaction) {
        this.reaction.setValue(reaction);
    }
}
