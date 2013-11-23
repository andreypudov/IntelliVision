/*
 * IntelliJustice Intelligent Referee Assistant System 
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

package com.intellijustice.ui.controllers;

import com.intellijustice.ui.modules.SearchEventHandler;
import com.intellijustice.ui.modules.SearchEventListener;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;

/**
 * FXML Controller class provides search functionality.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      SearchBarController.java
 * %date      09:50:00 AM, Sep 25, 2012
 */
public class SearchBarController implements Initializable {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
              com.intellijustice.core.Manifest.NAME);

    @FXML private Region    searchBar;
    @FXML private TextField searchTextField;
    @FXML private Button    searchClearButton;

    private final SearchEventHandler handler = new SearchEventHandler();

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
        searchBar.needsLayoutProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> ov,
                                final Boolean t, final Boolean t1) {
                searchTextField.resize(
                        searchBar.getWidth(), searchBar.getHeight());
                searchClearButton.resizeRelocate(
                        searchBar.getWidth() - 18, 6, 12, 13);
            }
        });

        searchTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov,
                                final String t, final String t1) {
                searchClearButton.setVisible(
                        searchTextField.getText().length() != 0);

                handler.fireEvent(searchTextField.getText());
            }
        });
    }

    /**
     * Adds search event listener.
     *
     * @param listener the search action event listener.
     */
    public void addSearchListener(final SearchEventListener listener) {
        handler.addEventListener(listener);
    }

    /**
     * Returns the search pattern.
     *
     * @return the search pattern.
     */
    public String getPattern() {
        return searchTextField.getText();
    }

    /**
     * Clears text field and returns to default state.
     *
     * @param actionEvent  the event source.
     */
    @FXML
    private void searchClearButtonAction(final ActionEvent actionEvent) {
        searchTextField.setText("");
        searchTextField.requestFocus();
    }

    /**
     * Clears text field when the user starts typing.
     *
     * @param actionEvent  the event source.
     */
    @FXML
    private void searchTextFieldAction(final ActionEvent actionEvent) {
        searchTextField.setText("");
        searchTextField.requestFocus();
    }

    /**
     *
     * @param keyEvent  the event source.
     */
    @FXML
    private void searchTextFieldKeyReleased(final KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.DOWN) {
            //contextMenu.setFocused(true);
        }
    }
}
