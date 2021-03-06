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

import com.intellivision.ui.controls.MachineBar;
import com.intellivision.ui.controls.MachinePanel;
import com.intellivision.util.logs.Machine;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class provides the list of machines to scan.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      RemoteModuleController.java
 * %date      05:20:00 PM, Oct 28, 2012
 */
public class RemoteModuleController implements Initializable {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
              com.intellivision.core.Manifest.NAME);

    /* the remote machine list */
    private static final com.intellivision.util.pools.Machines MACHINES
            = com.intellivision.util.pools.Machines.getMachines();

    @FXML private Button remoteAddButton;
    @FXML private Button remoteRemoveButton;

    @FXML private ListView<MachineBar> remoteMachineList;
    @FXML private StackPane            remoteMainPane;

    @FXML private MachinePanel         remoteMachinePanel = new MachinePanel();

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
        remoteMachineList.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<MachineBar>() {
            @Override
            public void changed(final ObservableValue<? extends MachineBar> observable,
                    final MachineBar oldValue, final MachineBar newValue) {
                if (newValue instanceof MachineBar) {
                    remoteMachinePanel.setMachine(newValue.getMachine());
                }
            }
        });

        if (MACHINES.getList().isEmpty() == false) {
            for (Machine machine : MACHINES.getList()) {
                MachineBar bar = new MachineBar(machine);

                remoteMachineList.getItems().add(bar);
            }

            remoteMachineList.getSelectionModel().selectFirst();

            /* update configuration panel */
            remoteMainPane.getChildren().clear();
            remoteMainPane.getChildren().add(remoteMachinePanel);
        }
    }

    /**
     * Adds new machine item to the list of remote machines.
     *
     * @param event the event source.
     */
    @FXML
    private void remoteAddButtonOnAction(final ActionEvent event) {
        Machine    machine = new Machine("Remote Machine", "", "", "");
        MachineBar bar     = new MachineBar(machine);

        MACHINES.add(machine);

        remoteMachineList.getItems().add(bar);
        remoteMachineList.getSelectionModel().select(
                remoteMachineList.getItems().size() - 1);

        /* update configuration panel */
        remoteMainPane.getChildren().clear();
        remoteMainPane.getChildren().add(remoteMachinePanel);
    }

    /**
     * Remove selected machine item from the list of remote machines.
     *
     * @param event the event source.
     */
    @FXML
    private void remoteRemoveButtonOnAction(final ActionEvent event) {
        int index = remoteMachineList.getSelectionModel().getSelectedIndex();

        /* bounds validation */
        if ((index < 0) || (index >= remoteMachineList.getItems().size())) {
            return;
        }

        remoteMachineList.getSelectionModel().select(
                (index == 0) ? 0 : index - 1);
        MACHINES.remove(remoteMachineList.getItems().get(index).getMachine());
        remoteMachineList.getItems().remove(index);

        /* update configuration panel */
        if (remoteMachineList.getItems().size() == 0) {
            remoteMainPane.getChildren().clear();
        }
    }

    /**
     * Search for the pattern over the module content.
     *
     * @param pattern the pattern value for the search.
     */
    public void search(String pattern) {
        for (MachineBar machineBar : remoteMachineList.getItems()) {
            final boolean visible = (machineBar.getMachine().getName(
                    ).toLowerCase().contains(pattern.toLowerCase()));
            machineBar.setVisible(visible);
        }

        remoteMachineList.getSelectionModel().selectFirst();
    }
}