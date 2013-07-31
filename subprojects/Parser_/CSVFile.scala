/*
 * IntelliJustice Intelligent Referee Assistant System 
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
import java.io.InputStreamReader
import java.io.FileInputStream

import au.com.bytecode.opencsv.CSVReader

/**
 * Comma-separated values format file.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      CSVFile.scala
 * %date      07:50:00 PM, Jul 11, 2013
 */
class CSVFile(source: File,       charset: String = "UTF-8", separator: Char = ',', 
              quote:  Char = '"', escape:  Char = '\\') extends Traversable[Array[String]] {

  override 
  def foreach[U](f: Array[String] => U): Unit = {
    val csvReader = new CSVReader(
      new InputStreamReader(
        new FileInputStream(source), charset), separator, quote, escape)
    try {
      var next = true

      while (next) {
        val values = csvReader.readNext()
        if (values != null) f(values)
        else next = false
      }
    } finally {
      csvReader.close()
    }
  }
}
