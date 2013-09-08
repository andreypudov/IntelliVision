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

import com.intellijustice.util.Utilities;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

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

    /* the Excel workbook instance */
    private final HSSFWorkbook workbook;

    /**
     * Constructs Excel data reader with an support for format version 1.0 for
     * specified Excel workbook.
     *
     * @param workbook  the workbook to read.
     */
    public ExcelDataFormatV1(final HSSFWorkbook workbook) {
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
        final HSSFCell cellName = workbook.getSheetAt(0
                ).getRow(NAME_ROW_INDEX
                ).getCell(NAME_CELL_INDEX);
        final HSSFCell cellCountry = workbook.getSheetAt(0
                ).getRow(COUNTRY_ROW_INDEX
                ).getCell(COUNTRY_CELL_INDEX);
        final HSSFCell cellCity = workbook.getSheetAt(0
                ).getRow(CITY_ROW_INDEX
                ).getCell(CITY_CELL_INDEX);
        final HSSFCell cellFormat = workbook.getSheetAt(0
                ).getRow(FORMAT_ROW_INDEX
                ).getCell(FORMAT_CELL_INDEX);

        if ((cellName.getCellType() != HSSFCell.CELL_TYPE_STRING)
                || (cellCountry.getCellType() != HSSFCell.CELL_TYPE_STRING)
                || (cellCity.getCellType() != HSSFCell.CELL_TYPE_STRING)
                || (cellFormat.getCellType() != HSSFCell.CELL_TYPE_STRING)

                || ((cellFormat.getStringCellValue().equals(
                        FORMAT_INDOOR_VALUE) == false)
                    && (cellFormat.getStringCellValue().equals(
                        FORMAT_OUTDOOR_VALUE) == false))) {
            throw new IncorrectFormatException(
                    "The championship information is in incorect format.");
        }

        /* the championship representation */
        final Championship championship = new Championship(-1,
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
    private Competition readCompetition(final HSSFSheet sheet)
            throws IncorrectFormatException {
        final HSSFCell cellDiscipline = workbook.getSheetAt(0
                ).getRow(DISCIPLINE_ROW_INDEX
                ).getCell(DISCIPLINE_CELL_INDEX);
        final HSSFCell cellRound = workbook.getSheetAt(0
                ).getRow(ROUND_ROW_INDEX
                ).getCell(ROUND_CELL_INDEX);
        final HSSFCell cellSex = workbook.getSheetAt(0
                ).getRow(SEX_ROW_INDEX
                ).getCell(SEX_CELL_INDEX);
        final HSSFCell cellStartTime = workbook.getSheetAt(0
                ).getRow(START_TIME_ROW_INDEX
                ).getCell(START_TIME_CELL_INDEX);
        final HSSFCell cellEndTime = workbook.getSheetAt(0
                ).getRow(END_TIME_ROW_INDEX
                ).getCell(END_TIME_CELL_INDEX);
        final HSSFCell cellTemperature = workbook.getSheetAt(0
                ).getRow(TEMPERATURE_ROW_INDEX
                ).getCell(TEMPERATURE_CELL_INDEX);
        final HSSFCell cellHumidity = workbook.getSheetAt(0
                ).getRow(HUMIDITY_ROW_INDEX
                ).getCell(HUMIDITY_CELL_INDEX);

        if ((cellDiscipline.getCellType() != HSSFCell.CELL_TYPE_STRING)
                || (cellRound.getCellType() != HSSFCell.CELL_TYPE_STRING)
                || (cellSex.getCellType() != HSSFCell.CELL_TYPE_STRING)
                || (cellStartTime.getCellType() != HSSFCell.CELL_TYPE_STRING)
                || (cellEndTime.getCellType() != HSSFCell.CELL_TYPE_STRING)
                || (cellTemperature.getCellType() != HSSFCell.CELL_TYPE_STRING)
                || (cellHumidity.getCellType() != HSSFCell.CELL_TYPE_STRING)

                || ((cellSex.getStringCellValue().equals(
                        SEX_MALE_VALUE) == false)
                    && (cellSex.getStringCellValue().equals(
                        SEX_FEMALE_VALUE) == false))) {
            throw new IncorrectFormatException(
                    "The championship " + sheet.getSheetName()
                    + " information is in incorect format.");
        }

        final Discipline discipline = Discipline.getDiscipline(
                cellDiscipline.getStringCellValue());
        final Round      round      = Round.getRound(
                cellRound.getStringCellValue());
        final boolean    sex        = cellSex.getStringCellValue().equals("Men");
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

        final Competition competition = new Competition(-1, discipline, round,
                sex, startTime, endTime, temperature, humidity);

        switch (discipline) {
            case R_100_METRES:
            case R_200_METRES:
            case R_300_METRES:
            case R_400_METRES:
            case R_600_METRES:
            case R_800_METRES:
            case R_1000_METRES:
            case R_1500_METRES:
            case R_MILE:
            case R_2000_METRES:
            case R_3000_METRES:
            case R_2_MILES:
            case R_5000_METRES:
            case R_10_000_METRES:
            case R_100_METRES_HURDLES:
            case R_110_METRES_HURDLES:
            case R_400_METRES_HURDLES:
            case R_2000_METRES_STEEPLECHASE:
            case R_3000_METRES_STEEPLECHASE:
                readRunning(sheet, competition);
                break;
            case HIGH_JUMP:
            case POLE_VAULT:
            case LONG_JUMP:
            case TRIPPLE_JUMP:
            case SHOT_PUT:
            case DISCUS_THROW:
            case HAMMER_THROW:
            case JAVELIAN_THROW:
            case R_10_KILOMETRES:
            case R_15_KILOMETRES:
            case R_10_MILES:
            case R_20_KILOMETRES:
            case HALF_MARATHON:
            case MARATHON:
            case W_3000_METRES_RACE_WALK:
            case W_5000_METRES_RACE_WALK:
            case W_5_KILOMETRES_RCAE_WALK:
            case W_10_000_METRES_RCAE_WALK:
            case W_10_KILOMETRES_RCAE_WALK:
            case W_20_000_METRES_RCAE_WALK:
            case W_20_KILOMETRES_RCAE_WALK:
            case W_35_KILOMETRES_RCAE_WALK:
            case W_50_KILOMETRES_RCAE_WALK:
            case HEPTATHLON:
            case DECATHLON:
            case R_4_100_METRES_RELAY:
            case R_4_400_METRES_RELAY:
                readRunning(sheet, competition);
                break;
            case UNDEFINED:
            default:
                throw new IncorrectFormatException("The discipline "
                    + cellDiscipline.getStringCellValue() + " is incorrect.");
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
    private void readRunning(final HSSFSheet sheet,
            final Competition competition)
            throws IncorrectFormatException {
        final DateFormat formatter = new SimpleDateFormat("dd.MM.yy");

        final HSSFCell cellWind = workbook.getSheetAt(0
                ).getRow(WIND_ROW_INDEX
                ).getCell(WIND_CELL_INDEX);
        final short wind = parseShort(cellWind.getStringCellValue());

        /* read entry data row by row */
        for (int rowIndex = COMPETITION_DATA_ROW;
                rowIndex <= sheet.getLastRowNum(); ++rowIndex) {
            final HSSFRow row = sheet.getRow(rowIndex);

            final HSSFCell cellRank     = row.getCell(0);
            final HSSFCell cellBib      = row.getCell(1);
            final HSSFCell cellName     = row.getCell(2);
            final HSSFCell cellBirthday = row.getCell(3);
            final HSSFCell cellCountry  = row.getCell(4);
            final HSSFCell cellPersonal = row.getCell(5);
            final HSSFCell cellSeason   = row.getCell(6);
            final HSSFCell cellLine     = row.getCell(7);
            final HSSFCell cellResult   = row.getCell(8);
            final HSSFCell cellReaction = row.getCell(9);

            /* no more data in the sheet */
            if (cellRank == null) {
                break;
            }

            try {
                final Athlete athlete = new Athlete(-1,
                        getFirstName(cellName.getStringCellValue()),
                        getLastName(cellName.getStringCellValue()),
                        formatter.parse(cellBirthday.getStringCellValue()).getTime(),
                        competition.getSex(), cellCountry.getStringCellValue());
                final Result result = new Result((short) -1, (short) -1,
                        (short) cellResult.getNumericCellValue(),
                        (short) cellReaction.getNumericCellValue(), wind);
                final Entry entry = new Entry(athlete,
                        (short) cellRank.getNumericCellValue(),
                        (short) cellBib.getNumericCellValue(),
                        (short) cellLine.getNumericCellValue(),
                        (int)   cellPersonal.getNumericCellValue(),
                        (int)   cellSeason.getNumericCellValue());
            } catch (Exception e) {
                throw new IncorrectFormatException(
                        "The athlete entry for competition "
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
}
