/*
 * IntelliJustice Intelligence Referee Assistant System
 *
 * The MIT License
 *
 * Copyright 2013 Andrey Pudov.
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

import java.io.File
import java.io.IOException
import java.io.FileInputStream
import java.io.FileOutputStream

import org.apache.poi.ss.usermodel.Header
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.Font
import org.apache.poi.ss.usermodel.Header
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.util.IOUtils

/**
 * Writes competition list to Excel workbook. 
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Writer.scala
 * %date      10:30:00 PM, Jul 07, 2013
 */
object Writer {

  @throws(classOf[IOException])
  def write(report: File, list: List[Competition], verbose: Boolean) {
    val workbook: Workbook = new XSSFWorkbook()

    var sheet: Sheet = null
    var cell:  Cell  = null;
    var row:   Row   = null;

    val styleHeader = getStyle(workbook, Font.BOLDWEIGHT_BOLD, 16.toShort, 
      IndexedColors.WHITE.getIndex(), CellStyle.ALIGN_LEFT,
      CellStyle.VERTICAL_CENTER, CellStyle.BORDER_NONE,
      IndexedColors.AUTOMATIC.getIndex(), IndexedColors.GREY_80_PERCENT.getIndex())
    val styleRecord = getStyle(workbook, Font.BOLDWEIGHT_NORMAL, 11.toShort, 
      IndexedColors.WHITE.getIndex(), CellStyle.ALIGN_LEFT, 
      CellStyle.VERTICAL_BOTTOM, CellStyle.BORDER_NONE, 
      IndexedColors.AUTOMATIC.getIndex(), IndexedColors.GREY_80_PERCENT.getIndex())
    val styleTitle  = getStyle(workbook, Font.BOLDWEIGHT_BOLD, 11.toShort, 
      IndexedColors.AUTOMATIC.getIndex(), CellStyle.ALIGN_LEFT, 
      CellStyle.VERTICAL_CENTER, CellStyle.BORDER_MEDIUM,
      IndexedColors.BLACK.getIndex(), IndexedColors.GREY_25_PERCENT.getIndex())
    val styleMiddle = getStyle(workbook, Font.BOLDWEIGHT_BOLD, 11.toShort, 
      IndexedColors.AUTOMATIC.getIndex(), CellStyle.ALIGN_CENTER, 
      CellStyle.VERTICAL_CENTER, CellStyle.BORDER_MEDIUM,
      IndexedColors.BLACK.getIndex(), IndexedColors.GREY_25_PERCENT.getIndex())
    val styleName   = getStyle(workbook, Font.BOLDWEIGHT_BOLD, 11.toShort, 
      IndexedColors.AUTOMATIC.getIndex(), CellStyle.ALIGN_LEFT, 
      CellStyle.VERTICAL_TOP, CellStyle.BORDER_THIN,
      IndexedColors.AUTOMATIC.getIndex(), IndexedColors.AUTOMATIC.getIndex())
    val styleValue  = getStyle(workbook, Font.BOLDWEIGHT_NORMAL, 11.toShort, 
      IndexedColors.AUTOMATIC.getIndex(), CellStyle.ALIGN_CENTER, 
      CellStyle.VERTICAL_TOP, CellStyle.BORDER_THIN,
      IndexedColors.AUTOMATIC.getIndex(), IndexedColors.AUTOMATIC.getIndex())

    list.foreach(competition => {
      var rowIndex = 0

      workbook.getSheet(competition.name) match {
        case null  => sheet = workbook.createSheet(competition.name)
        case sheet => if (verbose) printf("[WRIT] Sheet is already exist and will be overwritten %s\n")
      }

      sheet.setDisplayGridlines(false)
      sheet.setColumnWidth(0, 10 * 100)
      sheet.setColumnWidth(1, 70 * 100)
      sheet.setColumnWidth(2, 30 * 100)
      sheet.setColumnWidth(3, 15 * 100)
      sheet.setColumnWidth(4, 15 * 100)
      sheet.setColumnWidth(5, 30 * 100)
      sheet.setColumnWidth(6, 30 * 100)

      sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 1, 6))
      row  = sheet.createRow(rowIndex)
      cell = row.createCell(1)
      row.setHeight(580.toShort)
      cell.setCellValue(competition.name)
      cell.setCellStyle(styleHeader)

      rowIndex = rowIndex + 1
      sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 1, 6))
      row  = sheet.createRow(rowIndex)
      cell = row.createCell(1)
      cell.setCellStyle(styleRecord)
      cell.setCellValue(
        (if (competition.world.length() > 0)         "WR: "  + competition.world        + "  " else "") +
        (if (competition.area.length() > 0)          "AR: "  + competition.area         + "  " else "") +
        (if (competition.national.length() > 0)      "NR: "  + competition.national     + "  " else "") +
        (if (competition.junior.length() > 0)        "WJR: " + competition.junior       + "  " else "") +
        (if (competition.juniorBest.length() > 0)    "WJB: " + competition.juniorBest   + "  " else "") +
        (if (competition.mediterranean.length() > 0) "MR: " + competition.mediterranean + "  " else "") +
        (if (competition.season.length() > 0)        "SB: " + competition.season        + "  " else ""))

      rowIndex = rowIndex + 1
      row  = sheet.createRow(rowIndex)
      cell = row.createCell(1)
      row.setHeight(580.toShort)
      cell.setCellValue("Athlete")
      cell.setCellStyle(styleTitle)

      cell = row.createCell(2)
      cell.setCellValue("Birthday")
      cell.setCellStyle(styleMiddle)

      sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 3, 4))
      cell = row.createCell(3)
      cell.setCellValue("Country")
      cell.setCellStyle(styleMiddle)

      cell = row.createCell(4)
      cell.setCellStyle(styleMiddle)

      cell = row.createCell(5)
      cell.setCellValue("PB")
      cell.setCellStyle(styleMiddle)

      cell = row.createCell(6)
      cell.setCellValue("SB")
      cell.setCellStyle(styleMiddle)

      competition.list.foreach(athlete => {
        rowIndex = rowIndex + 1
        row = sheet.createRow(rowIndex)
        row.setHeight(580.toShort)

        cell = row.createCell(1);
        cell.setCellValue(athlete.lastName + " " + athlete.firstName)
        cell.setCellStyle(styleName)

        cell = row.createCell(2)
        cell.setCellValue(athlete.birthday)
        cell.setCellStyle(styleValue)

        cell = row.createCell(3)
        cell.setCellValue(athlete.country)
        cell.setCellStyle(styleValue)

        cell = row.createCell(4)
        addImage(athlete.country, rowIndex.toShort, 4.toShort, workbook, sheet)
        cell.setCellStyle(styleValue)

        cell = row.createCell(5)
        cell.setCellValue(athlete.personal)
        cell.setCellStyle(styleValue)

        cell = row.createCell(6)
        cell.setCellValue(athlete.season)
        cell.setCellStyle(styleValue)})})

    workbook.write(new FileOutputStream(report))
  }

  private def addImage(country: String, row: Short, column: Short, workbook: Workbook, sheet: Sheet) {
    val stream = getClass.getResourceAsStream("/flags/" + country + ".png")
    if (stream == null) return

    val data    = IOUtils.toByteArray(stream)
    val index   = workbook.addPicture(data, Workbook.PICTURE_TYPE_PNG)
    val helper  = workbook.getCreationHelper()
    val drawing = sheet.createDrawingPatriarch()
    val anchor  = helper.createClientAnchor()

    anchor.setAnchorType(ClientAnchor.MOVE_AND_RESIZE)
    anchor.setCol1(column)
    anchor.setRow1(row)

    val picture = drawing.createPicture(anchor, index)
    picture.resize()

    stream.close()
  }

  private def getStyle(workbook:     Workbook, fontWeight:  Short, fontHeight:      Short,
                       fontColor:    Short,    alignment:   Short, valignment:      Short, 
                       borderBottom: Short,    borderColor: Short, backgroundColor: Short): CellStyle = {
    val cellStyle: CellStyle = workbook.createCellStyle()
    val font:      Font      = workbook.createFont()

    font.setBoldweight(fontWeight)               /* BOLDWEIGHT_NORMAL, BOLDWEIGHT_BOLD */
    font.setFontHeightInPoints(fontHeight)
    font.setColor(fontColor)

    cellStyle.setFont(font)
    cellStyle.setAlignment(alignment)                /* ALIGN_GENERAL, ALIGN_LEFT, ALIGN_CENTER, 
                                                        ALIGN_RIGHT,   ALIGN_FILL, ALIGN_JUSTIFY,
                                                        ALIGN_CENTER_SELECTION */
    cellStyle.setVerticalAlignment(valignment)       /* VERTICAL_TOP,  VERTICAL_CENTER, VERTICAL_BOTTOM, VERTICAL_JUSTIFY */
    cellStyle.setBorderBottom(borderBottom)          /* BORDER_NONE,   BORDER_THIN,  BORDER_MEDIUM,   BORDER_DASHED, 
                                                        BORDER_DOTTED, BORDER_THICK, BORDER_DOUBLE,   BORDER_HAIR, 
                                                        BORDER_MEDIUM_DASHED,        BORDER_DASH_DOT, BORDER_MEDIUM_DASH_DOT, 
                                                        BORDER_DASH_DOT_DOT,         BORDER_MEDIUM_DASH_DOT_DOT, 
                                                        BORDER_SLANTED_DASH_DOT */
    cellStyle.setBottomBorderColor(borderColor)

    if (backgroundColor != IndexedColors.AUTOMATIC.getIndex()) {
      cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND)
      cellStyle.setFillForegroundColor(backgroundColor)
    }

    return cellStyle
  }
}
