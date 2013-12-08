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

package com.intellijustice.util.pools;

import com.intellijustice.util.StatusCodes;
import java.util.logging.Level;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Scoreboard window representation to control scoreboard graphical interface.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      ScoreboardWindow.java
 * %date      01:15:00 PM, Dec 08, 2013
 */
public class ScoreboardWindow {
    
    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellijustice.core.Manifest.NAME);
    
    /* the list of application properties */
    private static final Settings SETTINGS = Settings.getSettings();
    
    /* the scoreboard stage for the application */
    private final Stage scoreboardStage = new Stage();
    
    /* the scoreboard visible property */
    private boolean visible = false;
    
    public ScoreboardWindow() {
        try {
            final Parent root = FXMLLoader.load(getClass().getResource(
                "/com/intellijustice/resources/schemas/Scoreboard.fxml"));
            final Scene scene = new Scene(root, Color.TRANSPARENT);

            scene.getStylesheets().add(getClass().getResource(
                    "/com/intellijustice/resources/styles/Scoreboard.css"
                ).toExternalForm());

            scoreboardStage.initStyle(StageStyle.TRANSPARENT);
            scoreboardStage.setTitle("IntelliJustice");
            scoreboardStage.getIcons().add(new Image(Core.class.getResource(
                    "/com/intellijustice/resources/images/IntelliJustice.png"
                ).openStream()));
            scoreboardStage.setScene(scene);

            /* set window size */
            scoreboardStage.setWidth(
                    Double.parseDouble(SETTINGS.getValue(
                    "intellijustice.scoreboard.width")));
            scoreboardStage.setHeight(
                    Double.parseDouble(SETTINGS.getValue(
                    "intellijustice.scoreboard.height")));
        } catch (java.io.IOException e) {
            LOG.log(Level.SEVERE, 
                    "Couldn''t constructs Scoreboard window. {0}", 
                    e.getMessage());
            System.exit(StatusCodes.EXIT_FAILURE);
        }
    }
    
    /**
     * Sets scoreboard window visible property.
     *
     * @param state the scoreboard visible property.
     */
    public void setVisible(final boolean state) {
        visible = state;
        
        if (visible) {
            scoreboardStage.show();
        } else {
            scoreboardStage.hide();
        }
    }
    
    /**
     *  Closes the scoreboard window.
     */
    public void close() {
        scoreboardStage.close();
    }
}