/*
 * IntelliJustice Intelligent Referee Assistant System
 *
 * The MIT License
 *
 * Copyright 2011-2014 Andrey Pudov.
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

package com.intellijustice.ui.controllers;

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
 * FXML Controller class provides server machine control functionality.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      ServerPanelController.java
 * %date      05:00:00 PM, Jul 30, 2013
 */
public class ServerPanelController implements Initializable {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
              com.intellijustice.core.Manifest.NAME);

    @FXML private Label machineStausField;
    @FXML private Label machineStausDesc;

    @FXML private TextField machineNameField;
    @FXML private TextField machineAddressField;
    @FXML private TextField userNameField;
    @FXML private TextField userPasswordField;

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
        machineNameField.addEventFilter(KeyEvent.KEY_TYPED, (final KeyEvent event) -> {
            char[] array  = event.getCharacter().toCharArray();
            char   letter = array[array.length - 1];
            
            /* XML invalid characters */
            if ((letter == '&') || (letter == '<') || (letter == '>')
                    || (letter == '\'') || (letter == '\"')) {
                event.consume();
            }
        });

        machineAddressField.addEventFilter(KeyEvent.KEY_TYPED, (final KeyEvent event) -> {
            char[] array  = event.getCharacter().toCharArray();
            char   letter = array[array.length - 1];
            
            /* only alphabet and digit letters, or numbers */
            if ((Character.isLetterOrDigit(letter) == false)
                    && (letter != '-') && (letter != '.')
                    && (letter != ':')) {
                event.consume();
            }
        });

        userNameField.addEventFilter(KeyEvent.KEY_TYPED, (final KeyEvent event) -> {
            char[] array  = event.getCharacter().toCharArray();
            char   letter = array[array.length - 1];
            
            /* XML invalid characters and space */
            if ((letter == ' ') || (letter == '&') || (letter == '<')
                    || (letter == '>') || (letter == '\'')
                    || (letter == '\"')) {
                event.consume();
            }
        });

        userPasswordField.addEventFilter(KeyEvent.KEY_TYPED, (final KeyEvent event) -> {
            char[] array  = event.getCharacter().toCharArray();
            char   letter = array[array.length - 1];
            
            /* XML invalid characters and space */
            if ((letter == ' ') || (letter == '&') || (letter == '<')
                    || (letter == '>') || (letter == '\'')
                    || (letter == '\"')) {
                event.consume();
            }
        });

        /*
         * The series of remote machine property handlers.
         */
        machineNameField.textProperty().addListener(
                (final ObservableValue<? extends String> ov, final String t, final String t1) -> {
            //machine.setName(t1);
        });

        userNameField.textProperty().addListener(
                (final ObservableValue<? extends String> ov, final String t, final String t1) -> {
            //machine.setUserName(t1);
        });

        userPasswordField.textProperty().addListener(
                (final ObservableValue<? extends String> ov, final String t, final String t1) -> {
            //machine.setUserPassword(t1);
        });

        machineAddressField.textProperty().addListener(
                (final ObservableValue<? extends String> ov, final String t, final String t1) -> {
            //machine.setAddress(t1);
        });
    }
}