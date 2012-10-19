/*
 * IntelliVision Intelligence Image Processing System
 *
 * The MIT License
 *
 * Copyright 2011-2012 Andrey Pudov.
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

package com.intellivision.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

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
            = java.util.logging.Logger.getLogger("IntelliVision");

    private static final Properties properties = new Properties();

    static {
        /*
         * Initialize application settings with default values and load
         * applicatino settings from configuration file (if file is not exists,
         * default values will be used).
         */
        init();

        File[] directoryList = {
            /* .IntelliVision */
            new File(System.getProperty("user.home")
                + System.getProperty("file.separator")
                + ".IntelliVision")
        };

        File[] fileList = {
            /* .IntelliVision/.settings */
            new File(System.getProperty("user.home")
                + System.getProperty("file.separator")
                + ".IntelliVision"
                + System.getProperty("file.separator")
                + ".settings")
        };

        for (File directory : directoryList) {
            if (!(directory.exists()) || !(directory.isDirectory())) {
                if (!(directory.delete()) && !(directory.mkdirs())) {
                    LOG.warning("Application settings initializaition failed!"
                            + " Default values will be used.");
                }
            }
        }

        for (File file : fileList) {
            if (!(file.exists()) || !(file.isFile())) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    LOG.warning(new StringBuffer(256
                        ).append("Could not create properties file. "
                        ).append(e.getMessage()).toString());
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
    public static Settings getSettings() {
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
    public String getValue(String key) {
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
    public String setValue(String key, String value) {
        return (String) properties.setProperty(key, value);
    }

    /**
     * Initialize application settings.
     */
    private static void init() {
        File settings = new File(System.getProperty("user.home")
                + System.getProperty("file.separator") + ".IntelliVision"
                + System.getProperty("file.separator") + ".settings");

        properties.setProperty("intellivision.window.width",  "1020.0");
        properties.setProperty("intellivision.window.height", "700.0");

        try {
            properties.loadFromXML(new FileInputStream(settings));
        } catch (IOException e) {
            LOG.warning(new StringBuffer(256
                    ).append("Could not read application properties. "
                    ).append(e.getMessage()).toString());
        }
    }

    /**
     * Reads application settings from configuration file.
     */
    public void load() {
        File settings = new File(System.getProperty("user.home")
                + System.getProperty("file.separator") + ".IntelliVision"
                + System.getProperty("file.separator") + ".settings");

        try {
            properties.loadFromXML(new FileInputStream(settings));
        } catch (IOException e) {
            LOG.warning(new StringBuffer(256
                    ).append("Could not read application properties. "
                    ).append(e.getMessage()).toString());
        }
    }

    /**
     * Writes application settings to configuration files.
     */
    public void save() {
        File settings = new File(System.getProperty("user.home")
                + System.getProperty("file.separator") + ".IntelliVision"
                + System.getProperty("file.separator") + ".settings");

        try {
            properties.storeToXML(new FileOutputStream(settings),
                    "IntelliVision Intelligence Image Processing System");
        } catch (IOException e) {
            LOG.warning(new StringBuffer(256
                    ).append("Could not write application properties. "
                    ).append(e.getMessage()).toString());
        }
    }
}