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

package com.intellivision;

import com.intellivision.util.StatusCodes;
import com.intellivision.util.pools.Core;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * The IntelliVision class initializes general program structures and builds
 * graphical user interface.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      IntelliVision.java
 * %date      07:30:00 PM, Aug 15, 2012
 */
public class IntelliVision extends Application {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger("IntelliVision");

    /**
     * The main entry point for all JavaFX applications. The start method is
     * called after the init method has returned, and after the system is ready
     * for the application to begin running.
     *
     * @param primaryStage  the primary stage for this application.
     */
    @Override
    public void start(final Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(
                    "/com/intellivision/resources/schemas/IntelliVision.fxml"));
            Scene scene = new Scene(root, Color.TRANSPARENT);
            scene.getStylesheets().add(getClass().getResource(
                        "/com/intellivision/resources/styles/IntelliVision.css"
                    ).toExternalForm());

            Core.setPrimaryStage(primaryStage, scene);
            Core.getPrimaryStage().show();
        } catch (java.io.IOException | IllegalArgumentException e) {
            LOG.severe(e.getMessage());
            System.exit(StatusCodes.EXIT_FAILURE);
        }
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        launch(args);
    }
}
