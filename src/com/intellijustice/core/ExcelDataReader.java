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
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Reads specified Excel workbook and returns the championship representation.
 * Reader validates Excel workbook file and determines supported version.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      ExcelDataReader.java
 * %date      06:40:00 PM, Sep 03, 2013
 */
public class ExcelDataReader {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellijustice.core.Manifest.NAME);

    private static final int METADATA_COLUMN_NUMBER = 19;

    /* the Excel worksheets */
    private final File worksheet;

    public ExcelDataReader(final File worksheet) {
        this.worksheet = worksheet;
    }

    /**
     * Reads championship values including all competitions from the Excel
     * worksheet specified in data provider.
     *
     * @return the championship representation.
     * @throws IOException
     *         the source of an exception.
     * @throws IncorrectFormatException
     *         the source of an exception.
     */
    public Championship readChampionship()
            throws IOException, IncorrectFormatException {
        final Workbook workbook
                = new HSSFWorkbook(new FileInputStream(worksheet));
        final FormulaEvaluator evaluator
                = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);

        final List<String>           workbooks  = new ArrayList<>(16);
        final List<FormulaEvaluator> evaluators = new ArrayList<>(16);

        workbooks.add(worksheet.getName());
        evaluators.add(evaluator);

        for (File book : worksheet.getParentFile().listFiles(
                new FileFilter() {
                    @Override
                    public boolean accept(final File file) {
                        return file.getName().toLowerCase(
                                ).endsWith(".xls")
                                /* skip worksheet itself */
                                && (file.equals(worksheet) == false);
                }})) {
            /* add evaluator and workbook file name to the list */
            evaluators.add(new HSSFFormulaEvaluator(
                    new HSSFWorkbook(new FileInputStream(book))));
            workbooks.add(book.getName());
        }

        /* set up workbook environment */
        HSSFFormulaEvaluator.setupEnvironment(
                workbooks.toArray(new String[] {}),
                evaluators.toArray(new HSSFFormulaEvaluator[] {}));

        /* evaluates all values in workbook */
        evaluator.evaluateAll();

        /* validate workbook and construct data reader */
        final ExcelDataFormatDefault reader = validate(workbook);

        return reader.readChampionship();
    }

    /**
     * Validates specified Excel workbook and returns used format version.
     *
     * @param workbook the Excel workbook to validate.
     * @return         the data format reader.
     * @throws IncorrectFormatException
     *                 the source of an exception.
     */
    private ExcelDataFormatDefault validate(final Workbook workbook)
            throws IncorrectFormatException {
        /* workbook doesn't contains data */
        if (workbook.getNumberOfSheets() == 0) {
            throw new IncorrectFormatException(
                    "Excel workbook doesn't contains the championship data.");
        }

        final Cell cell = workbook.getSheetAt(0
                ).getRow(0).getCell(METADATA_COLUMN_NUMBER);
        if (cell.getCellType() != Cell.CELL_TYPE_NUMERIC) {
            throw new IncorrectFormatException(
                "Excel workbook doesn't contains version information.");
        }

        final ExcelDataFormatList version = ExcelDataFormatList.getFormat(
                (int) cell.getNumericCellValue());
        switch (version) {
            case VERSION_1:
                return new ExcelDataFormatV1(worksheet, workbook);
            case UNSUPPORTED:
                /* fall through */
            default:
                throw new IncorrectFormatException(
                    "Excel workbook data is in unsupported format version.");
        }
    }
}
