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

import com.intellivision.ui.modules.HelpModule;
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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
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
            = java.util.logging.Logger.getLogger(
            com.intellivision.core.Manifest.NAME);

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

            final DateFormat formatter = new SimpleDateFormat(
                    "HH-mm-ss dd-MMM-yyyy");

            /* the hostname of the machine */
            final InetAddress localMachine = InetAddress.getLocalHost();

            /* the pattern for naming the output file */
           final String pattern = "%t/intellivision."
                   + System.getProperty("user.name")  + "."
                   + localMachine.getHostName()       + "."
                   + System.getProperty("os.name")    + "."
                   + System.getProperty("os.version") + "."
                   + System.getProperty("os.arch")    + "."
                   + formatter.format(new Date())     + "."
                   + "%g.log";

            /* specify append mode to disabled */
            final boolean append = false;

            final ConsoleHandler ttyHandler  = new ConsoleHandler();
            final FileHandler    fileHandler = new FileHandler(pattern, append);

            ttyHandler.setFormatter(new ConsoleFormatter());
            fileHandler.setFormatter(new XMLFormatter());

            LOG.addHandler(ttyHandler);
            LOG.addHandler(fileHandler);

            LOG.setUseParentHandlers(false);
        } catch (IOException e) {
            LOG.severe(e.getMessage());
            System.exit(StatusCodes.EXIT_FAILURE);
        }

        LOG.info("IntelliVision Intelligence Image Processing System\n"
                + "Copyright (C) 2011-2013 Andrey Pudov. "
                + "All rights reserved.\n");

        /* adds first-level module to the appliation window */
        Modules.addModule(HelpModule.getInstance());
    }

    /* the list of application properties */
    private static final com.intellivision.util.pools.Settings SETTINGS
            = com.intellivision.util.pools.Settings.getSettings();

    /* do not let anyone instantiate this class */
    private Core() {
    }

    /* the primary stage for the application */
    private static Stage primaryStage;

    /* the primary scene for the stage */
    private static Scene primaryScene;

    /* the primary panel for the scene */
    private static Node  primaryPanel;

    /* window maximizing stuff */
    private static Rectangle2D windowBounds = null;
    private static boolean     maximized    = false;

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
    public static void setPrimaryStage(final Stage stage, final Scene scene)
            throws IOException {
        if ((stage == null) || (scene == null)) {
            throw new IllegalArgumentException();
        }

        primaryScene = scene;
        primaryPanel = scene.lookup("#mainPanel");

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

        primaryStage.fullScreenProperty().addListener(
                new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> ov,
                                Boolean t, Boolean t1) {
                if (ov.getValue()) {
                    AnchorPane.setBottomAnchor(primaryPanel, 0.0);
                    AnchorPane.setLeftAnchor(primaryPanel,   0.0);
                    AnchorPane.setRightAnchor(primaryPanel,  0.0);
                    AnchorPane.setTopAnchor(primaryPanel,    0.0);

                    primaryScene.getRoot().setStyle("-fx-effect: null");
                } else {
                    AnchorPane.setBottomAnchor(primaryPanel, 32.0);
                    AnchorPane.setLeftAnchor(primaryPanel,   32.0);
                    AnchorPane.setRightAnchor(primaryPanel,  32.0);
                    AnchorPane.setTopAnchor(primaryPanel,    32.0);

                    primaryScene.getRoot().setStyle("");
                }
            }});

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                closeWindow();

                event.consume();
            }
        });
    }

    /**
     * Closes application window.
     */
    public static void closeWindow() {
        SETTINGS.setValue("intellivision.window.width",
                 Double.toString(primaryStage.getWidth()));
        SETTINGS.setValue("intellivision.window.height",
                Double.toString(primaryStage.getHeight()));
        SETTINGS.save();

        primaryStage.close();
    }

    /**
     * Returns the maximized state of the application window.
     *
     * @return true if window is maximized and false otherwise.
     */
    public static boolean isMaximized() {
        return maximized || primaryStage.isFullScreen();
    }

    /**
     * Maximizes application window.
     */
    public static void maximizeWindow() {
        final Screen screen  = Screen.getScreensForRectangle(
                primaryStage.getX(),
                primaryStage.getY(), 1, 1).get(0);

        if (maximized) {
            maximized = false;

            if (windowBounds != null) {
                primaryStage.setX(windowBounds.getMinX());
                primaryStage.setY(windowBounds.getMinY());
                primaryStage.setWidth(windowBounds.getWidth());
                primaryStage.setHeight(windowBounds.getHeight());
            }

            primaryScene.getRoot().setStyle(
                    "-fx-effect: dropshadow(gaussian,\n" +
                    "               derive(black, 24%), 26, 0.0, 0, 16)");

            AnchorPane.setBottomAnchor(primaryPanel, 32.0);
            AnchorPane.setLeftAnchor(primaryPanel,   32.0);
            AnchorPane.setRightAnchor(primaryPanel,  32.0);
            AnchorPane.setTopAnchor(primaryPanel,    32.0);
        } else {
            maximized = true;

            windowBounds = new Rectangle2D(
                    primaryStage.getX(),     primaryStage.getY(),
                    primaryStage.getWidth(), primaryStage.getHeight());

            primaryScene.getRoot().setStyle("-fx-effect: null");

            AnchorPane.setBottomAnchor(primaryPanel, 0.0);
            AnchorPane.setLeftAnchor(primaryPanel,   0.0);
            AnchorPane.setRightAnchor(primaryPanel,  0.0);
            AnchorPane.setTopAnchor(primaryPanel,    0.0);

            primaryStage.setX(screen.getVisualBounds().getMinX());
            primaryStage.setY(screen.getVisualBounds().getMinY());
            primaryStage.setWidth(screen.getVisualBounds().getWidth());
            primaryStage.setHeight(screen.getVisualBounds().getHeight());
        }
    }

    /**
     * Sets application window to full screen mode.
     */
    public static void maximizeWindowToScreen() {
        primaryStage.setFullScreen(!primaryStage.isFullScreen());
    }

    /**
     * Minimizes application window.
     */
    public static void minimizeWindow() {
        primaryStage.setIconified(true);
    }
}
