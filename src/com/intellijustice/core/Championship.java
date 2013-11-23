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

package com.intellijustice.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * The championship entity representation.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Championship.java
 * %date      04:30:00 PM, Aug 14, 2013
 */
public class Championship {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellijustice.core.Manifest.NAME);

    /* the list of competitions */
    private final List<Competition> list;

    private final int    id;
    private final String name;
    private final String country;
    private final String city;
    private final Format format;

    /**
     * Constructs new championship object.
     *
     * @param id      the identification number for the championship.
     * @param name    the name of the championship.
     * @param country the country of the competition.
     * @param city    the city of the championship.
     * @param format  the format of the championship (indoor, outdoor).
     */
    public Championship(final int id, final String name, final String city,
            final String country, final Format format) {
        this.list    = new ArrayList<>(16);

        this.id      = id;
        this.name    = name;
        this.country = country;
        this.city    = city;
        this.format  = format;
    }

    /**
     * Returns the identification number for the championship.
     *
     * @return the identification number for the championship.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the name of the championship.
     *
     * @return the name of the championship.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the country of the championship.
     *
     * @return the country of the championship.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Returns the city of the championship.
     *
     * @return the city of the championship.
     */
    public String getCity() {
        return city;
    }

    /**
     * Returns the format of the championship.
     *
     * @return the format of the championship (indoor, outdoor).
     */
    public Format getFormat() {
        return format;
    }

    /**
     * Adds competition representation to the list.
     *
     * @param competition the competition representation.
     */
    public void addCompetition(final Competition competition) {
        list.add(competition);
    }

    /**
     * Return the list of championship events.
     *
     * @return the competition list.
     */
    public List<Competition> getCompetitionList() {
        return Collections.unmodifiableList(list);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj the reference object with which to compare.
     * @return    true if this object is the same as the obj argument;
     *            false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        final Championship other = (Championship) obj;
        if (!Objects.equals(this.list, other.list)) {
            return false;
        }

        if (this.id != other.id) {
            return false;
        }

        if (!Objects.equals(this.name, other.name)) {
            return false;
        }

        if (!Objects.equals(this.country, other.country)) {
            return false;
        }

        if (!Objects.equals(this.city, other.city)) {
            return false;
        }

        if (this.format != other.format) {
            return false;
        }

        return true;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        /*int hash = 7;

        hash = 47 * hash + Objects.hashCode(this.list);
        hash = 47 * hash + this.id;
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + Objects.hashCode(this.country);
        hash = 47 * hash + Objects.hashCode(this.city);
        hash = 47 * hash + Objects.hashCode(this.format);*/

        return this.id;
    }


    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(128);

        builder.append(name).append(" "
                ).append(country).append(" "
                ).append(city).append(" "
                ).append(format);

        return builder.toString();
    }
}
