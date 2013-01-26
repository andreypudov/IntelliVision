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

import com.intellivision.util.logs.Machine;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    private Machine machine;

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
        /*
         * The series of user input filetr handlers.
         */
        machineNameField.addEventFilter(KeyEvent.KEY_TYPED,
                new EventHandler<KeyEvent>() {
            @Override
            public void handle(final KeyEvent event) {
                char[] array  = event.getCharacter().toCharArray();
                char   letter = array[array.length - 1];

                /* XML invalid characters */
                if ((letter == '&') || (letter == '<') || (letter == '>')
                        || (letter == '\'') || (letter == '\"')) {
                    event.consume();
                }
            }
        });

        machineAddressField.addEventFilter(KeyEvent.KEY_TYPED,
                new EventHandler<KeyEvent>() {
            @Override
            public void handle(final KeyEvent event) {
                char[] array  = event.getCharacter().toCharArray();
                char   letter = array[array.length - 1];

                /* only alphabet and digit letters, or numbers */
                if ((Character.isLetterOrDigit(letter) == false)
                        && (letter != '-') && (letter != '.')
                        && (letter != ':')) {
                    event.consume();
                }
            }
        });

        userNameField.addEventFilter(KeyEvent.KEY_TYPED,
                new EventHandler<KeyEvent>() {
            @Override
            public void handle(final KeyEvent event) {
                char[] array  = event.getCharacter().toCharArray();
                char   letter = array[array.length - 1];

                /* XML invalid characters and space */
                if ((letter == ' ') || (letter == '&') || (letter == '<')
                        || (letter == '>') || (letter == '\'')
                        || (letter == '\"')) {
                    event.consume();
                }
            }
        });

        userPasswordField.addEventFilter(KeyEvent.KEY_TYPED,
                new EventHandler<KeyEvent>() {
            @Override
            public void handle(final KeyEvent event) {
                char[] array  = event.getCharacter().toCharArray();
                char   letter = array[array.length - 1];

                /* XML invalid characters and space */
                if ((letter == ' ') || (letter == '&') || (letter == '<')
                        || (letter == '>') || (letter == '\'')
                        || (letter == '\"')) {
                    event.consume();
                }
            }
        });

        /*
         * The series of remote machine property handlers.
         */
        machineNameField.textProperty().addListener(
                new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov,
                                final String t, final String t1) {
                machine.setName(t1);
            }
        });

        userNameField.textProperty().addListener(
                new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov,
                                final String t, final String t1) {
                machine.setUserName(t1);
            }
        });

        userPasswordField.textProperty().addListener(
                new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov,
                                final String t, final String t1) {
                machine.setUserPassword(t1);
            }
        });

        machineAddressField.textProperty().addListener(
                new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov,
                                final String t, final String t1) {
                machine.setAddress(t1);
            }
        });
    }

    /**
     * Returns remote machine instance.
     *
     * @return the remote machine instance.
     */
    public Machine getMachine() {
        return machine;
    }

    /**
     * Sets remote machine instance.
     *
     * @param machine the remote machine instance.
     */
    public void setMachine(final Machine machine) {
        this.machine = machine;

        machineNameField.setText(machine.getName());
        userNameField.setText(machine.getUserName());
        userPasswordField.setText(machine.getUserPassword());
        machineAddressField.setText(machine.getAddress());
    }
}
