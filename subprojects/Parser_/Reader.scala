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

import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfReaderContentParser
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy

import scala.collection.mutable.ListBuffer

import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.StringReader

import java.text.ParseException

/**
 * Reads portable document format and produces competition list. 
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Reader.scala
 * %date      10:30:00 PM, Jul 07, 2013
 */
object Reader {

  @throws(classOf[ParseException])
  @throws(classOf[IOException])
  def read(source: File, verbose: Boolean): List[Competition] = {
    val preader = new PdfReader(source.getAbsolutePath())
    val parser  = new PdfReaderContentParser(preader)
    val list    = new ListBuffer[Competition]()

    var competition: Competition = null
    var athlete:     Athlete     = null

    /* parsing helpers */
    var nextIsBirthday = false
    var athleteIsMan   = false
    var athleteName    = ""

    for (pageNumber <- 1 to preader.getNumberOfPages()) {
      val strategy = parser.processContent(pageNumber, new SimpleTextExtractionStrategy())
      val reader = new BufferedReader(new StringReader(strategy.getResultantText()))

      strategy.getResultantText().lines.foreach(line => line match {
        case line if (line.startsWith("Men's") || line.startsWith("Women's"))  => {
          athleteIsMan = line match {
            case entry if entry.startsWith("Men's") => true
            case _ => false
          }

          if (verbose) println("[COMP]" + line)}
        case line if line.startsWith("Start list") => {
          if (competition != null) list += competition
          competition = new Competition()

          competition.name = athleteIsMan match {
            case true  => "Men's "   + line.substring("Start list ".length(), 
              line.indexOf(' ', "Start list ".length() + 1))
            case false => "Women's " + line.substring("Start list ".length(), 
              line.indexOf(' ', "Start list ".length() + 1))}

          if (verbose) println("[STRT]" + line)}
        case line if line.matches("\\w{1,3}\\s\\w{1,3}" +
            "([\\s|-]\\p{javaUpperCase}{1}\\p{javaLowerCase}*)+" +
            "([\\s|-]\\p{javaUpperCase}+)+\\s[A-Z]{3}" +
            "(\\s[\\w\\.\\:]+)*(\\s[\\w\\.\\:]+)*") => {
          val array  = line.split(" ");
          var column = 0

          column = array.indexWhere(entry => isCountry(entry))
          if (column > 0) {
            var firstName = ""
            var lastName  = ""

            (2 to column - 1).foreach(index => {
              if (isFirstName(array(index))) firstName += array(index) + " "
              if (isLastName(array(index)))  lastName  += array(index) + " "})

            athlete = new Athlete(firstName.trim(), lastName.trim(), "", array(column),
              if (array.length > (column + 1)) array(column + 1) else "",
              if (array.length > (column + 2)) array(column + 2) else "")
            competition.list += athlete

            if (verbose) printf("[SENT]%s [%s]\n", line, athlete)
          } else {
            if (verbose) println("######" + line)}}
        case line if line.equals("Records") => {
          if (verbose) println("[RCRD]" + line)}
        case line if line.matches("\\w{2,3}\\s[\\w\\.\\:]+" +
            "([\\s|-]\\p{javaUpperCase}{1}\\p{javaLowerCase}*)+" +
            "([\\s|-]\\p{javaUpperCase}+)+\\s[A-Z]{3}" +
            "(([\\s|-]\\p{javaUpperCase}{1}\\p{javaLowerCase}*)+)*" +
            "\\s[\\d\\.]+") => {

          line match {
            case record if record.startsWith("WR") => 
              competition.world = record.substring("WR ".length(),
                record.indexOf(' ', "WR ".length() + 1))
            case record if record.startsWith("AR") => 
              competition.area = record.substring("AR ".length(),
                record.indexOf(' ', "AR ".length() + 1))
            case record if record.startsWith("NR") => 
              competition.national = record.substring("NR ".length(),
                record.indexOf(' ', "NR ".length() + 1))
            case record if record.startsWith("WJR") => 
              competition.junior = record.substring("WJR ".length(),
                record.indexOf(' ', "WJR ".length() + 1))
            case record if record.startsWith("WJB") => 
              competition.juniorBest = record.substring("WJB ".length(),
                record.indexOf(' ', "WJB ".length() + 1))
            case record if record.startsWith("MR") => 
              competition.mediterranean = record.substring("MR ".length(),
                record.indexOf(' ', "MR ".length() + 1))
            case record if record.startsWith("SB") => 
              competition.season = record.substring("SB ".length(),
                record.indexOf(' ', "SB ".length() + 1))
            case record => if (verbose) printf("[####]%s\n", record)
          }

          if (verbose) printf("[RENT]%s [%s]\n", line, competition.name)}
        case line if line.matches("(\\p{javaUpperCase}{1}\\p{javaLowerCase}*)+" +
            "([\\s|-]\\p{javaUpperCase}{1}\\p{javaLowerCase}*)*" +
            "([\\s|-]\\p{javaUpperCase}+)+") => {
          nextIsBirthday = true
          athleteName    = line
          if (verbose) printf("[BRT>]%s [%s]\n", line, competition.name)}
        case line if nextIsBirthday => {
          (line.matches("[A-Z]{3}\\s(\\d{2}.\\d{2}.\\d{2}){1}") == true)  match {
            case true =>  {
              competition.getAthleteByName(athleteName) match {
                case None => if (verbose) println("######" + line)
                case Some(athlete) => {
                  athlete.birthday = line.substring(line.indexOf(' ') + 1)
                  if (verbose) printf("[BRT<]%s [%s]\n", line, athlete)}
                
              }}
            case false => if (verbose) println("      " + line)
          }

          nextIsBirthday = false}
        case line => if (verbose) println("      " + line)
      })
    }

    return list.toList
  }

