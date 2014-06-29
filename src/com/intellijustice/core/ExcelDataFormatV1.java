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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.logging.Level;

import com.ibm.icu.text.Transliterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Provides support for the Excel data format version 1.0.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      ExcelDataFormatV1.java
 * %date      08:40:00 AM, Sep 04, 2013
 */
public class ExcelDataFormatV1 implements ExcelDataFormatDefault {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellijustice.core.Manifest.NAME);

    private static final int NAME_ROW_INDEX     = 0;
    private static final int NAME_CELL_INDEX    = 0;
    private static final int COUNTRY_ROW_INDEX  = 1;
    private static final int COUNTRY_CELL_INDEX = 2;
    private static final int CITY_ROW_INDEX     = 1;
    private static final int CITY_CELL_INDEX    = 0;
    private static final int FORMAT_ROW_INDEX   = 1;
    private static final int FORMAT_CELL_INDEX  = 3;

    private static final int DISCIPLINE_ROW_INDEX   = 3;
    private static final int DISCIPLINE_CELL_INDEX  = 2;
    private static final int ROUND_ROW_INDEX        = 3;
    private static final int ROUND_CELL_INDEX       = 3;
    private static final int SEX_ROW_INDEX          = 3;
    private static final int SEX_CELL_INDEX         = 4;
    private static final int START_TIME_ROW_INDEX   = 3;
    private static final int START_TIME_CELL_INDEX  = 6;
    private static final int END_TIME_ROW_INDEX     = 3;
    private static final int END_TIME_CELL_INDEX    = 8;
    private static final int TEMPERATURE_ROW_INDEX  = 4;
    private static final int TEMPERATURE_CELL_INDEX = 6;
    private static final int HUMIDITY_ROW_INDEX     = 4;
    private static final int HUMIDITY_CELL_INDEX    = 8;
    private static final int WIND_ROW_INDEX         = 4;
    private static final int WIND_CELL_INDEX        = 9;

    private static final int COMPETITION_DATA_ROW   = 6;

    private static final String FORMAT_INDOOR_VALUE  = "Indoor";
    private static final String FORMAT_OUTDOOR_VALUE = "Outdoor";
    private static final String SEX_MALE_VALUE       = "Men";
    private static final String SEX_FEMALE_VALUE     = "Women";

    /* the Excel worksheets file */
    private final File file;

    /* the Excel workbook instance */
    private final Workbook workbook;

    /**
     * Constructs Excel data reader with an support for format version 1.0 for
     * specified Excel workbook.
     *
     * @param file      the workbook file object.
     * @param workbook  the workbook to read.
     */
    public ExcelDataFormatV1(final File file, final Workbook workbook) {
        this.file      = file;
        this.workbook  = workbook;
    }

    /**
     * Reads and constructs championship representation.
     *
     * @return the championship representation.
     * @throws IncorrectFormatException
     *         the source of an exception.
     */
    @Override
    public Championship readChampionship() throws IncorrectFormatException {
        /* start parsing the workbook content */
        final Cell cellName = workbook.getSheetAt(0
                ).getRow(NAME_ROW_INDEX
                ).getCell(NAME_CELL_INDEX);
        final Cell cellCountry = workbook.getSheetAt(0
                ).getRow(COUNTRY_ROW_INDEX
                ).getCell(COUNTRY_CELL_INDEX);
        final Cell cellCity = workbook.getSheetAt(0
                ).getRow(CITY_ROW_INDEX
                ).getCell(CITY_CELL_INDEX);
        final Cell cellFormat = workbook.getSheetAt(0
                ).getRow(FORMAT_ROW_INDEX
                ).getCell(FORMAT_CELL_INDEX);

        if ((cellName.getCellType()           != Cell.CELL_TYPE_STRING)
                || (cellCountry.getCellType() != Cell.CELL_TYPE_STRING)
                || (cellCity.getCellType()    != Cell.CELL_TYPE_STRING)
                || (cellFormat.getCellType()  != Cell.CELL_TYPE_STRING)

                || ((cellFormat.getStringCellValue().equals(
                        FORMAT_INDOOR_VALUE) == false)
                    && (cellFormat.getStringCellValue().equals(
                        FORMAT_OUTDOOR_VALUE) == false))) {
            throw new IncorrectFormatException(
                    "The championship information is in incorrect format.");
        }

        /* the championship representation */
        final Championship championship = new Championship(
                file.getAbsolutePath().hashCode(),
                cellName.getStringCellValue(), cellCountry.getStringCellValue(),
                cellCity.getStringCellValue(),
                (cellFormat.getStringCellValue().equals(FORMAT_INDOOR_VALUE)
                    ? Format.INDOOR : Format.OUTDOOR));

        for (int index = 0; index < workbook.getNumberOfSheets(); ++index) {
            final Competition competition
                    = readCompetition(workbook.getSheetAt(index));

            championship.addCompetition(competition);
        }

        return championship;
    }

    /**
     * Reads and constructs competition representation.
     *
     * @param sheet the sheet to read.
     *
     * @return      the competition representation.
     * @throws IncorrectFormatException
     *              the source of an exception.
     */
    private Competition readCompetition(final Sheet sheet)
            throws IncorrectFormatException {
        final Cell cellDiscipline = sheet.getRow(DISCIPLINE_ROW_INDEX
                ).getCell(DISCIPLINE_CELL_INDEX);
        final Cell cellRound = sheet.getRow(ROUND_ROW_INDEX
                ).getCell(ROUND_CELL_INDEX);
        final Cell cellSex = sheet.getRow(SEX_ROW_INDEX
                ).getCell(SEX_CELL_INDEX);
        final Cell cellStartTime = sheet.getRow(START_TIME_ROW_INDEX
                ).getCell(START_TIME_CELL_INDEX);
        final Cell cellEndTime = sheet.getRow(END_TIME_ROW_INDEX
                ).getCell(END_TIME_CELL_INDEX);
        final Cell cellTemperature = sheet.getRow(TEMPERATURE_ROW_INDEX
                ).getCell(TEMPERATURE_CELL_INDEX);
        final Cell cellHumidity = sheet.getRow(HUMIDITY_ROW_INDEX
                ).getCell(HUMIDITY_CELL_INDEX);

        if ((cellDiscipline.getCellType()         != Cell.CELL_TYPE_STRING)
                || (cellRound.getCellType()       != Cell.CELL_TYPE_STRING)
                || (cellSex.getCellType()         != Cell.CELL_TYPE_STRING)
                || (cellStartTime.getCellType()   != Cell.CELL_TYPE_STRING)
                || (cellEndTime.getCellType()     != Cell.CELL_TYPE_STRING)
                || (cellTemperature.getCellType() != Cell.CELL_TYPE_STRING)
                || (cellHumidity.getCellType()    != Cell.CELL_TYPE_STRING)

                || ((cellSex.getStringCellValue().equals(
                        SEX_MALE_VALUE) == false)
                    && (cellSex.getStringCellValue().equals(
                        SEX_FEMALE_VALUE) == false))) {
            throw new IncorrectFormatException(
                    "The championship " + sheet.getSheetName()
                    + " information is in incorrect format.");
        }

        final Discipline discipline = Discipline.getDiscipline(
                cellDiscipline.getStringCellValue());
        final Round      round      = Round.getRound(
                cellRound.getStringCellValue());

        final boolean    sex = cellSex.getStringCellValue().equals("Men");
        final long       startTime;
        final long       endTime;
        final short      temperature;
        final short      humidity;

        if (discipline == Discipline.UNDEFINED) {
            throw new IncorrectFormatException("The discipline "
                    + cellDiscipline.getStringCellValue() + " is incorrect.");
        }

        if (round == Round.UNDEFINED) {
            throw new IncorrectFormatException("The round "
                    + cellDiscipline.getStringCellValue() + " is incorrect.");
        }

        try {
            final DateFormat formatter = new SimpleDateFormat("hh:mm");

            startTime   = formatter.parse(
                    cellStartTime.getStringCellValue()).getTime();
            endTime     = formatter.parse(
                    cellEndTime.getStringCellValue()).getTime();
            temperature = Short.parseShort(cellTemperature.getStringCellValue());
            humidity    = Short.parseShort(cellHumidity.getStringCellValue());
        } catch (ParseException e) {
            throw new IncorrectFormatException("The start or end time "
                    + cellStartTime.getStringCellValue() + ", "
                    + cellEndTime.getStringCellValue()
                    + " are incorrect.");
        } catch(NumberFormatException e) {
            throw new IncorrectFormatException(
                    "The temperature or humidity "
                    + cellTemperature.getStringCellValue() + ", "
                    + cellHumidity.getStringCellValue()
                    + " are in incorrect.");
        }

        final Competition competition = new Competition(
                sheet.getSheetName().hashCode(), discipline, round,
                sex, startTime, endTime, temperature, humidity);

        switch (DisciplineType.getDisciplineType(discipline)) {
            case RUNNING:
                readRunning(sheet, competition);
                break;
            case UNDEFINED:
            default:
                throw new IncorrectFormatException("The discipline "
                        + cellDiscipline.getStringCellValue()
                        + " is incorrect.");
        }

        return competition;
    }

    /**
     * Reads running type of the competition.
     *
     * @param sheet       the Excel worksheet to read.
     * @param competition the competition where to add data.
     * @throws IncorrectFormatException
     *                    the source of an exception.
     */
    private void readRunning(final Sheet sheet,
            final Competition competition)
            throws IncorrectFormatException {
        final DateFormat formatter = new SimpleDateFormat("dd.MM.yy");

        final Cell cellWind = workbook.getSheetAt(0
                ).getRow(WIND_ROW_INDEX
                ).getCell(WIND_CELL_INDEX);
        final short wind = parseShort(cellWind.getStringCellValue());

        /* read entry data row by row */
        for (int rowIndex = COMPETITION_DATA_ROW;
                rowIndex <= sheet.getLastRowNum(); ++rowIndex) {
            final Row row = sheet.getRow(rowIndex);

            final Cell cellRank     = row.getCell(0);
            final Cell cellBib      = row.getCell(1);
            final Cell cellName     = row.getCell(2);
            final Cell cellBirthday = row.getCell(3);
            final Cell cellCountry  = row.getCell(4);
            final Cell cellPersonal = row.getCell(5);
            final Cell cellSeason   = row.getCell(6);
            final Cell cellLine     = row.getCell(7);
            final Cell cellResult   = row.getCell(8);
            final Cell cellReaction = row.getCell(9);

            /* no more data in the sheet */
            if (cellRank == null) {
                break;
            }

            if ((cellRank.getCachedFormulaResultType()            != Cell.CELL_TYPE_NUMERIC)
                    || (cellBib.getCachedFormulaResultType()      != Cell.CELL_TYPE_NUMERIC)
                    || (cellName.getCachedFormulaResultType()     != Cell.CELL_TYPE_STRING)
                    || (cellBirthday.getCachedFormulaResultType() != Cell.CELL_TYPE_STRING)
                    || (cellCountry.getCachedFormulaResultType()  != Cell.CELL_TYPE_STRING)
                    || (cellPersonal.getCachedFormulaResultType() != Cell.CELL_TYPE_STRING)
                    || (cellSeason.getCachedFormulaResultType()   != Cell.CELL_TYPE_STRING)
                    || (cellLine.getCachedFormulaResultType()     != Cell.CELL_TYPE_NUMERIC)
                    || (cellResult.getCachedFormulaResultType()   != Cell.CELL_TYPE_STRING)
                    || (cellReaction.getCachedFormulaResultType() != Cell.CELL_TYPE_NUMERIC)) {
                final String info = String.format(
                        "[Rank (num) %b, Bib (num) %b, Name(str) %b, "
                        + "Birthday (str) %b, Country (str) %b, "
                        + "Personal (str) %b, Season (str) %b, "
                        + "Line (num) %b, Result (str) %b, Reaction (num) %b]",

                        (cellRank.getCachedFormulaResultType()     != Cell.CELL_TYPE_NUMERIC),
                        (cellBib.getCachedFormulaResultType()      != Cell.CELL_TYPE_NUMERIC),
                        (cellName.getCachedFormulaResultType()     != Cell.CELL_TYPE_STRING),
                        (cellBirthday.getCachedFormulaResultType() != Cell.CELL_TYPE_STRING),
                        (cellCountry.getCachedFormulaResultType()  != Cell.CELL_TYPE_STRING),
                        (cellPersonal.getCachedFormulaResultType() != Cell.CELL_TYPE_STRING),
                        (cellSeason.getCachedFormulaResultType()   != Cell.CELL_TYPE_STRING),
                        (cellLine.getCachedFormulaResultType()     != Cell.CELL_TYPE_NUMERIC),
                        (cellResult.getCachedFormulaResultType()   != Cell.CELL_TYPE_STRING),
                        (cellReaction.getCachedFormulaResultType() != Cell.CELL_TYPE_NUMERIC));

                throw new IncorrectFormatException(
                        "The competition entry information at row "
                                + (rowIndex + 1) + " in competition "
                                + sheet.getSheetName()
                                + " is in incorrect format. " + info);
            }

            try {
                // TODO change athlete entry to use country and bithplace format
                
                final Athlete athlete = new Athlete(
                        (long) cellName.getStringCellValue().hashCode(),
                        transliterate(getFirstName(cellName.getStringCellValue())),
                        "", /* middle name is omitted */
                        transliterate(getLastName(cellName.getStringCellValue())),
                        getFirstName(cellName.getStringCellValue()),
                        "", /* middle name is omitted */
                        getLastName(cellName.getStringCellValue()),
                        formatter.parse(cellBirthday.getStringCellValue()).getTime(),
                        0L, competition.getSex(), 0L);
                final Result result = new Result(
                        parseDate(cellResult.getStringCellValue()),
                        (short) -1, (short) -1,
                        parseDate(cellResult.getStringCellValue()),
                        (short) cellReaction.getNumericCellValue(), wind);
                final Entry entry = new Entry(-1, athlete,
                        (short) cellRank.getNumericCellValue(),
                        (short) cellBib.getNumericCellValue(),
                        (short) cellLine.getNumericCellValue(),
                        parseDate(cellPersonal.getStringCellValue()),
                        parseDate(cellSeason.getStringCellValue()));

                /* add result to the entry - running allows one result only */
                entry.addResult(result);

                /* add entry to the competition */
                competition.addEntry(entry);
            } catch (Exception e) {
                throw new IncorrectFormatException(
                        "The result entry for competition "
                        + competition + " is incorrect.");
            }
        }
    }

    /**
     * Returns the first name of athlete.
     *
     * @param name the string containing the first and last names.
     * @return     the first name of the athlete.
     */
    private String getFirstName(final String name) {
        final StringBuilder builder = new StringBuilder(name.length());
        final String[]      list    = name.split(" ");

        for (String entry : list) {
            for (char character : entry.toCharArray()) {
                if (Character.isLowerCase(character)) {
                    builder.append(entry).append(' ');
                    break;
                }
            }
        }

        return builder.toString().trim();
    }

    /**
     * Returns the last name of athlete.
     *
     * @param name the string containing the first and last names.
     * @return     the last name of the athlete.
     */
    private String getLastName(final String name) {
        final StringBuilder builder = new StringBuilder(name.length());
        final String[]      list    = name.split(" ");

        for (String entry : list) {
            boolean isLowerCase = false;

            for (char character : entry.toCharArray()) {
                if (Character.isLowerCase(character)) {
                    isLowerCase = true;
                    break;
                }
            }

            if (isLowerCase == false) {
                builder.append(entry).append(' ');
            }
        }

        return builder.toString().trim();
    }

    /**
     * Parses the string argument as a signed decimal short. If string
     * contains data that can not be converted to short, return zero.
     *
     * @param value the string contain short value.
     * @return      the short representation of the string value or zero.
     */
    public static short parseShort(final String value) {
        try {
            return Short.parseShort(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Parses date value given as a string to int specifies Unix time.
     * Supported date formats: ss.SS, mm:ss.SS, hh:mm:ss and hh:mm:ss.SS,
     * where each pair can be presented by one or two numbers.
     *
     * @param date the date value to parse.
     * @return     the int value of date as a Unix time number.
     */
    public static int parseDate(final String date) {
        /* the map of supported date formats and regular expressions */
        final String[][] map = {
                {"([01]?[0-9]|[0-5][0-9])\\.([01]?[0-9]|[0-9][0-9])", "ss.SS"},
                {"([01]?[0-9]|[0-5][0-9]):([01]?[0-9]|[0-5][0-9])\\."
                        + "([01]?[0-9]|[0-9][0-9])", "mm:ss.SS"},
                {"([01]?[0-9]|[0-5][0-9]):([01]?[0-9]|[0-5][0-9]):"
                        + "([01]?[0-9]|[0-5][0-9])", "hh:mm:ss"},
                {"([01]?[0-9]|[0-5][0-9]):([01]?[0-9]|[0-5][0-9]):"
                        + "([01]?[0-9]|[0-5][0-9])\\."
                        + "([01]?[0-9]|[0-9][0-9])", "hh:mm:ss.SS"}};

        try {
            for (final String[] entry : map) {
                final String key   = entry[0];
                final String value = entry[1];

                if (date.matches(key)) {
                    final DateFormat formatter = new SimpleDateFormat(value);
                    formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

                    return (int) (formatter.parse(date)).getTime();
                }
            }
        } catch (Exception e) {
            return 0;
        }

        return 0;
    }

    /**
     * Transliterates Russian name to English.
     *
     * @param russian the athlete name in Russian.
     * @return        the athlete name in English.
     */
    public static String transliterate(final String russian) {
        try {
            final Transliterator transliterator = Transliterator.getInstance(
                    "Russian-Latin/BGN");

            return transliterator.transliterate(russian);
        } catch (Exception e) {
            LOG.log(Level.WARNING,
                    "Could not translate Russian name to English. {0}",
                    e.getMessage());

            return russian;
        }
    }
}
