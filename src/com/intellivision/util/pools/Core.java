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

package com.intellivision.util.pools;

import com.intellivision.util.ConsoleFormatter;
import com.intellivision.util.StatusCodes;
import java.io.IOException;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.XMLFormatter;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * The general application pool.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Core.java
 * %date      08:40:00 PM, Oct 12, 2012
 */
public class Core {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger("IntelliVision");

    /* the list of application properties */
    private static final com.intellivision.util.Settings SETTINGS
            = com.intellivision.util.Settings.getSettings();

    static {
        try {
            /* Initialize general application logging */

            /*
             * "/" the local pathname separator
             * "%t" the system temporary directory
             * "%h" the value of the "user.home" system property
             * "%g" the generation number to distinguish rotated logs
             * "%u" a unique number to resolve conflicts
             * "%%" translates to a single percent sign "%"
             */

            DateFormat formatter = new SimpleDateFormat("HH-mm-ss dd-MMM-yyyy");

            /* the hostname of the machine */
            InetAddress localMachine = InetAddress.getLocalHost();

            /* the pattern for naming the output file */
            String pattern = "%t/intellivision."
                    + System.getProperty("user.name")  + "."
                    + localMachine.getHostName()       + "."
                    + System.getProperty("os.name")    + "."
                    + System.getProperty("os.version") + "."
                    + System.getProperty("os.arch")    + "."
                    + formatter.format(new Date())     + "."
                    + "%g.log";

            /* specify append mode to disabled */
            boolean append = false;

            FileHandler    fileHandler    = new FileHandler(pattern, append);
            ConsoleHandler consoleHandler = new ConsoleHandler();

            fileHandler.setFormatter(new XMLFormatter());
            consoleHandler.setFormatter(new ConsoleFormatter());

            LOG.addHandler(fileHandler);
            LOG.addHandler(consoleHandler);

            LOG.setUseParentHandlers(false);
        } catch (IOException e) {
            LOG.severe(e.getMessage());
            System.exit(StatusCodes.EXIT_FAILURE);
        }
    }

    /* do not let anyone instantiate this class */
    private Core() {
    }

    /* the primary stage for the application */
    private static Stage primaryStage;

    /**
     * Returns the primary stage of the application.
     *
     * @return the primary stage of the application.
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Initialize the primary stage of the application with user settings.
     *
     * @param stage the primary stage of the application.
     * @param scene specify the scene to be used on this stage.
     *
     * @throws IOException
     *        if an I/O exception occurs.
     * @throws IllegalArgumentException
     *        method has been passed an illegal or inappropriate argument
     */
    public static void setPrimaryStage(Stage stage, Scene scene)
            throws IOException {
        if ((stage == null) || (scene == null)) {
            throw new IllegalArgumentException();
        }

        primaryStage = stage;
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("IntelliVision");
        primaryStage.getIcons().add(new Image(Core.class.getResource(
                "/com/intellivision/resources/images/IntelliVision.png"
            ).openStream()));
        primaryStage.setScene(scene);

        /* set window size */
        primaryStage.setWidth(
                Double.parseDouble(SETTINGS.getValue(
                "intellivision.window.width")));
        primaryStage.setHeight(
                Double.parseDouble(SETTINGS.getValue(
                "intellivision.window.height")));

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                SETTINGS.setValue("intellivision.window.width",
                        Double.toString(primaryStage.getWidth()));
                SETTINGS.setValue("intellivision.window.height",
                        Double.toString(primaryStage.getHeight()));
                SETTINGS.save();

                primaryStage.close();
                event.consume();
            }
        });
    }

}
