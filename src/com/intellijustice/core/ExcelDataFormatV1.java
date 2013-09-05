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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.apache.poi.hssf.usermodel.HSSFCell;
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
     * @param workbook the workbook to read.
     */
    public ExcelDataFormatV1(final HSSFWorkbook workbook) {
        this.workbook = workbook;
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
        LOG.info(championship.toString());

        for (int index = 0; index < workbook.getNumberOfSheets(); ++index) {
            final Competition competition
                    = readCompetition(workbook.getSheetAt(index));

            championship.addCompetition(competition);
            LOG.info(competition.toString());
        }

        //workbook.setForceFormulaRecalculation(true);
        //FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        //evaluator.evaluateAll();

//            Iterator<Row> rowIterator = sheet.iterator();
//            while (rowIterator.hasNext()) {
//                Row row = rowIterator.next();
//
//                Iterator<Cell> cellIterator = row.iterator();
//                while (cellIterator.hasNext()) {
//                    Cell cell = cellIterator.next();
//
//                    //LOG.info(cell.getStringCellValue());
//                    LOG.info(evaluator.evaluate(cell).getStringValue());
//                }
//            }

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

        return new Competition(-1, discipline, round, sex, startTime, endTime,
                temperature, humidity);
    }
}
