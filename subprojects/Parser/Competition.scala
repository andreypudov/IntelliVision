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

import scala.collection.mutable.ListBuffer

/**
 * A competition representation contains name, records and the athletes list.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Competition.scala
 * %date      10:30:00 PM, Jul 07, 2013
 */
class Competition {
  val list = new ListBuffer[Athlete]()

  var name :         String = ""
  var world:         String = ""
  var area:          String = ""
  var national:      String = ""
  var junior:        String = ""
  var juniorBest:    String = ""
  var mediterranean: String = ""
  var season:        String = ""

  def getAthleteByName(name: String): Option[Athlete] = {
    list.find(athlete => (athlete.firstName + " " + athlete.lastName) == name)
  }

  override
  def toString(): String =
    "(" + world      + " " +    area          + " " +
          national   + " " +    junior        + " " + 
          juniorBest + " " +    mediterranean + " " +
          season     + ")"
}
