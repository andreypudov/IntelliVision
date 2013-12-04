/*
 * IntelliJustice Intelligent Referee Assistant System
 *
 * The MIT License
 *
 * Copyright 2011-2013 Andrey Pudov.
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

package com.onlineathletics.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * The internal web service provides public methods to obtain technical
 * information about Online Athletics and IntelliJustice status.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      InternalService.java
 * %date      07:15:00 PM, Dec 03, 2013
 */
public class Database {
    
    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.onlineathletics.core.Manifest.NAME);
    
    /* the name of the database pool in application container */
    private static final String POOL_NAME = "java:comp/env/jdbc/OnlineAthletics";
    
    private static DataSource source;
    
    static {
        try {
            source = (DataSource) new InitialContext().lookup(POOL_NAME);
        } catch (NamingException e) {
            LOG.log(Level.SEVERE, 
                    "Could not get data source from application container.{0}", 
                    e.getMessage());
        }
    }
    
    /* do not let anyone instantiate this class */
    private Database() {
    }
    
    /**
     * 
     * @return
     * @throws SQLException 
     */
    public static Connection getConnection() throws SQLException {
        return source.getConnection();
    }
    
    public static String executeStatement() throws SQLException {
        try (final Connection connection = getConnection()) {
            final CallableStatement statement = connection.prepareCall(
                    "{CALL `onlineathletics`.`getDatabaseVersion` ()}");
            
            //statement.setInt(1, 0);
            
            try (final ResultSet set = statement.executeQuery()) {
                while (set.next()) {
                    return "dddddd " + set.getString(1);
                }
            }
        }
        
        return "";
    }
}
