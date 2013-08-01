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

package com.intellivision.util.pools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Provides a database server managing layer.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Server.java
 * %date      05:30:00 PM, Jul 30, 2013
 */
public enum Server {

    INSTANCE;

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellivision.core.Manifest.NAME);

    /* the list of application properties */
    private static final Settings SETTINGS = Settings.getSettings();

    /* connection timeout in seconds */
    private static final int CONENCTION_TIMEOUT = 1;

    private static Connection connection = null;

    /* do not let anyone instantiate this class */
    private Server() {
    }

    /**
     * Returns an instance of database server manager.
     *
     * @return the database server manager.
     */
    public static synchronized Server getDatabaseServer() {
        return Server.INSTANCE;
    }

    /**
     * Returns database connection status.
     *
     * @return true if connection is alive, false otherwise.
     */
    public synchronized boolean isConnected() {
        try {
            if ((connection == null) || (connection.isClosed())) {
                return false;
            }

            return connection.isValid(CONENCTION_TIMEOUT);
        } catch (SQLException e) {
            LOG.warning(e.getMessage());
        }

        return false;
    }

    /**
     * Connects to the database server.
     */
    public synchronized void connect() {
        try {
            Properties properties = new Properties();
            /*
             * http://dev.mysql.com/doc/refman/5.0/en/
             * connector-j-reference-configuration-properties.html
             */
            properties.put("user",           SETTINGS.getValue("intellivision.database.username"));
            properties.put("password",       SETTINGS.getValue("intellivision.database.password"));
            properties.put("connectTimeout", SETTINGS.getValue("intellivision.database.connectTimeout"));
            properties.put("autoReconnect",  SETTINGS.getValue("intellivision.database.autoReconnect"));
            properties.put("maxReconnects",  SETTINGS.getValue("intellivision.database.maxReconnects"));

            /* the name of a class that will be used to log messages */
            properties.put("logger",         "com.intellivision.util.pools.ConnectionLogger");

            /* load MySQL driver */
            Class.forName(SETTINGS.getValue("intellivision.database.driver"));

            /* setup the connection to the database server */
            connection = DriverManager.getConnection(
                    /* jdbc:mysql://localhost:3306/database */
                    SETTINGS.getValue("intellivision.database.url")
                    + "://" + SETTINGS.getValue("intellivision.database.hostname")
                    + ":"   + SETTINGS.getValue("intellivision.database.port")
                    + "/"   + SETTINGS.getValue("intellivision.database.database"),
                    properties);
        } catch (ClassNotFoundException | SQLException e) {
            LOG.warning(e.getMessage());
        }
    }

    /**
     * Releases a connection to the database server.
     */
    public synchronized void disconnect() {
        try {
            if ((connection != null) && (connection.isClosed() != false)) {
                connection.close();
            }
        } catch (SQLException e) {
            LOG.warning(e.getMessage());
        }
    }
}