  private def isCountry(string: String): Boolean = {
    val list = Array("AFG",  "ALB",  "ALG",  "ASA",  "AND",
      "ANG",  "ANT",  "ARG",  "ARM",  "ARU",  "AUS",  "AUT",  "AZE",
      "BAH",  "BRN",  "BAN",  "BAR",  "BLR",  "BEL",  "BIZ",  "BER",  
      "BEN",  "BHU",  "BOL",  "BIH",  "BOT",  "BRA",  "IVB",  "BRU",  
      "BUL",  "BUR",  "BDI",  "CAM",  "CMR",  "CAN",  "CPV",  "CAY",  
      "CAF",  "CHA",  "CHI",  "CHN",  "COL",  "COM",  "CGO",  "COD",  
      "COK",  "CRC",  "CIV",  "CRO",  "CUB",  "CYP",  "CZE",  "DEN",  
      "DJI",  "DMA",  "DOM",  "TLS",  "ECU",  "EGY",  "ESA",  "GEQ",  
      "ERI",  "EST",  "ETH",  "FIJ",  "FIN",  "FRA",  "GAB",  "GAM",  
      "GEO",  "GER",  "GHA",  "GRE",  "GRN",  "GUM",  "GUA",  "GUI", 
      "GBS",  "GUY",  "HAI",  "HON",  "HKG",  "HUN",  "ISL",  "IND",  
      "INA",  "IRI",  "IRQ",  "IRL",  "ISR",  "ITA",  "JAM",  "JPN",  
      "JOR",  "KAZ",  "KEN",  "KIR",  "PRK",  "KOR",  "KUW",  "KGZ",  
      "LAO",  "LAT",  "LIB",  "LES",  "LBR",  "LBA",  "LIE",  "LTU",  
      "LUX",  "MKD",  "MAD",  "MAW",  "MAS",  "MDV",  "MLI",  "MLT",  
      "MHL",  "MTN",  "MRI",  "MEX",  "FSM",  "MDA",  "MON",  "MGL",  
      "MNE",  "MAR",  "MOZ",  "MYA",  "NAM",  "NRU",  "NEP",  "NED",  
      "NZL",  "NCA",  "NIG",  "NGR",  "NOR",  "OMA",  "PAK",  "PLW",  
      "PLE",  "PAN",  "PNG",  "PAR",  "PER",  "PHI",  "POL",  "POR",  
      "PUR",  "QAT",  "ROU",  "RUS",  "RWA",  "SKN",  "LCA",  "VIN",  
      "SAM",  "SMR",  "STP",  "KSA",  "SEN",  "SRB",  "SEY",  "SLE",  
      "SIN",  "SVK",  "SLO",  "SOL",  "SOM",  "RSA",  "ESP",  "SRI",  
      "SUD",  "SUR",  "SWZ",  "SWE",  "SUI",  "SYR",  "TPE",  "TJK",  
      "TAN",  "THA",  "TOG",  "TGA",  "TRI",  "TUN",  "TUR",  "TKM",  
      "TUV",  "UGA",  "UKR",  "UAE",  "GBR",  "USA",  "URU",  "UZB",  
      "VAN",  "VEN",  "VIE",  "ISV",  "YEM",  "ZAM",  "ZIM")

    list.exists(country => country == string)
  }

  private def isFirstName(string: String): Boolean = {
    var isFirst = true

    string.toCharArray().foreach(character => character match {
      case value if ((isFirst == true)  && Character.isUpperCase(value) == false) => return false
      case value if ((isFirst == false) && Character.isUpperCase(value) == true)  => return false
      case value if (Character.isLetter(value) == false) => isFirst = true
      case _ => isFirst = false
    })

    return true
  }

  private def isLastName(string: String): Boolean = {
    !(string.toCharArray().exists(character => Character.isLowerCase(character)))
  }
}
