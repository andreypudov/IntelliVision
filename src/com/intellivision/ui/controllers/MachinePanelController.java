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

package com.intellivision.ui.controllers;

import com.intellivision.ui.controls.MachinePanelEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class provides remote machine control functionality.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      MachinePanelController.java
 * %date      12:00:00 PM, Dec 19, 2012
 */
public class MachinePanelController implements Initializable {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
              com.intellivision.core.Manifest.NAME);

    @FXML private Label machineStausField;
    @FXML private Label machineStausDesc;

    @FXML private TextField machineNameField;
    @FXML private TextField machineAddressField;
    @FXML private TextField userNameField;
    @FXML private TextField userPasswordField;

    private final ObjectProperty<EventHandler<MachinePanelEvent>> onAction
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
        userNameField.addEventFilter(KeyEvent.KEY_TYPED,
                new EventHandler<KeyEvent>() {
            @Override
            public void handle(final KeyEvent event) {
                char[] array = event.getCharacter().toCharArray();
                char   letter  = array[array.length - 1];

                if (letter == ' ') {
                    event.consume();
                }
            }
        });
    }

    /**
     * Defines a function to be called when the remote machine is changed.
     *
     * @return the onAction property.
     */
    public ObjectProperty<EventHandler<MachinePanelEvent>> onAction() {
        return onAction;
    }

    /**
     * Sets remote machine name.
     *
     * @param name the remote machine name.
     */
    public void setName(final String name) {
        machineNameField.setText(name);
    }

    /**
     * Sets remote machine user name.
     *
     * @param username the remote machine user name.
     */
    public void setUserName(final String username) {
        userNameField.setText(username);
    }

    /**
     * Sets remote machine user password.
     *
     * @param password the remote machine user password.
     */
    public void setUserPassword(final String password) {
        userPasswordField.setText(password);
    }

    /**
     * Sets remote machine address.
     *
     * @param address the remote machine address.
     */
    public void setAddress(final String address) {
        machineAddressField.setText(address);
    }
}