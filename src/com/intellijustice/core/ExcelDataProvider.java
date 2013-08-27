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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * The Excel data provider adds support to use Excel worksheet as a source data
 * for retrieving championship information.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      ExcelDataProvider.java
 * %date      05:00:00 PM, Aug 14, 2013
 */
public class ExcelDataProvider implements DefaultDataProvider {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellijustice.core.Manifest.NAME);

    /* the Excel worksheets */
    private final File worksheet;

    /**
     * Constructs new Excel data provider.
     *
     * @param worksheet the Excel worksheet file.
     */
    public ExcelDataProvider(final File worksheet) {
        this.worksheet = worksheet;

        update();
    }

    /**
     * Returns current championship representation.
     *
     * @return the championship representation.
     */
    @Override
    public Championship getChampionship() {
        return new Championship(-1, "Name", "City", ChampionshipFormat.OUTDOOR);
    }

    /**
     * Updates championship representation.
     */
    private void update() {
        try {
            final HSSFWorkbook workbook
                    = new HSSFWorkbook(new FileInputStream(worksheet));
            final HSSFFormulaEvaluator evaluator
                    = new HSSFFormulaEvaluator(workbook);

            final List<String>               workbooks  = new ArrayList<>(16);
            final List<HSSFFormulaEvaluator> evaluators = new ArrayList<>(16);

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
                    workbooks.toArray(new String[]{}),
                    evaluators.toArray(new HSSFFormulaEvaluator[] {}));

            /* evaluates all values in workbook */
            evaluator.evaluateAll();

            HSSFCell cell = workbook.getSheetAt(0).getRow(0).getCell(0);
            LOG.info(cell.getStringCellValue());

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
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Could not update championship data. {0}",
                    e.getMessage());
        }
    }
}
