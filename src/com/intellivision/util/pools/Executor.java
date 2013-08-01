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

package com.intellivision.util.pools;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * The job execution pool provides ability to schedule a task to run after a
 * given delay, or to execute periodically.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Executor.java
 * %date      10:10:00 PM, Jul 31, 2013
 */
public enum Executor {

    INSTANCE;

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellivision.core.Manifest.NAME);

    /* he number of threads to keep in the pool, even if they are idle */
    private static final int CORE_POOL_ZISE = 16;

    /* thread pool that can schedule commands */
    private static final ScheduledExecutorService service
            = Executors.newScheduledThreadPool(CORE_POOL_ZISE);

    /* do not let anyone instantiate this class */
    private Executor() {
    }

    /**
     * Returns an instance of job executor.
     *
     * @return the job execution pool.
     */
    public static synchronized Executor getExecutor() {
        return Executor.INSTANCE;
    }

    /**
     * Schedule a job execution.
     *
     * @param task   the task to execute.
     * @param delaey the time from now to delay execution unit.
     * @param units  the time unit of the delay parameter.
     */
    public synchronized void scheduleTask(Runnable task,
            long delaey, TimeUnit units) {
        service.schedule(task, delaey, units);
    }

    /**
     * Schedule a periodically executing job.
     *
     * @param task   the task to execute.
     * @param delay  the time to delay first execution.
     * @param period the period between successive executions.
     * @param units  the time unit of the initialDelay and period parameters.
     */
    public void schedulePeriodicTask(Runnable task,
            long delay, long period, TimeUnit units) {
        /*
         * Executes a periodic action that becomes enabled first after the given
         * initial delay, and subsequently with the given delay between the
         * termination of one execution and the commencement of the next.
         */
        service.scheduleWithFixedDelay(task, delay, period, units);
    }

    /**
     * Initiates an orderly shutdown in which previously submitted tasks are
     * executed, but no new tasks will be accepted. Invocation has no additional
     * effect if already shut down.
     */
    public void shutdown() {
        try {
            /* initiates an orderly shutdown */
            service.shutdown();

            /* ait until all threads are finish */
            service.awaitTermination(1000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            LOG.warning(e.getMessage());
        }
    }
}
