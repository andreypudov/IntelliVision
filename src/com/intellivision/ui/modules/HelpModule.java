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

package com.intellivision.ui.modules;

import com.intellivision.util.StatusCodes;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

/**
 * Brief description about application functionality, development team,
 * and application license.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      HelpModule.java
 * %date      07:00:00 PM, Oct 19, 2012
 */
public class HelpModule extends VBox implements AbstractModule {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
              com.intellivision.core.Manifest.NAME);

    /* the instance of the help module */
    private static volatile HelpModule INSTANCE;

    private HelpModule() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                    "/com/intellivision/resources/schemas/HelpModule.fxml"));

        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (java.io.IOException e) {
            LOG.severe(e.getMessage());
            System.exit(StatusCodes.EXIT_FAILURE);
        }
    }

    /**
     * Returns an instance of help module.
     *
     * @return the instance of this module.
     */
    public static HelpModule getInstance() {
        if (INSTANCE == null) {
            synchronized (HelpModule.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HelpModule();
                }
            }
        }

        return INSTANCE;
    }

    /**
     * Search for the pattern over the module content.
     *
     * @param pattern the pattern value for the search.
     */
    @Override
    public void search(String pattern) {
        /* */
    }
}
