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

package com.intellijustice.util.tasks;

import java.net.URL;

/**
 * Package entry used by application update task.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Package.java
 * %date      11:15:00 AM, Aug 07, 2013
 */
public class Package {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellijustice.core.Manifest.NAME);

    private final String name;
    private final URL    link;
    private final long   version;
    private final Stage  stage;
    private final String checksum;

    public Package(final String name, final URL link, final long version,
            final Stage stage, final String checksum) {
        this.name     = name;
        this.link     = link;
        this.version  = version;
        this.stage    = stage;
        this.checksum = checksum;
    }

    /**
     * The package binary file name.
     *
     * @return the file name of the application binary file.
     */
    public String getName() {
        return name;
    }

    /**
     * The download link of the binary file.
     *
     * @return the URL to the application file located on the project server.
     */
    public URL getLink() {
        return link;
    }

    /**
     * The binary build number.
     *
     * @return the version of the application binary file.
     */
    public long getVersion() {
        return version;
    }

    /**
     * The application development life cycle stage. If stage is unknown, alpha
     * will be returned.
     *
     * @return the application development life cycle stage.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Returns application binary checksum calculated by SHA-512 algorithm.
     *
     * @return the binary checksum string.
     */
    public String getChecksum() {
        return checksum;
    }

    @Override
    public String toString() {
        return new StringBuilder(128).append(name).append(" "
                ).append(link).append(" "
                ).append(version).append(" "
                ).append(stage).append(" "
                ).append(checksum).toString();
    }
}
