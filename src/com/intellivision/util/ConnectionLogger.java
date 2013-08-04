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

package com.intellivision.util;

import com.mysql.jdbc.Util;
import com.mysql.jdbc.log.Log;
import com.mysql.jdbc.log.LogUtils;
import com.mysql.jdbc.profiler.ProfilerEvent;
import java.util.logging.Level;

/**
 * An intermediate layer between application and MySQL loggers.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      ConnectionLogger.java
 * %date      06:50:00 PM, Aug 01, 2013
 */
public class ConnectionLogger implements Log {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
              com.intellivision.core.Manifest.NAME);

    /**
     * Java Logger level correlations.
     *
     * SEVERE  logFatal (highest value)
     * WARNING logError logWarn
     * INFO    logInfo
     * CONFIG  logDebug
     * FINE
     * FINER
     * FINEST (lowest value)
     */

    /**
     * Constructs logger object.
     *
     * @param name the name of the configuration to use.
     */
    public ConnectionLogger(final String name) {
    }

    @Override
    public boolean isDebugEnabled() {
        return true;
    }

    @Override
    public boolean isErrorEnabled() {
        return true;
    }

    @Override
    public boolean isFatalEnabled() {
        return true;
    }

    @Override
    public boolean isInfoEnabled() {
        return true;
    }

    @Override
    public boolean isTraceEnabled() {
        return true;
    }

    @Override
    public boolean isWarnEnabled() {
        return true;
    }

    @Override
    public void logDebug(Object message) {
        if (message instanceof ProfilerEvent) {
            LOG.info(LogUtils.expandProfilerEventIfNecessary(
                        message).toString());
        }
    }

    @Override
    public void logDebug(Object message, Throwable exception) {
        if (message instanceof ProfilerEvent) {
            LOG.log(Level.INFO, "{0}\n{1}",
                    new Object[]{LogUtils.expandProfilerEventIfNecessary(
                        message).toString(),
                    Util.stackTraceToString(exception)});
        }
    }

    @Override
    public void logError(Object message) {
        if (message instanceof ProfilerEvent) {
            LOG.warning(LogUtils.expandProfilerEventIfNecessary(
                        message).toString());
        }
    }

    @Override
    public void logError(Object message, Throwable exception) {
        if (message instanceof ProfilerEvent) {
            LOG.log(Level.WARNING, "{0}\n{1}",
                    new Object[]{LogUtils.expandProfilerEventIfNecessary(
                        message).toString(),
                    Util.stackTraceToString(exception)});
        }
    }

    @Override
    public void logFatal(Object message) {
        if (message instanceof ProfilerEvent) {
            LOG.severe(LogUtils.expandProfilerEventIfNecessary(
                        message).toString());
        }
    }

    @Override
    public void logFatal(Object message, Throwable exception) {
        if (message instanceof ProfilerEvent) {
            LOG.log(Level.SEVERE, "{0}\n{1}",
                    new Object[]{LogUtils.expandProfilerEventIfNecessary(
                        message).toString(),
                    Util.stackTraceToString(exception)});
        }
    }

    @Override
    public void logInfo(Object message) {
        if (message instanceof ProfilerEvent) {
            LOG.info(LogUtils.expandProfilerEventIfNecessary(
                        message).toString());
        }
    }

    @Override
    public void logInfo(Object message, Throwable exception) {
        if (message instanceof ProfilerEvent) {
            LOG.log(Level.INFO, "{0}\n{1}",
                    new Object[]{LogUtils.expandProfilerEventIfNecessary(
                        message).toString(),
                    Util.stackTraceToString(exception)});
        }
    }

    @Override
    public void logTrace(Object message) {
        if (message instanceof ProfilerEvent) {
            LOG.warning(LogUtils.expandProfilerEventIfNecessary(
                        message).toString());
        }
    }

    @Override
    public void logTrace(Object message, Throwable exception) {
        if (message instanceof ProfilerEvent) {
            LOG.log(Level.WARNING, "{0}\n{1}",
                    new Object[]{LogUtils.expandProfilerEventIfNecessary(
                        message).toString(),
                    Util.stackTraceToString(exception)});
        }
    }

    @Override
    public void logWarn(Object message) {
        if (message instanceof ProfilerEvent) {
            LOG.warning(LogUtils.expandProfilerEventIfNecessary(
                        message).toString());
        }
    }

    @Override
    public void logWarn(Object message, Throwable exception) {
        if (message instanceof ProfilerEvent) {
            LOG.log(Level.WARNING, "{0}\n{1}",
                    new Object[]{LogUtils.expandProfilerEventIfNecessary(
                        message).toString(),
                    Util.stackTraceToString(exception)});
        }
    }
}
