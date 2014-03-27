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

package com.onlineathletics.services;

import com.onlineathletics.core.Athlete;
import com.onlineathletics.util.Database;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * The list of stored procedures caller implementations.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      StoredProcedures.java
 * %date      09:40:00 AM, Mar 26, 2014
 */
public class StoredProcedures {
    
    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.onlineathletics.core.Manifest.NAME);
    
    private static final String PRC_ADD_ATHLETE = "{CALL `onlineathletics`.`add_athlete` (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    private static final String PRC_GET_ATHLETE = "{CALL get_athlete(?, ?)}";
    
    private static final String VAR_ATHLETE_ID         = "athlete_id_var";
    private static final String VAR_FIRST_NAME         = "first_nm_var";
    private static final String VAR_MIDDLE_NAME        = "middle_nm_var";
    private static final String VAR_LAST_NAME          = "last_nm_var";
    private static final String VAR_FIRST_NAME_LOCALE  = "first_nm_lc_var";
    private static final String VAR_MIDDLE_NAME_LOCALE = "middle_nm_lc_var";
    private static final String VAR_LAST_NAME_LOCALE   = "last_nm_lc_var";
    private static final String VAR_BIRTHDAY           = "birthday_var";
    private static final String VAR_BIRTHPLACE         = "birthplace_var";
    private static final String VAR_SEX                = "sex_var";
    private static final String VAR_LANGUAGE           = "language_var";
    
    private static final int PARAM_FIRST  = 1;
    private static final int PARAM_SECOND = 2;
    
    /* do not let anyone instantiate this class */
    private StoredProcedures() {
    }
    
    /**
     * Adds athlete entry and returns identification number.
     * 
     * @param firstName           the firth name of the athlete.
     * @param middleName          the middle name of the athlete.
     * @param lastName            the last name of the athlete.
     *
     * @param firstNameLocalized  the firth name of the athlete in local language.
     * @param middleNameLocalized the middle name of the athlete in local language.
     * @param lastNameLocalized   the last name of the athlete in local language.
     *
     * @param birthday            the date of birth of the athlete.
     * @param birthplace          the place of birth of the athlete.
     * @param sex                 the sex of the athlete (true for male).
     *
     * @param language            the identifier of the local language.
     * @param userName            the name value to authenticate query.
     *
     * @return the database id for the athlete as athlete_indx column.
     * 
     * @throws java.lang.Exception
     */
    public static int addAthlete(final String firstName, 
            final String middleName, final String lastName,
            final String firstNameLocalized, final String middleNameLocalized,
            final String lastNameLocalized, final long birthday,
            final int birthplace, final boolean sex, 
            final int language, final String userName) 
            
            throws Exception {
        
        /* the database stored procedure statement */
        final CallableStatement statement;
        
        try {
            statement = Database.getConnection().prepareCall(PRC_ADD_ATHLETE);
            
            statement.setString("first_nm_arg",     firstName);
            statement.setString("middle_nm_arg",    middleName);
            statement.setString("last_nm_arg",      lastName);
            
            statement.setString("first_nm_lc_arg",  firstNameLocalized);
            statement.setString("middle_nm_lc_arg", middleNameLocalized);
            statement.setString("last_nm_lc_arg",   lastNameLocalized);
            
            statement.setTimestamp("birthday_arg",  new Timestamp(birthday));
            statement.setInt("birthplace_arg",      birthplace);
            statement.setInt("sex_arg",             (sex) ? 1 : 0);
            
            statement.setInt("language_arg",        language);
            statement.setString("user_nm_arg",      userName);
            
            try (final ResultSet set = statement.executeQuery()) {
                return set.getInt(1);
            }
        } catch (final SQLException e) {
            throw new Exception(e.getMessage());
        }
    }
    
    /**
     * Returns athlete entry for given database id.
     * 
     * @param id       the database id of the athlete.
     * @param userName the name value to authenticate query.
     * 
     * @return the athlete entry for given database id.
     * 
     * @throws SQLException the source of the exception.
     */
    public static Athlete getAthlete(final long id, final String userName) 
            throws SQLException {
        /* the database stored procedure statement */
        final CallableStatement statement;
        
        try {
            statement = Database.getConnection().prepareCall(PRC_GET_ATHLETE);
            
            statement.setLong(PARAM_FIRST, id);
            statement.setString(PARAM_SECOND, userName);

            
            try (final ResultSet set = statement.executeQuery()) {
                set.next();
                
                return new Athlete(set.getLong(VAR_ATHLETE_ID), 
                        set.getString(VAR_FIRST_NAME), 
                        set.getString(VAR_MIDDLE_NAME),
                        set.getString(VAR_LAST_NAME),
                        set.getString(VAR_FIRST_NAME_LOCALE),
                        set.getString(VAR_MIDDLE_NAME_LOCALE),
                        set.getString(VAR_LAST_NAME_LOCALE),
                        set.getDate(VAR_BIRTHDAY).getTime(),
                        set.getLong(VAR_BIRTHPLACE),
                        (set.getInt(VAR_SEX) != 0),
                        set.getLong(VAR_LANGUAGE)
                );
            }
        } catch (final SQLException e) {
            switch (DatabaseException.getType(e.getErrorCode())) {
                case INVALID_ARGUMENT_EXCEPTION:
                    throw new SQLException(e.getMessage());
                case PERMISSIONS_DENIED_EXCEPTION:
                    throw new SQLException(e.getMessage());
                case ATHLETE_ENTRY_DOES_NOT_EXISTS_EXCEPTION:
                    throw new SQLException(e.getMessage());
                default:
                    throw new SQLException(e.getMessage());
            }
        }
    }
}
