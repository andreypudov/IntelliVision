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

import scala.io.Source
import scala.collection.mutable.ListBuffer
import scala.collection.immutable.Map

import java.io.File
import java.io.FileReader
import java.io.IOException
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

import java.net.Proxy
import java.net.HttpURLConnection
import java.net.InetSocketAddress
import java.net.URL
import java.net.URLEncoder

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat

import java.util.Date

import com.ibm.icu.text.Transliterator

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

/**
 * Reads comma-separated values format and produces competition list.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      ReaderCSV.scala
 * %date      07:00:00 PM, Jul 11, 2013
 */
object ReaderCSV {

  @throws(classOf[ParseException])
  @throws(classOf[IOException])
  def read(source: File, verbose: Boolean, proxy: String): List[Competition] = {
    val compList    = new ListBuffer[Competition]()
    val CSVFile     = new CSVFile(source, separator = ';')
    val translator  = Transliterator.getInstance("Russian-Latin/BGN")
    val search      = "http://www.all-athletics.com/nodereference/autocomplete/athletesearch/"

    val competition: Competition   = new Competition()
    var athlete:     Athlete       = null
    var respond:     Array[String] = null
    var string:      String        = ""

    /* temporarely only one competition */
    competition.name = "General"

    for (values <- CSVFile) {
      if (values.length == 10) {
        string = getContentFromService(search +
          URLEncoder.encode(translator.transliterate(values(1)), "UTF-8"), proxy)

        /*
         * CSV file entry
         * 
         * М                 - the sex
         * КАЦУРА ЮЛИЯ       - the full name
         * 28.05.1983        - the birthday date
         * МСМК              - the title
         * КРАСНОДАРСКИЙ КР. - the homeland region
         * МОСКОВСКАЯ ОБЛ.   - the second hameland region
         * Л                 - 
         * ЦСП ПО Л/А        - 
         * МАСЛАКОВ В.М., ГЛАДЫРЬ М.Ф., ФУНИКОВ В.В. - the list of trainer names
         * 100               - the competition name
         */

        /*
         * Respond from the service
         * 
         * "144950"            - the athlete identification number
         * "Tatyana MARKELOVA" - the full name
         * ""                  - N/A
         * "RUS"               - the olympic country code
         * "800m, 800m ind."   - the list of passed contests separated by comma
         * "60.00"             - N/A
         * "f"                 - the athlete sex, 't' for man, 'f' for woman
         */

        if (string.length() > 3) {
          /*var foundFlag: Boolean = false

          string.substring(2, string.length - 2).split("\\],\\[").foreach(entry => {
            if (foundFlag == false) {
              respond = entry.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)")

              athlete  = getAthlete(respond(0).substring(1, respond(0).length() - 1),
                respond(1).substring(1, respond(1).length() - 1), values(2),
                respond(3).substring(1, respond(3).length() - 1),
                translateCompetitionName(values(9)), proxy, verbose)

              if (values(2).equals(athlete.birthday)) {
                foundFlag = true
              } else {
                //println(values(2) + " " + athlete.birthday)
              }
            }
          })
          sys.exit*/

          val respond = string.substring(string.indexOf("[", 1) + 1, string.indexOf("]")).
            split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)")
          
          if (respond.length == 7) {
            if (verbose) printf("[ATHL]%s %s %s\n      %s %s %s [%s]\n",
              values(1), values(2), values(9),
              respond(0).substring(1, respond(0).length() - 1), 
              respond(1).substring(1, respond(1).length() - 1),
              respond(3).substring(1, respond(3).length() - 1),
              respond(4).substring(1, respond(4).length() - 1))

            val athlete = getAthlete(respond(0).substring(1, respond(0).length() - 1),
              respond(1).substring(1, respond(1).length() - 1), values(2),
              respond(3).substring(1, respond(3).length() - 1), 
              translateCompetitionName(values(9)), proxy, verbose)
            if (verbose) printf("      %s\n", athlete)
            
            //athlete.metadata = translateCompetitionName(values(9))
            competition.name = translateCompetitionName(values(9))
            competition.list += athlete
          } else {
            printf("Source data is corrupted: [%s;%d]\n", respond.deep.mkString(", "), respond.length)
          }
        } else {
          if (verbose) printf("[NOTF]%s\n", string)
        }
      } else {
        printf("Source data is corrupted: [%s;%d]\n", values.deep.mkString(", "), values.length)
      }
    }

    // temporarely only one competition
    compList += competition

