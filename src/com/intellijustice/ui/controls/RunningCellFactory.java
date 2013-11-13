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

package com.intellijustice.ui.controls;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.util.Callback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * TableView cell factory provides designed cell instances.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      RunningCellFactory.java
 * %date      08:00:00 PM, Nov 08, 2013
 */
public class RunningCellFactory {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellijustice.core.Manifest.NAME);

    /* do not let anyone instantiate this class */
    private RunningCellFactory() {
    }

    /**
     * Returns cell factory for central aligned short number.
     *
     * @return the cell factory for central aligned short number.
     */
    public static Callback<TableColumn, TableCell> centralAlignedShort() {
        return new Callback<TableColumn, TableCell>() {
            public TableCell call(final TableColumn column) {
                final TableCell cell = new TableCell<RunningDataModel, Short>() {
                    @Override
                    public void updateItem(final Short item, final boolean empty) {
                        super.updateItem(item, empty);

                        setText(empty
                                ? null
                                : getString());
                        setGraphic(null);
                    }

                    private String getString() {
                        return (getItem() == null)
                                ? ""
                                : getItem().toString();
                    }
                };

                cell.setAlignment(Pos.TOP_CENTER);

                return cell;
            }
        };
    }

    /**
     * Returns cell factory for right aligned short number.
     *
     * @return the cell factory for right aligned short number.
     */
    public static Callback<TableColumn, TableCell> rightAlignedShort() {
        return new Callback<TableColumn, TableCell>() {
            public TableCell call(final TableColumn column) {
                final TableCell cell = new TableCell<RunningDataModel, Short>() {
                    @Override
                    public void updateItem(final Short item, final boolean empty) {
                        super.updateItem(item, empty);

                        setText(empty
                                ? null
                                : getString());
                        setGraphic(null);
                    }

                    private String getString() {
                        return (getItem() == null)
                                ? ""
                                : getItem().toString();
                    }
                };

                cell.setAlignment(Pos.TOP_RIGHT);

                return cell;
            }
        };
    }

    /**
     * Returns cell factory for right aligned integer number.
     *
     * @return the cell factory for right aligned integer number.
     */
    public static Callback<TableColumn, TableCell> rightAlignedInteger() {
        return new Callback<TableColumn, TableCell>() {
            public TableCell call(final TableColumn column) {
                final TableCell cell = new TableCell<RunningDataModel, Integer>() {
                    @Override
                    public void updateItem(final Integer item, final boolean empty) {
                        super.updateItem(item, empty);

                        setText(empty
                                ? null
                                : getString());
                        setGraphic(null);
                    }

                    private String getString() {
                        return (getItem() == null)
                                ? ""
                                : getItem().toString();
                    }
                };

                cell.setAlignment(Pos.TOP_RIGHT);

                return cell;
            }
        };
    }

    /**
     * Returns cell factory for right aligned date.
     *
     * @return the cell factory for right aligned date.
     */
    public static Callback<TableColumn, TableCell> rightAlignedDate() {
        return new Callback<TableColumn, TableCell>() {
            public TableCell call(final TableColumn column) {
                final TableCell cell = new TableCell<RunningDataModel, Long>() {
                    @Override
                    public void updateItem(final Long item, final boolean empty) {
                        super.updateItem(item, empty);

                        setText(empty
                                ? null
                                : getString());
                        setGraphic(null);
                    }

                    private String getString() {
                        final DateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                        return (getItem() == null)
                                ? ""
                                : format.format(new Date(this.getItem()));
                    }
                };

                cell.setAlignment(Pos.TOP_RIGHT);

                return cell;
            }
        };
    }

    /**
     * Returns cell factory for right aligned integer date.
     *
     * @return the cell factory for right integer aligned date.
     */
    public static Callback<TableColumn, TableCell> rightAlignedIntegerDate() {
        return new Callback<TableColumn, TableCell>() {
            public TableCell call(final TableColumn column) {
                final TableCell cell = new TableCell<RunningDataModel, Integer>() {
                    @Override
                    public void updateItem(final Integer item, final boolean empty) {
                        super.updateItem(item, empty);

                        setText(empty
                                ? null
                                : getString());
                        setGraphic(null);
                    }

                    private String getString() {
                        final long ONE_MINUTE = 60 * 1000;
                        final long ONE_HOUR   = 60 * ONE_MINUTE;

                        final Date       date     = new Date(getItem());
                        final Calendar   calendar = new GregorianCalendar();
                        final DateFormat format;

                        calendar.setTime(date);

                        long time = date.getTime() - new Date(0L).getTime();
                        if (time < ONE_MINUTE) {
                            format = new SimpleDateFormat("s.SS");
                        } else if (time < ONE_HOUR) {
                            format = new SimpleDateFormat("m:ss.SS");
                        } else if (calendar.get(Calendar.MILLISECOND) == 0) {
                            format = new SimpleDateFormat("h:mm:ss");
                        } else {
                            format = new SimpleDateFormat("h:mm:ss.SS");
                        }

                        return (getItem() == null) ? "" : format.format(date);
                    }
                };

                cell.setAlignment(Pos.TOP_RIGHT);

                return cell;
            }
        };
    }

    /**
     * Returns cell factory for right aligned integer date.
     *
     * @return the cell factory for right integer aligned date.
     */
    public static Callback<TableColumn, TableCell> centerAlignedImage() {
        return new Callback<TableColumn, TableCell>() {
            public TableCell call(final TableColumn column) {
                final TableCell cell = new TableCell<RunningDataModel, String>() {
                    @Override
                    public void updateItem(final String item, final boolean empty) {
                        super.updateItem(item, empty);

                        if (item != null) {
                            final Image image = new Image(getClass(
                                ).getResourceAsStream(
                                    "/com/intellijustice/resources/images/flags/"
                                    + getString() + ".gif"));

                            final HBox      box   = new HBox();
                            final Label     label = new Label(getString());
                            final ImageView view  = new ImageView(image);
                            final Region    space = new Region();

                            space.setPrefWidth(4.0);
                            box.setPadding(new Insets(4.0, 0.0, 0.0, 0.0));
                            box.setAlignment(Pos.TOP_CENTER);
                            box.getChildren().addAll(view, space, label);

                            setGraphic(box);
                        }
                    }

                    private String getString() {
                        return (getItem() == null)
                                ? ""
                                : getItem().toString();
                    }
                };

                cell.setAlignment(Pos.TOP_CENTER);

                return cell;
            }
        };
    }
}
