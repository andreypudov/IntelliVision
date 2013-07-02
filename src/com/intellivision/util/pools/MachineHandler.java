/*
 * IntelliJustice Intelligence Referee Assistant System
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

import com.intellivision.util.logs.Machine;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Machine file format SAX handler implementation.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      MachineHandler.java
 * %date      01:40:00 PM, Jan 13, 2013
 */
public class MachineHandler extends DefaultHandler {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
              com.intellivision.core.Manifest.NAME);

    /* current XML element */
    private String     currentElement    = null;
    private Attributes currentAttributes = null;

    /* remote machine list */
    private List<Machine> list = new ArrayList<>(10);

    private String name     = "";
    private String username = "";
    private String password = "";
    private String address  = "";

    /**
     * Returns parsing result.
     *
     * @return the unmodifiable remote machine list.
     */
    public List<Machine> getMachines() {
        return Collections.unmodifiableList(list);
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

        if (qName.equalsIgnoreCase("machine")) {
            list.add(new Machine(name, username, password, address));

            name     = "";
            username = "";
            password = "";
            address  = "";
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

        if (currentElement.equalsIgnoreCase("machines")) {
            String ver = currentAttributes.getValue("version");

            if (ver.equalsIgnoreCase(com.intellivision.core.Manifest.VERSION) == false) {
                throw new SAXException(new StringBuffer(256
                    ).append("Invalid machine file format. "
                    ).append("Required: "
                    ).append("0.00.00. "
                    ).append("Found: "
                    ).append(ver
                    ).append('.').toString());
            }
        }

        if (currentElement.equalsIgnoreCase("name")) {
            name = new String(ch, start, length);
        }

        if (currentElement.equalsIgnoreCase("username")) {
            username = new String(ch, start, length);
        }

        if (currentElement.equalsIgnoreCase("password")) {
            password = new String(ch, start, length);
        }

        if (currentElement.equalsIgnoreCase("address")) {
            address = new String(ch, start, length);
        }
    }
}
