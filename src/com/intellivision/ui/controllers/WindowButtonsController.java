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

import com.intellivision.util.pools.Core;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class provides window closing, minimizing and maximizing.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      WindowButtonsController.java
 * %date      07:50:00 AM, Sep 06, 2012
 */
public class WindowButtonsController implements Initializable {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger("IntelliVision");

    @FXML private HBox   windowButtons;

    @FXML private Button closeButton;    /* red */
    @FXML private Button minimizeButton; /* yellow */
    @FXML private Button maximizeButton; /* green */

    private final ObjectProperty<EventHandler<ActionEvent>> onCloseAction
            = new SimpleObjectProperty<>();
    private final ObjectProperty<EventHandler<ActionEvent>> onMinimizeAction
            = new SimpleObjectProperty<>();
    private final ObjectProperty<EventHandler<ActionEvent>> onMaximizeAction
            = new SimpleObjectProperty<>();

    /**
     * Initializes the controller class.
     *
     * @param url the location used to resolve relative paths for the root
     *            object, or null if the location is not known.
     * @param rb  the resources used to localize the root object, or null if the
     *            root object was not localized.
     */
    @Override
    public void initialize(final URL url, final ResourceBundle rb) {
    }

    /**
     * Defines a function to be called when the close button is fired.
     *
     * @return the onCloseAction property.
     */
    public ObjectProperty<EventHandler<ActionEvent>> onCloseAction() {
        return onCloseAction;
    }

    /**
     * Defines a function to be called when the minimize button is fired.
     *
     * @return the onCloseAction property.
     */
    public ObjectProperty<EventHandler<ActionEvent>> onMinimizeAction() {
        return onMinimizeAction;
    }

    /**
     * Defines a function to be called when the maximize button is fired.
     *
     * @return the onCloseAction property.
     */
    public ObjectProperty<EventHandler<ActionEvent>> onMaximizeAction() {
        return onMaximizeAction;
    }

    /**
     * Closes the application window and returns management to OS.
     *
     * @param event the event source.
     */
    @FXML
    private void windowButtonsCloseOnAction(final ActionEvent event) {
        onCloseAction.get().handle(event);
    }

    /**
     * Minimizes the application window to system task bar.
     *
     * @param event the event source.
     */
    @FXML
    private void windowButtonsMinimizeOnAction(final ActionEvent event) {
        onMinimizeAction.get().handle(event);
    }

    /**
     * Maximizes the application window.
     *
     * @param event the event source.
     */
    @FXML
    private void windowButtonsMaximizeOnAction(final ActionEvent event) {
        onMaximizeAction.get().handle(event);
    }
}
