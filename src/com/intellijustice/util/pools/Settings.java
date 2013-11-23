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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.TreeMap;
import java.util.logging.Level;

/**
 * Provides a persistent set of properties.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Settings.java
 * %date      06:00:00 PM, Sep 12, 2012
 */
public enum Settings {

    INSTANCE;

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellijustice.core.Manifest.NAME);

    private static final Properties properties = new Properties();

    /* configuration file structure */
    private static final String FILE_SEPARATOR =
            System.getProperty("file.separator");
    private static final String HOME_DIRECTORY =
            System.getProperty("user.home");
    private static final String CONFIG_DIRECTORY = HOME_DIRECTORY
            + FILE_SEPARATOR + "." + com.intellijustice.core.Manifest.NAME;
    private static final String SETTINGS_FILE = CONFIG_DIRECTORY
            + FILE_SEPARATOR + ".settings";

    static {
        /*
         * Initialize application settings with default values and load
         * applicatino settings from configuration file (if file is not exists,
         * default values will be used).
         */
        init();

        /* prepare  settings by replacing templates with an appropriate values */
        replaceTemplates();

        /*
         * Merge application configuration with default and exists values when
         * existing configuration file contains entries corresponds to
         * different application version.
         */
        if (properties.getProperty("intellijustice.application.version"
                ).equals(com.intellijustice.core.Manifest.VERSION) == false) {
            properties.setProperty("intellijustice.application.version",
                    com.intellijustice.core.Manifest.VERSION);
        }

        final File[] directoryList = {
            /* .IntelliJustice */
            new File(CONFIG_DIRECTORY)
        };

        final File[] fileList = {
            /* .IntelliJustice/.settings */
            new File(SETTINGS_FILE)
        };

        for (File directory : directoryList) {
            if (!(directory.exists()) || !(directory.isDirectory())) {
                directory.delete();
                directory.mkdirs();

                LOG.warning("First time initialization."
                            + " Default values will be used.");
            }
        }

        for (File file : fileList) {
            if (!(file.exists()) || !(file.isFile())) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    LOG.log(Level.WARNING,
                            "Could not create properties file. {0}",
                            e.getMessage());
                }
            }
        }
    }

    /* do not let anyone instantiate this class */
    private Settings() {
    }

    /**
     * Returns an instance of application settings list.
     *
     * @return the suitable settings list.
     */
    public static synchronized Settings getSettings() {
        return Settings.INSTANCE;
    }

    /**
     * Searches for the property with the specified key in this property list.
     * The method returns null if the property is not found.
     *
     * @param key the key whose associated value is to be returned.
     * @return    the value to which the specified key is mapped, or null if
     *            this map contains no mapping for the key.
     */
    public synchronized String getValue(final String key) {
        return properties.getProperty(key);
    }

    /**
     * Sets property value for the specified key. The method returns the
     * previous value of the specified key.
     *
     * @param key   the key to be placed into this property list.
     * @param value the value corresponding to key.
     * @return      the previous value of the specified key in this property
     *              list, or null if it did not have one.
     */
    public synchronized String setValue(final String key, final String value) {
        return (String) properties.setProperty(key, value);
    }

    /**
     * Initialize application settings.
     */
    private static synchronized void init() {
        final File settings = new File(SETTINGS_FILE);

        try {
            /* load default values for application properties */
            properties.loadFromXML(Settings.class.getResource(
                "/com/intellijustice/resources/schemas/InitialConfiguration.xml"
                ).openStream());

            /* load user configuration file */
            properties.loadFromXML(new FileInputStream(settings));
        } catch (IOException e) {
            LOG.log(Level.WARNING,
                    "Could not read application properties. {0}",
                    e.getMessage());
        }
    }

    /**
     * Replaces templates in application settings values by the corresponding
     * configuration values.
     */
    private static synchronized void replaceTemplates() {
        final Map<String, String> templates = new TreeMap<>();
        templates.put("${FILE_SEPARATOR}",   FILE_SEPARATOR);
        templates.put("${HOME_DIRECTORY}",   HOME_DIRECTORY);
        templates.put("${CONFIG_DIRECTORY}", CONFIG_DIRECTORY);

        for (Entry<Object, Object> entry : properties.entrySet()) {
            final String key   = (String) entry.getKey();
            final String value = (String) entry.getValue();

            /* replace templates */
            if (value.indexOf('$') != -1) {
                String str = value;

                for (Entry<String, String> template : templates.entrySet()) {
                    str = str.replace(template.getKey(), template.getValue());
                }

                properties.setProperty(key, str);
            }
        }
    }

    /**
     * Reads application settings from configuration file.
     */
    public synchronized void load() {
        final File settings = new File(SETTINGS_FILE);

        try {
            properties.loadFromXML(new FileInputStream(settings));
        } catch (IOException e) {
            LOG.log(Level.WARNING,
                    "Could not read application properties. {0}",
                    e.getMessage());
        }
    }

    /**
     * Writes application settings to configuration files.
     */
    public synchronized void save() {
        final File settings = new File(SETTINGS_FILE);

        try {
            properties.storeToXML(new FileOutputStream(settings),
                    com.intellijustice.core.Manifest.DESCRIPTION);
        } catch (IOException e) {
            LOG.log(Level.WARNING,
                    "Could not write application properties. {0}",
                    e.getMessage());
        }
    }
}