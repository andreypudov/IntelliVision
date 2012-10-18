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

package com.intellivision.ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;

/**
 * FXML Controller class provides module selection functionality.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      ModuleBarController.java
 * %date      02:00:00 PM, Oct 16, 2012
 */
public class ModuleBarController implements Initializable {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger("IntelliVision");

    @FXML private ToggleButton moduleBarHome;
    @FXML private ToggleButton moduleBarCategories;
    @FXML private ToggleButton moduleBarRemote;
    @FXML private ToggleButton moduleBarHelp;

    /**
     * Initializes the controller class.
     *
     * @param url the location used to resolve relative paths for the root
     *            object, or null if the location is not known.
     * @param rb  the resources used to localize the root object, or null if the
     *            root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * Changes current application module to Home.
     *
     * @param event the event source.
     */
    public void moduleBarHomeOnAction(ActionEvent event) {
    }

    /**
     * Changes current application module to Categories.
     *
     * @param event the event source.
     */
    public void moduleBarCategoriesOnAction(ActionEvent event) {
    }

    /**
     * Changes current application module to Remote.
     *
     * @param event the event source.
     */
    public void moduleBarRemoteOnAction(ActionEvent event) {
    }

    /**
     * Changes current application module to Help.
     *
     * @param event the event source.
     */
    public void moduleBarHelpOnAction(ActionEvent event) {
    }

}
