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

package com.intellivision.ui.controls;

import com.intellivision.ui.controllers.WindowButtonsController;
import com.intellivision.util.StatusCodes;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * Window buttons panel for window close, minimize and maximize.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      WindowButtons.java
 * %date      08:00:00 PM, Aug 31, 2012
 */
public class WindowButtons extends HBox {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger("IntelliVision");

    /* loads an object hierarchy from an XML document */
    private final FXMLLoader fxmlLoader;

    /* controller initialization interface */
    private final WindowButtonsController controller;

    public WindowButtons() {
        fxmlLoader = new FXMLLoader(getClass().getResource(
                "/com/intellivision/resources/schemas/WindowButtons.fxml"));

        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (java.io.IOException e) {
            LOG.severe(e.getMessage());
            System.exit(StatusCodes.EXIT_FAILURE);
        }

        controller = fxmlLoader.getController();
    }

    /**
     * Sets the value of the property onCloseAction.
     *
     * @param handler the button's action, which is invoked whenever the button
     *                is fired. This may be due to the user clicking on the
     *                button with the mouse, or by a touch event, or by a key
     *                press, or if the developer programmatically invokes
     *                the fire() method.
     */
    public void setOnCloseAction(EventHandler<ActionEvent> handler) {
        controller.onCloseAction().setValue(handler);
    }

    /**
     * Sets the value of the property onMinimizeAction.
     *
     * @param handler the button's action, which is invoked whenever the button
     *                is fired. This may be due to the user clicking on the
     *                button with the mouse, or by a touch event, or by a key
     *                press, or if the developer programmatically invokes
     *                the fire() method.
     */
    public void setOnMinimizeAction(EventHandler<ActionEvent> handler) {
        controller.onMinimizeAction().setValue(handler);
    }

    /**
     * Sets the value of the property onMaximizeAction.
     *
     * @param handler the button's action, which is invoked whenever the button
     *                is fired. This may be due to the user clicking on the
     *                button with the mouse, or by a touch event, or by a key
     *                press, or if the developer programmatically invokes
     *                the fire() method.
     */
    public void setOnMaximizeAction(EventHandler<ActionEvent> handler) {
        controller.onMaximizeAction().setValue(handler);
    }
}
