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

import java.net.Proxy
import java.net.HttpURLConnection
import java.net.InetSocketAddress
import java.net.URL
import java.net.URLEncoder

import java.text.ParseException

import com.ibm.icu.text.Transliterator

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

    var string = ""

    for (values <- CSVFile) {
      if (values.length == 10) {
        string = getAthleteFromService(translator.transliterate(values(1)), proxy)

        if (string.length() > 3) {
          val respond = string.substring(string.indexOf("[", 1) + 1, string.indexOf("]")).
            split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)")
          /*
           * "144950"            - the athlete identification number
           * "Tatyana MARKELOVA" - the full name
           * ""                  - N/A
           * "RUS"               - the olympic country code
           * "800m, 800m ind."   - the list of passed contests separated by comma
           * "60.00"             - N/A
           * "f"                 - the athlete sex, 't' for man, 'f' for woman
           */

          if (respond.length == 7) {
            println("Athlete to look:" + respond(0) + " " + respond(1))
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

    return compList.toList
  }

  @throws(classOf[NumberFormatException])
  def getAthleteFromService(name: String, proxyAddress: String): String = {
    val service = "http://www.all-athletics.com/nodereference/autocomplete/athletesearch/"

    if ((proxyAddress.contains(":") == false) || (proxyAddress.equals(""))) {
      return Source.fromURL(service + name).mkString
    }

    val proxy: Proxy = new Proxy(Proxy.Type.HTTP, 
      new InetSocketAddress(
        proxyAddress.substring(0, proxyAddress.indexOf(":")),
        Integer.parseInt(proxyAddress.substring(proxyAddress.indexOf(":") + 1))))
    val connection: HttpURLConnection = new URL(service + URLEncoder.encode(name, "UTF-8")).
      openConnection(proxy).asInstanceOf[HttpURLConnection]
    
    connection.setDoOutput(true)
    connection.setDoInput(true)
    //connection.setRequestProperty("Content-type", "text/xml")
    //connection.setRequestProperty("Accept", "text/xml, application/xml")
    //connection.setRequestMethod("POST")
    //connection.setRequestProperty("Content-Length", "0")

    connection.connect()

    return Source.fromInputStream(connection.getInputStream()).getLines().mkString
  }

  def translateContestName(russian: String): String = {
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
}
