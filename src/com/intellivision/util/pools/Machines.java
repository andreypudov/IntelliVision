/*
 * IntelliVision Intelligence Image Processing System
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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Remote machine list pool.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Machines.java
 * %date      04:00:00 PM, Jan 12, 2013
 */
public enum Machines {
    INSTANCE;

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellivision.core.Manifest.NAME);

    /* remote machine list */
    private static final List<Machine> list = new ArrayList<>(10);

    /* the list of application properties */
    private static final Settings SETTINGS = Settings.getSettings();

    static {
        /*
         * Inithialize machine list with entries from application configuration.
         */

        /* .IntelliVision/machines */
        final File directory = new File(System.getProperty("user.home")
                + System.getProperty("file.separator")
                + "." + com.intellivision.core.Manifest.NAME
                + System.getProperty("file.separator")
                + "machines");

        if (!(directory.exists()) || !(directory.isDirectory())) {
            directory.delete();
            directory.mkdirs();

            LOG.warning("First time initialization."
                    + " Empty remote machine list will be created.");
        }

        for (File entry : directory.listFiles()) {
            /* skip directories and corrupted files */
            if (!(entry.isFile()) || !(entry.canRead())) {
                continue;
            }


            try {
                final SAXParserFactory factory = SAXParserFactory.newInstance();
                final SAXParser        parser  = factory.newSAXParser();

                final SchemaFactory    schemaFactory = SchemaFactory.newInstance(
                        XMLConstants.W3C_XML_SCHEMA_NS_URI);
                final Schema           schema = schemaFactory.newSchema(
                        Machines.class.getResource(
                        "/com/intellivision/resources/schemas/RemoteMachine.xsd"));

                final Validator        validator = schema.newValidator();

                final MachineHandler   handler = new MachineHandler();

                validator.validate(new StreamSource(entry));
                parser.parse(entry, handler);
                list.add(handler.getMachine());
            } catch (ParserConfigurationException | SAXException | IOException e) {
                LOG.severe(new StringBuffer(256
                        ).append("Could not read machine configuration file. "
                        ).append(e.getMessage()).toString());
            }
        }
    }

    /* do not let anyone instantiate this class */
    private Machines() {
    }

    /**
     * Returns an instance of remote machine list.
     *
     * @return the remote machine list.
     */
    public static synchronized Machines getMachines() {
        return Machines.INSTANCE;
    }

    /**
     * Adds machine to the remote machine list.
     *
     * @param machine the remote machine.
     */
    public synchronized void add(Machine machine) {
        list.add(machine);
    }

    /**
     * Returns the list iterator over the elements in remote machine list.
     *
     * @return the list iterator over the elements in remote machine list.
     */
    public synchronized ListIterator<Machine> getIterator() {
        return list.listIterator();
    }

    /**
     * Return an unmodifiable view of the remote machine list.
     *
     * @return an unmodifiable view of the remote machine list.
     */
    public synchronized List<Machine> getList() {
        return Collections.unmodifiableList(list);
    }
}
