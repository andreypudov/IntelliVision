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

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
/**
 * Database connection reresentation.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Database.java
 * %date      03:50:00 AM, Jul 22, 2014
 */
public class Database {

    /* a connection to database */
    private Connection connection = null;

    /**
     * Creates a connection to database using given username and password.
     *
     * @param username the username to connect to database.
     * @param password the password to connect to database.
     */
    public Database(final String username, final String password) {
        try {
            connection =  DriverManager.getConnection(
                String.format("jdbc:mysql://localhost/onlineathletics?"
                    + "user=%s&password=%s"
                    + "&noAccessToProcedureBodies=true"
                    + "&characterEncoding=UTF-8", username, password));
        } catch (final SQLException e) {
            System.err.println(String.format(
                "Unable to connect to database [%s].", e.getMessage()));
            System.exit(Integer.MIN_VALUE);
        }
    }

    /**
     * Clears database content.
     */
    public void clear() {
        final String[] queries = {
            "SET FOREIGN_KEY_CHECKS=0;",
            "TRUNCATE oa_athl_tbl;",
            "TRUNCATE oa_first_nm_tbl;",
            "TRUNCATE oa_middle_nm_tbl;",
            "TRUNCATE oa_last_nm_tbl;",
            "TRUNCATE oa_birthday_tbl;",
            "SET FOREIGN_KEY_CHECKS=1;"};

        try {
            final Statement statement = connection.createStatement();

            /* add separate queries to a execution list */
            for (String query : queries) {
                statement.addBatch(query);
            }

            statement.executeBatch();
        } catch (final SQLException e) {
            System.err.println(String.format(
                "Unable to clear database [%s].", e.getMessage()));
        }
    }

    /*
     * Makes a call to given stored procedure using format string to specify
     * the type of procedure arguments.
     *
     * @param procedure the name of the stored procedure.
     * @param format    the format string contains the list of argument's types.
     * @param args      the variable list of arguments.
     *
     * @return          the result set of a stored procedure call.
     */
    public ResultSet call(final String procedure, final String format, final Object ... args) {
        final CallableStatement statement;

        try {
            final StringBuilder builder = new StringBuilder(16);
            builder.append("{CALL `onlineathletics`.`"
                  ).append(procedure
                  ).append("` (");
            for (int index = 0; index < args.length; ++index) {
                builder.append('?');

                if (index + 1 < args.length) {
                    builder.append(", ");
                }
            }
            builder.append(")}");
            statement = connection.prepareCall(builder.toString());

            int index = 0;
            for (final String entry : format.split(" ")) {
                if (entry.equals("%s")) {
                    statement.setString(index + 1, (String) args[index++]);
                    continue;
                }

                if (entry.equals("%i")) {
                    statement.setInt(index + 1, (int) args[index++]);
                    continue;
                }

                if (entry.equals("%l")) {
                    statement.setLong(index + 1, (long) args[index++]);
                    continue;
                }

                if (entry.equals("%t")) {
                    statement.setTimestamp(index + 1, new Timestamp(getTime((String) args[index++])));
                    continue;
                }
            }

            return statement.executeQuery();
        } catch (final SQLException | ParseException e) {
            System.err.println(String.format(
                "Unable to call a stored procedure [%s].", e.getMessage()));
             System.exit(Integer.MIN_VALUE);

             /* required return statement */
             return null;
        }
    }

    /**
     * Returns a time in milliseconds for given string contains year, month and day.
     *
     * @param value the string contains year, month and day values.
     * @return      the long value represents time in milliseconds.
     */
    private long getTime(final String value) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(value).getTime();
    }
}
