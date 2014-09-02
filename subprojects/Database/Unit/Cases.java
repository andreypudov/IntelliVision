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

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * The list of unit validation cases.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Cases.java
 * %date      09:00:00 AM, Jul 22, 2014
 */
public class Cases {

    private Database database;

    public Cases(final Database database) {
        this.database = database;

        //u_add_athlete();
        //u_remove_region_from_athlete();
        u_geo_country_list();
        u_geo_region_list();
        u_geo_city_list();
        u_geo_get_city_complete_name_by_id();
        u_geo_get_city_id_by_complete_name();
    }

    private void u_add_athlete() {
        database.clear();

        final ResultSet set = database.call("add_athlete", "%s %s %s %s %s %s %t %l %i %i %s",
            "Pudov", "Andrey", "Semenovich", "Пудов", "Андрей", "Семёнович",
            "1989-03-17", 518976L, 1, 133, "apudov");
        //if (validate(set, "[1]") == false) {
        //    System.out.println("Method: add_athlete(1) FAILED");
        //    return false;
        //}

        System.out.println("Method: add_athlete() SUCCESS");
    }

    private void u_remove_region_from_athlete() {
        final String[][] sdata = {
            {"Pudov", "Andrey", "Semenovich", "Пудов", "Андрей", "Семёнович", 
             "1989-03-17", "518976", "1", "133", "apudov"},
            {"Eremina", "Olga", "Vladimirovna", "Еремина", "Ольга", "Владимировна", 
             "1990-04-06", "518976", "0", "133", "apudov"}};
        final String[][] udata = {
            {"567395", "RU", "apudov"}};

        /* the list of athletes IDs */
        int id[] = new int[sdata.length];

        database.clear();
        
        for (final String[] source : sdata) {
            final ResultSet set = database.call("add_athlete", 
                "%s %s %s %s %s %s %t %l %i %i %s",
                source[0], source[1], source[2], source[3], source[4], 
                source[5], source[6], Long.parseLong(source[7]), 
                Integer.parseInt(source[8]), Integer.parseInt(source[9]), 
                source[10]);
            
            
        }

        for (final String[] unit : udata) {
            validate("remove_region_from_athlete", unit[3], "%s %s %s",
                     unit[0], unit[1], unit[2]);
        }

        System.out.println("Method: remove_region_from_athlete() SUCCESS");
    }

    private void u_geo_country_list() {
         for (final String[] unit : Values.GEO_COUNTRY_LIST) {
             validate("geo_country_list", unit[2], "%s %s",
                     unit[0], unit[1]);
        }

        System.out.println("Method: geo_country_list() SUCCESS");
    }

    private void u_geo_region_list() {
        for (final String[] unit : Values.GEO_REGION_LIST) {
            validate("geo_region_list", unit[3], "%i %s %s",
                     Integer.parseInt(unit[0]), unit[1], unit[2]);
        }

        System.out.println("Method: geo_region_list() SUCCESS");
    }

    private void u_geo_city_list() {
        final String[][] data = {
            {"567395", "RU", "apudov", "[583437, Алатырь], [582533, Аликово], [827792, Атлашево], "
                 + "[578659, Батырево], [571154, Буинск], [470845, ВУРНАРЫ], [557628, Ибреси], "
                 + "[555950, Ишлей], [553216, Канаш], [548302, Киря], [543133, Козловка], "
                 + "[545690, Комсомольское], [541791, Красн.Четаи], [542429, Красноармейское], "
                 + "[539794, Кугеси], [529343, Мариинский Посад], [525280, Моргауши], "
                 + "[518976, Новочебоксарск], [866053, Новые Лапсары], [506286, Порецкое], "
                 + "[478689, Урмары], [480851, Цивильск], [569696, Чебоксары], [494920, Шемурша], "
                 + "[494514, Шихазаны], [493463, Шумерля], [469809, Ядрин], [469049, Янтиково]"},
            {"000000", "EN", "apudov", "[]"}};

        for (final String[] unit : data) {
            validate("geo_city_list", unit[3], "%s %s %s",
                     unit[0], unit[1], unit[2]);
        }

        System.out.println("Method: geo_city_list() SUCCESS");
    }

    private void u_geo_get_city_complete_name_by_id() {
        final String[][] data = {
            {"518976", "EN", "apudov", "[Russian Federation, Chuvashskaya Respublika, Novocheboksarsk]"},
            {"551487", "EN", "apudov", "[Russian Federation, Tatarstan, Kazan]"},
            {"518976", "RU", "apudov", "[Россия, Республика Чувашия, Новочебоксарск]"},
            {"551487", "RU", "apudov", "[Россия, Республика Татарстан, Казань]"},

            {"484048", "RU", "apudov", "[Россия, Республика Татарстан, Республика Татарстан]"},
            {"000000", "RU", "apudov", "[]"}};
         for (final String[] unit : data) {
             validate("geo_get_city_complete_name_by_id", unit[3], "%i %s %s",
                     Integer.parseInt(unit[0]), unit[1], unit[2]);
        }

        System.out.println("Method: geo_get_city_complete_name_by_id() SUCCESS");
    }

    private void  u_geo_get_city_id_by_complete_name() {
        final String[][] data = {{"Russia", "Chuvashia", "Novocheboksarsk", "apudov", "[518976]"},
            {"Russian Federation", "Chuvash Republic", "Novocheboksarsk", "apudov", "[518976]"},
            {"Россия", "Чувашия", "Новочебоксарск", "apudov", "[518976]"},
            {"Российская Федерация", "Республика Чувашия", "Новочебоксарск", "apudov", "[518976]"}, 
            {"Russia", "Чувашия", "Новочебоксарск", "apudov", "[518976]"},

            {"Россия", "Чувашская Республика", "Новочебоксарск", "apudov", "[]"}};
        for (final String[] unit : data) {
            validate("geo_get_city_id_by_complete_name", unit[4], "%s %s %s %s",
                     unit[0], unit[1], unit[2], unit[3]);
        }

        System.out.println("Method: geo_get_city_id_by_complete_name() SUCCESS");
    }

    /**
     * Compares result set value with given string and return comparison result.
     *
     * @param set   the database result set to compare.
     * @param value the string contains expected value.
     *
     * @return      true if result set is equals to expected value and false otherwise.
     */
    private boolean validate(final String procedure, final String expected, 
                             final String format, final Object ... args) {
        try {
            final ResultSet set    = database.call(procedure, format, args);
            final String    result = getResult(set);

            if (expected.equals(result) == false) {
                System.out.println(String.format(
                    "Method: %s() FAILED\n\nArguments: %s\nExpected: %s\nValue: %s", 
                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                    Arrays.toString(args), expected, result));
                System.exit(Integer.MIN_VALUE);
            }

            return true;
        } catch (final SQLException e) {
            System.err.println(String.format(
                "Unable to validate a result set [%s].", e.getMessage()));
            System.exit(Integer.MIN_VALUE);

            /* required return statement */
            return false;
        }
    }

    /**
     * Builds the string representation of result set.
     *
     * @param set the database result set.
     * @return    the string representation.
     */
    private String getResult(final ResultSet set) throws SQLException {
        final ResultSetMetaData meta    = set.getMetaData();
        final StringBuilder     builder = new StringBuilder(16);

        int columns = meta.getColumnCount();

        builder.append('[');
        while (set.next()) {
            for (int index = 1; index <= columns; ++index) {
                builder.append(set.getObject(index));
                if (index != columns) {
                    builder.append(", ");
                }
            }

            if (set.isLast() == false) {
                builder.append("], [");
            }
        }

        builder.append(']');
        return builder.toString();
    }
}
