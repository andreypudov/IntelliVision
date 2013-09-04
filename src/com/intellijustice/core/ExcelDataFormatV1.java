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

import org.apache.poi.hssf.usermodel.HSSFCell;
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

    /* the Excel workbook instance */
    private final HSSFWorkbook workbook;

    /**
     * Constructs Excel data reader with an support for format version 1.0 for
     * specified Excel workbook.
     *
     * @param workbook the workbook to read;
     */
    public ExcelDataFormatV1(final HSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    @Override
    public Championship readChampionship() {
        /* start parsing the workbook content */
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
        
        return new Championship(-1, "Name", "Country", "City", Format.OUTDOOR);
    }
}
