/*
 * IntelliJustice Intelligent Referee Assistant System
 *
 * The MIT License
 *
 * Copyright 2011-2014 Andrey Pudov.
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

/**
 * Online Athletics database unit validation library.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Unit.java
 * %date      10:50:00 AM, Jul 20, 2014
 */
public class Unit {

    private static Database database;
    private static Cases    cases;

    public static void main(final String[] args) {
        System.out.println("Online Athletics Database Unit Validation Library\n"
                         + "Copyright (C) 2011-2014 Andrey Pudov\n");
        if (args.length != 2) {
            System.err.println("Usage: java -jar unit.jar <username> <password>\n"
                             + "\t<username>    the username to connect to database.\n"
                             + "\t<password>    the password to connect to database.");
            System.exit(Integer.MIN_VALUE);
        }

        database = new Database(args[0], args[1]);
        cases    = new Cases(database);
    }
}
