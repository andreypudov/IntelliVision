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

package com.intellijustice.util.pools;

import java.io.File;

/**
 * The application pool provides directory synchronization functionality.
 * Implementation based on Git revision control system.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Reviser.java
 * %date      01:40:00 PM, Nov 11, 2012
 */
public enum Reviser {

    INSTANCE;

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellijustice.core.Manifest.NAME);

    /* the list of application properties */
    private static final Settings SETTINGS = Settings.getSettings();

    /* configuration file structure */
    private static final String FILE_SEPARATOR =
            System.getProperty("file.separator");
    private static final String HOME_DIRECTORY =
            System.getProperty("user.home");
    private static final String CONFIG_DIRECTORY = HOME_DIRECTORY
            + FILE_SEPARATOR + "." + com.intellijustice.core.Manifest.NAME;
    private static final String WEBSITE_DIRECTORY = CONFIG_DIRECTORY
            + FILE_SEPARATOR + "website";

    static {
    }

    /* do not let anyone instantiate this class */
    private Reviser() {
    }

    /**
     * Returns an instance of application synchronization pool.
     *
     * @return the application synchronization pool.
     */
    public static synchronized Reviser getReviser() {
        return Reviser.INSTANCE;
    }

    /**
     * Synchronize website content data.
     */
    public synchronized void synchronize() {
        final String website  = SETTINGS.getValue("intellijustice.website.url");
        final String username = SETTINGS.getValue("intellijustice.website.username");
        final String password = SETTINGS.getValue("intellijustice.website.password");

        validateDirectory(website, username, password);
    }

    /**
     * Validates website directory and initialize checkout from specified
     * website address if directory doesn't exists.
     *
     * @param website  the website address to use.
     * @param username the user name to Git repository.
     * @param password the user password to Git repository.
     */
    private void validateDirectory(final String website, final String username,
            final String password) {
        boolean directoryCreated = false;

        /* validates file structure */
        final File[] directoryList = {
            /* .IntelliJustice */
                new File(CONFIG_DIRECTORY),
                new File(WEBSITE_DIRECTORY)
        };

        for (File directory : directoryList) {
            if (!(directory.exists()) || !(directory.isDirectory())) {
                directory.delete();
                directory.mkdirs();

                directoryCreated = true;
            }
        }
    }
}
