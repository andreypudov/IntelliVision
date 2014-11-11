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

package com.intellijustice.core;

import com.intellijustice.iso.CountryCodes;

/**
 * The geo place entity representation.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Place.java
 * %date      07:50:00 AM, Nov 09, 2014
 */
public class Place {
    
    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellijustice.core.Manifest.NAME);
    
    private final long id;   /* negative value represents no database id */
    private final int  code;
    
    private final String country;
    private final String region;
    private final String city;
    
    /**
     * Constructs new place object.
     * 
     * @param id      the database identification number.
     * @param code    the identification number of the country by ISO 3166-1.
     * @param country the country name of the place.
     * @param region  the region name of the place.
     * @param city    the city name of the place.
     */
    public Place(final long id, final int code, final String country, 
            final String region, final String city) {
        this.id      = id;
        this.code    = code;
        this.country = country;
        this.region  = region;
        this.city    = city;
    }
    
    /**
     * Constructs object and returns an unfinished place object for given three 
     * letters country code. Constructed object provides region and city values 
     * set to empty strings, and database id set to negative value.
     * 
     * @param alpha3 the three letters country code.
     * 
     * @return       the place object for given country code.
     */
    public static Place valueOfAlpha3(final String alpha3) {
        final int    code    = CountryCodes.getCodeByAlpha3(alpha3);
        final String country = CountryCodes.getName(code);
                
        /* negative value represents no database id */
        return new Place(-1, code, country, "", "");
    }
    
    /**
     * Returns the database identification number.
     * 
     * @return the database identification number.
     */
    public long getId() {
        return id;
    }
    
    /**
     * Returns the identification number of the country by ISO 3166-1.
     * 
     * @return the identification number of the country by ISO 3166-1.
     */
    public int getCode() {
        return code;
    }

    /**
     * Returns the country name of the place.
     * 
     * @return the country name of the place.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Returns the region name of the place.
     * 
     * @return the region name of the place.
     */
    public String getRegion() {
        return region;
    }

    /**
     * Returns the city name of the place.
     * 
     * @return the city name of the place.
     */
    public String getCity() {
        return city;
    }
    
    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj the reference object with which to compare.
     * @return    true if this object is the same as the obj argument;
     *            false otherwise.
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final Place other = (Place) obj;
        if ((this.getId() != other.getId())
                || (this.getId()  == -1)
                || (other.getId() == -1)) {
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
        return (int) getId();
    }
    
    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder(126);

        /* 01, 01, Russian Federation, Chuvash Republic, Novocheboksarsk */
        builder.append(id).append(", "
                ).append(code).append(", "
                ).append(country).append(", "
                ).append(region).append(", "
                ).append(city);
        
        return builder.toString();
    }
}
