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

package com.intellivision.util.logs;

import com.intellivision.util.ssh.ChannelExec;
import com.intellivision.util.ssh.SSHConnection;
import com.intellivision.util.ssh.SSHException;
import com.intellivision.util.ssh.Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.util.Properties;

/**
 * The local and remote machine layer contains methods to obtain machine
 * information and its log files.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Machine.java
 * %date      09:30:00 AM, Oct 30, 2012
 */
public class Machine {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellivision.core.Manifest.NAME);

    /* secure connection to the remote machine */
    private final SSHConnection connection = new SSHConnection();

    /* connection details */
    private String name;
    private String username;
    private String password;
    private String address;

    private final MachineChangedEventHandler handler
            = new MachineChangedEventHandler();

    /* unique identification number for the remote machine */
    private final int UID;

    /* openned session to the remote machine */
    private Session session;

    public Machine(final String name, final String username,
            final String password, final String address) {

        SecureRandom random = new SecureRandom();

        this.name     = name;
        this.username = username;
        this.password = password;
        this.address  = address;

        UID = random.nextInt();
    }

    public void connect() throws SSHException {
        if ((session == null) || (session.isConnected())) {
            return;
        }

        final Properties properties = new Properties();

        /* disable strict host key checking in ssh */
        properties.put("StrictHostKeyChecking", "no");

        session = connection.getSession(username, address, 22);

        session.setPassword(password);
        session.setConfig(properties);
        session.connect(3000);

        session.connect();
    }

    public void disconnect() {
        if ((session == null) || !(session.isConnected())) {
            return;
        }

        session.disconnect();
    }

//    public System getSystem() throws SSHException, IOException {
//        final ChannelExec channel = (ChannelExec) session.openChannel("exec");
//
//        channel.setCommand("uname");
//        channel.setInputStream(null);
//        channel.connect();
//
//        final StringBuffer   buffer = new StringBuffer(16);
//        final InputStream    stream = channel.getInputStream();
//        final BufferedReader reader = new BufferedReader(
//            new InputStreamReader(stream));
//
//        String line = reader.readLine();
//        while (line != null) {
//            buffer.append(line).append('\n');
//            line = reader.readLine();
//        }
//
//        channel.disconnect();
//
//        /* determine operating system */
//        switch(buffer.toString()) {
//            case "Darwin":
//                return System.OSX;
//            case "Linux":
//                return System.LINUX;
//            default:
//                return System.UNDEFINED;
//        }
//    }

    /**
     * Adds external machine event listener.
     *
     * @param listener the remote machine changed event listener.
     */
    public void addListener(final MachineChangedEventListener listener) {
        handler.addEventListener(listener);
    }

    /**
     * Returns remote machine name.
     *
     * @return the remote machine name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns user name for this remote machine instance.
     *
     * @return the user name.
     */
    public String getUserName() {
        return username;
    }

    /**
     * Returns user password for this remote machine instance.
     *
     * @return the user password.
     */
    public String getUserPassword() {
        return password;
    }

    /**
     * Returns machine address for this remote machine instance.
     *
     * @return the remote machine address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets remote machine name.
     *
     * @param name the remote machine name.
     */
    public void setName(final String name) {
        this.name = name;

        handler.fireEvent();
    }

    /**
     * Sets user name for this remote machine instance.
     *
     * @param username the user name.
     */
    public void setUserName(final String username) {
        this.username = username;

        handler.fireEvent();
    }

    /**
     * Sets user password for this remote machine instance.
     *
     * @param password the user password.
     */
    public void setUserPassword(final String password) {
        this.password = password;

        handler.fireEvent();
    }

    /**
     * Sets machine address for this remote machine instance.
     *
     * @param address the remote machine address.
     */
    public void setAddress(final String address) {
        this.address = address;

        handler.fireEvent();
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param object the reference object with which to compare.
     * @return       {@code true} if this machine is the same as the object
     *               argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }

        if (object instanceof Machine) {
            Machine anotherMachine = (Machine) object;

            return (anotherMachine.UID == UID);
        }

        return false;
    }

    /**
     * Returns a hash code value for the machine.
     *
     * @return a hash code value for this machine.
     */
    @Override
    public int hashCode() {
        return UID;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return new StringBuilder(256).append("Name: ").append(name
                ).append(", User: ").append(username
                ).append(", Address: ").append(address).toString();
    }
}
