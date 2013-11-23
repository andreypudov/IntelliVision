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
import java.io.IOException

import java.text.ParseException

/**
 * Produces a Excel worksheet with a table of athlets listed in given PDF file.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Parser.scala
 * %date      10:30:00 PM, Jul 07, 2013
 */
object Parser {
  def main(args: Array[String]) {
    var source:  File    = null
    var report:  File    = null
    var method:  String  = ""
    var proxy:   String  = ""
    var verbose: Boolean = false

    val usage: String = "Usage: parser --source=filename --report=filename\n" +
      "              [--method=algorithm] [--verbose] [--proxy=server:port]\n" +
      "\t--source\tthe PDF file to read\n" +
      "\t--report\tthe Excel file to write\n" +
      "\t--method\tthe algorithm to parse [all, rus]\n" +
      "\t--verbose\tprint debug informatino during parsing\n" +
      "\t--help\t\tprint this message and exit\n" +
      "\t--version\tprint version information and exit"

    println("IntelliJustice Intelligent Referee Assistant System ")
    println("Copyright (C) 2013. Andrey Pudov. All rights reserved.\n")

    if (args.length == 0) {
      println(usage)
      sys.exit(Byte.MaxValue)
    }
    args.foreach(arg =>
      arg match {
        case entry if entry.startsWith("--source=")  => {
          source = new File(arg.substring("--source=".length()))

          if ((source.exists() == false) || (source.canRead() == false)) {
            printf("Unable to open given source file %s\n",
                   source.getAbsolutePath())
            sys.exit(Byte.MaxValue)
          }}
        case entry if entry.startsWith("--report=")  => {
          report = new File(arg.substring("--report=".length()));

          try {
            if ((report.exists() == false) && (report.createNewFile() == false)) {
              printf("Unable to create report file %s\n", report.getAbsolutePath())
              sys.exit(Byte.MaxValue)
            }
          } catch {
            case e: IOException => {
              printf("Error during parsing %s\n", e.getMessage())
              sys.exit(Byte.MaxValue)
            }}

          if ((report.exists() == true) && (report.canWrite() == false)) {
            printf("Unable to open given report file %s\n", report.getAbsolutePath())
            sys.exit(Byte.MaxValue)
          }}
        case entry if entry.startsWith("--method=") => 
          method = arg.substring("--method=".length())
        case "--verbose"  => verbose = true
        case entry if entry.startsWith("--proxy=") => 
          proxy = arg.substring("--proxy=".length())
        case "--help"     => {
          println(usage) 
          sys.exit(Byte.MaxValue)}
        case "--version"  => {
          println("Version: 0.00.00 Alpha")
          sys.exit(Byte.MaxValue)}
        case line => {
          println("Unknown option " + line)
          println(usage)
          sys.exit(Byte.MaxValue)}
      })

    if ((source == null) || (report == null)) {
      printf("Invalid arguments: source file %s, report file %s\n\n",
        source match {case null => "not specified"; case _ => source},
        report match {case null => "not specified"; case _ => report})
      println(usage)
      sys.exit(Byte.MaxValue)
    }

    try {
      method match {
        case "rus" => Writer.write(report, ReaderCSV.read(source, verbose, proxy), verbose)
        case _     => Writer.write(report, Reader.read(source, verbose), verbose)
      }
    } catch {
      case e: ParseException => {
        printf("A parsing error occured during processing source file: %s\n", e.getMessage())
        sys.exit(Byte.MaxValue)
      }
      case e: IOException => {
        printf("An Input/Output error occured during processing source file: %s\n", e.getMessage())
        sys.exit(Byte.MaxValue)
      }
    }
  }
}
