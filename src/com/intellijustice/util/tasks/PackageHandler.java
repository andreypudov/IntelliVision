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

import java.net.MalformedURLException;
import java.net.URL;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * The general application manifest file handler.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      PackageHandler.java
 * %date      02:00:00 PM, Aug 07, 2013
 */
public class PackageHandler extends DefaultHandler {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
              com.intellijustice.core.Manifest.NAME);

    /* current XML element */
    private String     currentElement    = null;
    private Attributes currentAttributes = null;

    /* package manifest representation */
    private Package pkg = null;

    private String name     = "";
    private URL    link     = null;
    private long   version  = 0L;
    private Stage  stage    = Stage.ALPHA;
    private String checksum = "";

    /**
     * Returns parsing result.
     *
     * @return the package manifest entry or null.
     */
    public Package getPackage() {
        return pkg;
    }

    /*
     * Receive notification of the start of an element.
     */
    @Override
    public void startElement(final String uri, final String localName,
            final String qName, final Attributes attributes)
            throws SAXException {
        currentElement    = qName;
        currentAttributes = attributes;
    }

    /*
     * Receive notification of the end of an element.
     */
    @Override
    public void endElement(final String uri, final String localName,
            final String qName) throws SAXException {
        currentElement    = null;
        currentAttributes = null;

        if (qName.equalsIgnoreCase("package")) {
            pkg = new Package(name, link, version, stage, checksum);

            name     = "";
            link     = null;
            version  = 0;
            stage    = Stage.ALPHA;
            checksum = "";
        }
    }

    /*
     * Receive notification of character data inside an element.
     */
    @Override
    public void characters(final char ch[], final int start, final int length)
            throws SAXException {
        if ((currentElement == null) || (currentAttributes == null)) {
            return;
        }

        if (currentElement.equalsIgnoreCase("name")) {
            name = new String(ch, start, length);
        }

        if (currentElement.equalsIgnoreCase("link")) {
            try {
                link = new URL(new String(ch, start, length));
            } catch (MalformedURLException ex) {
                throw new SAXException(ex);
            }
        }

        if (currentElement.equalsIgnoreCase("version")) {
            version = Long.parseLong(new String(ch, start, length));
        }

        if (currentElement.equalsIgnoreCase("stage")) {
            final String value = new String(ch, start, length);
            switch (value) {
                case "ALPHA":
                    stage = Stage.ALPHA;
                    break;
                case "BETA":
                    stage = Stage.BETA;
                    break;
                case "RELEASE":
                    stage = Stage.RELEASE;
                    break;
                default:
                    stage = Stage.ALPHA;
                    break;
            }
        }

        if (currentElement.equalsIgnoreCase("checksum")) {
            checksum = new String(ch, start, length);
        }
    }
}
