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

package com.onlineathletics.core;

import com.onlineathletics.util.Database;
import com.onlineathletics.util.Parameters;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The athlete entity database representation layer.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Athlete.java
 * %date      11:30:00 AM, Aug 03, 2013
 */
public class Athlete extends com.intellijustice.core.Athlete {
    
    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.onlineathletics.core.Manifest.NAME);

    /**
     * Constructs new Athletic object.
     *
     * @param id         the database id for the athlete (zero for new entry).
     * @param firstName  the firth name of the athlete.
     * @param secondName the second name of the athlete.
     * @param firstNameLocale  the first name of the athlete in local language.
     * @param secondNameLocale the second name of the athlete in local language.
     * @param birthday   the birthday of the athlete.
     * @param sex        the sex of the athlete (true for male).
     * @param country    the country where the athlete from.
     */
    private Athlete(final int id, final String firstName,
            final String secondName, final String firstNameLocale,
            final String secondNameLocale, final long birthday,
            final boolean sex, final String country) {
        super(id, firstName, "", secondName, firstNameLocale, "", secondNameLocale, 
                birthday, sex, country, 0);
    }
    
    /**
     * Returns an athlete representation for given database id.
     * 
     * @param id the database identification number.
     * @return   the athlete representation.
     * 
     * @throws SQLException the source of the exception.
     */
    public static Athlete getAthleteById(final int id) throws SQLException {
        try (final Connection        connection = Database.getConnection();
             final CallableStatement statement  = connection.prepareCall(Database.PRC_GET_ATHLETE)) {
            
            statement.setInt(1, id);
            statement.setString(2, Parameters.get("MySQL_OA_username"));
            
            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                
                return new Athlete(resultSet.getInt("athlete_id_var"),
                    resultSet.getString("first_nm_var"),
                    resultSet.getString("last_nm_var"),
                    "",
                    "",
                    resultSet.getDate("birthday_var").getTime(),
                    resultSet.getInt("sex_var") != 0,
                    "");
            }
        }
    }
}
