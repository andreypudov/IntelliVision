/*
 * IntelliJustice Intelligent Referee Assistant System
 *
 * The MIT License
 *
 * Copyright 2011-2014 Andrey Pudov.
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

import org.teleal.cling.UpnpService;
import org.teleal.cling.UpnpServiceImpl;
import org.teleal.cling.model.message.header.STAllHeader;
import org.teleal.cling.model.meta.LocalDevice;
import org.teleal.cling.model.meta.RemoteDevice;
import org.teleal.cling.registry.Registry;
import org.teleal.cling.registry.RegistryListener;

import java.util.logging.Level;


/**
 * The network application pool provides common talk interface
 * between application instances.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Network.java
 * %date      10:30:00 PM, Nov 08, 2012
 */
public enum Network {

    INSTANCE;

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellijustice.core.Manifest.NAME);

    static {
        RegistryListener listener = new RegistryListener() {
            @Override
            public void remoteDeviceDiscoveryStarted(final Registry registry,
                                                     final RemoteDevice device) {
                LOG.log(Level.INFO, "Discovery started: {0}",
                        device.getDisplayString());
            }

            @Override
            public void remoteDeviceDiscoveryFailed(final Registry registry,
                    final RemoteDevice device, final Exception ex) {
                LOG.log(Level.INFO, "Discovery failed: {0}",
                        device.getDisplayString());
            }

            @Override
            public void remoteDeviceAdded(final Registry registry,
                                          final RemoteDevice device) {
                LOG.log(Level.INFO, "Remote device available: {0}",
                        device.getDisplayString());
            }

            @Override
            public void remoteDeviceUpdated(final Registry registry,
                                            final RemoteDevice device) {
                LOG.log(Level.INFO, "Remote device updated: {0}",
                        device.getDisplayString());
            }

            @Override
            public void remoteDeviceRemoved(final Registry registry,
                                            final RemoteDevice device) {
                LOG.log(Level.INFO, "Remote device removed: {0}",
                        device.getDisplayString());
            }

            @Override
            public void localDeviceAdded(final Registry registry,
                                         final LocalDevice device) {
                LOG.log(Level.INFO, "Local device added: {0}",
                        device.getDisplayString());
            }

            @Override
            public void localDeviceRemoved(final Registry registry,
                                           final LocalDevice device) {
                LOG.log(Level.INFO, "Local device removed: {0}",
                        device.getDisplayString());
            }

            @Override
            public void beforeShutdown(final Registry registry) {
                LOG.log(Level.INFO, "Before shutdown, the registry has devices: {0}",
                        registry.getDevices().size());
            }

            @Override
            public void afterShutdown() {
                LOG.info("Shutdown of registry complete!");
            }
        };

        try {
            final UpnpService upnpService = new UpnpServiceImpl(listener);
            upnpService.getControlPoint().search(new STAllHeader());
            upnpService.shutdown();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error {0}", e.getMessage());
        }
    }

    /* do not let anyone instantiate this class */
    private Network() {
    }

    /**
     * Returns an instance of application talk interface.
     *
     * @return the application talk interface.
     */
    public static synchronized Network getNetwork() {
        return Network.INSTANCE;
    }
}
