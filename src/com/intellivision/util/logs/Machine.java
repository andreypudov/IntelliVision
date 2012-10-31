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

package com.intellivision.util.logs;

import com.intellivision.util.ssh.ChannelExec;
import com.intellivision.util.ssh.SSHConnection;
import com.intellivision.util.ssh.SSHException;
import com.intellivision.util.ssh.Session;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
            = java.util.logging.Logger.getLogger("IntelliVision");

    /* secure connection to the remote machine */
    private final SSHConnection connection = new SSHConnection();

    /* connection details */
    private final System system;
    private final String username;
    private final String password;
    private final String address;

    /* openned session to the remote machine */
    private Session session;

    public Machine(final String username, final String password,
                   final String address) {

        this.system   = System.UNDEFINED;
        this.username = username;
        this.password = password;
        this.address  = address;
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

    public System getSystem() throws SSHException, IOException {
        final ChannelExec channel = (ChannelExec) session.openChannel("exec");

        channel.setCommand("uname");
        channel.setInputStream(null);
        channel.connect();

        final StringBuffer   buffer = new StringBuffer(16);
        final InputStream    stream = channel.getInputStream();
        final BufferedReader reader = new BufferedReader(
            new InputStreamReader(stream));

        String line = reader.readLine();
        while (line != null) {
            buffer.append(line).append('\n');
            line = reader.readLine();
        }

        channel.disconnect();

        /* determine operating system */
        switch(buffer.toString()) {
            case "Darwin":
                return System.OSX;
            case "Linux":
                return System.LINUX;
            default:
                return System.UNDEFINED;
        }
    }
}