    return compList.toList
  }

  private def getAthlete(athleteID: String, athleteName: String, 
                         athleteBirthday: String, athleteCountry: String, 
                         competition: String, proxyAddress: String, 
                         verbose: Boolean): Athlete = {
    import scala.collection.JavaConversions._

    val profile = "http://www.all-athletics.com/en-us/node/"
    val respond = getContentFromService(profile + athleteID, proxyAddress)

    val document: Document = Jsoup.parse(respond)

    /* personal best */
    val ptag  = document.select("td.f1-embedded-shortbio-personal-bests")
    val pkeys = ptag.select("td.f1-event-column").map(x => x.text).toList
    val pvals = ptag.select("td.f1-result-column").map(x => x.text).toList
    val pmap  = pkeys.zip(pvals)
    val pval  = pmap.filter(pair => pair._1 == competition)

    /* season best */
    val stag  = document.select("td.f1-embedded-shortbio-seasons-bests")
    val skeys = stag.select("td.f1-event-column").map(x => x.text).toList
    val svals = stag.select("td.f1-result-column").map(x => x.text).toList
    val smap  = skeys.zip(svals)
    val sval  = smap.filter(pair => pair._1 == competition)

    /* birthday date */
    val bval  = document.select("td.f1-ap-athlete-birthdate").select("strong").text

    val formatService: DateFormat = new SimpleDateFormat("dd.mm.yyyy")
    val formatCSV:     DateFormat = new SimpleDateFormat("dd.mm.yyyy")
    var birthday:      String     = null

    try {
      birthday = formatCSV.format(formatService.parse(bval))
    } catch {
      case e: ParseException => {
        birthday = athleteBirthday
      }
    }

    return new Athlete(athleteName, "", birthday, athleteCountry, 
      (if (pval.length > 0) pval(0)._2 else ""), 
      (if (sval.length > 0) sval(0)._2 else ""))
  }

  @throws(classOf[NumberFormatException])
  private def getContentFromService(service: String, proxyAddress: String): String = {
    //if ((proxyAddress.contains(":") == false) || (proxyAddress.equals(""))) {
      //return Source.fromURL(service).mkString
    //}

    /*val proxy: Proxy = new Proxy(Proxy.Type.HTTP, 
      new InetSocketAddress(
        proxyAddress.substring(0, proxyAddress.indexOf(":")),
        Integer.parseInt(proxyAddress.substring(proxyAddress.indexOf(":") + 1))))
    val connection: HttpURLConnection = new URL(service).
      openConnection(proxy).asInstanceOf[HttpURLConnection]*/

    var connection: HttpURLConnection = null
    if ((proxyAddress.contains(":") == false) || (proxyAddress.equals(""))) {
      connection = new URL(service).openConnection().asInstanceOf[HttpURLConnection]
    } else {
      val proxy: Proxy = new Proxy(Proxy.Type.HTTP, 
        new InetSocketAddress(
          proxyAddress.substring(0, proxyAddress.indexOf(":")),
          Integer.parseInt(proxyAddress.substring(proxyAddress.indexOf(":") + 1))))
      connection = new URL(service).openConnection(proxy).asInstanceOf[HttpURLConnection]
    }
    
    connection.setDoOutput(true)
    connection.setDoInput(true)
    //connection.setRequestProperty("Content-type", "text/xml")
    //connection.setRequestProperty("Accept", "text/xml, application/xml")
    //connection.setRequestMethod("POST")
    //connection.setRequestProperty("Content-Length", "0")

    connection.connect()
    var respond: String = getStringFromInputStream(connection.getInputStream())
    //Source.fromInputStream(someStream).getLines.mkString
    connection.disconnect()

    return respond
  }

  private def translateCompetitionName(russian: String): String = {
    /* Hurdles - Барьер, Steeplechase (SC) - Препятствие   */
    val contests = Map[String, String]("100" -> "100m",        "200"     -> "200m", 
      "400"     -> "400m",         "800"    -> "800m",         "1500"    -> "1500m", 
      "3000"    -> "3000m",        "5000"   -> "5000m",        "10000"   -> "10,000m",
      "4х100"   -> "4x100m",       "4х200"  -> "4x200m",       "4х400"   -> "4x400m", 
      "4х800"   -> "4x800m",       "100СБ"  -> "100mH",        "110СБ"   -> "110mH", 
      "400СБ"   -> "400mH",        "2000СП" -> "2000mSC",      "3000СП"  -> "3000mSC", 
      "3000СХ"  -> "3000m Walk",   "5000СХ" -> "5000m Walk",   "10000СХ" -> "10,000m Walk", 
      "ВЫСОТА"  -> "High Jump",    "ШЕСТ"   -> "Pole Vault",   "ДЛИНА"   -> "Long Jump",
      "ТРОЙНОЙ" -> "Triple Jump",  "ДИСК"   -> "Discus Throw", "КОПЬЕ"   -> "Javelin Throw", 
      "МОЛОТ"   -> "Hammer Throw", "ЯДРО"   -> "Shot Put",     "7БОРЬЕ"  -> "Heptathlon",
      "10БОРЬЕ" -> "Decathlon")

    if (contests.contains(russian)) {
      return contests(russian)
    } else {
      return ""
    }
  }

  private def getStringFromInputStream(stream: InputStream): String = {
    var reader:  BufferedReader = null
    val builder: StringBuilder  = new StringBuilder()
    var line   : String         = ""

    try {
      reader = new BufferedReader(new InputStreamReader(stream))
      line = reader.readLine()
	  while (line != null) {
	    builder.append(line)
        line = reader.readLine()
	  }
    } catch {
      case e: IOException => {
        e.printStackTrace()
      }
    } finally {
      if (reader != null) {
	    try {
		  reader.close();
		} catch {
          case e: IOException => {
		    e.printStackTrace();
	      }
        }
	  }
    }

    return builder.toString()
  }
}
