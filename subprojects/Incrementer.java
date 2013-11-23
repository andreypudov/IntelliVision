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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.text.DecimalFormatSymbols;
import java.text.DecimalFormat;

/**
 * Increments build number in specified XML properties file.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Incrementer.java
 * %date      08:10:A0 PM, Aug 27, 2013
 */
public class Incrementer {

    private static final String USAGE_STRING
        = "IntelliJustice Version Incrementer [0.00.00]\n"
        + "Copyright (C) 2011-2013 Andrey Pudov. All rights reserved.\n\n"
        + "Usage: Incrementer <filename>\n"
        + "    <filename>\tthe name of the XML properties file";

    private static final String VERSION_STRING = "<entry key=\"VERSION\">";

    /**
     * The application entry point.
     *
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        if (args.length != 1) {
            System.err.println(USAGE_STRING);
            System.exit(-1);
        }

        final BufferedReader reader;
        final BufferedWriter writer;
        final StringBuilder  builder;

        String line;

        try {
            reader  = new BufferedReader(new FileReader(new File(args[0])));
            builder = new StringBuilder(256);

            line = reader.readLine();
            while (line != null) {
                if (line.contains(VERSION_STRING)) {
                    /* current build version number */
                    String current = line.substring(line.indexOf(VERSION_STRING) 
                        + VERSION_STRING.length(), line.lastIndexOf('<'));

                    /* incremented value of version build number */
                    int number  = Integer.parseInt(current.replace(".", ""));
                    number = number + 1;

                    /* the value ready to be written into the file */
                    String incremented = String.format("%06d", number);
                    incremented = incremented;

                    /* insert zeros and dots to the final number */
                    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                    symbols.setGroupingSeparator('.');

                    DecimalFormat formatter = new DecimalFormat("00,00,00", symbols);
                    String        modified  = formatter.format(number);

                    /* the modified file content */
                    line = line.replace(current, modified);
                }

                builder.append(line);
                line = reader.readLine();
                if (line != null) {
                    builder.append('\n');
                }
            }

            reader.close();

            writer = new BufferedWriter(new FileWriter(args[0]));
            writer.write(builder.toString());
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
    }
}
