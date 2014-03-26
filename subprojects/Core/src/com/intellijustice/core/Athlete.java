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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * The athlete entity representation.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Athlete.java
 * %date      11:30:00 AM, Aug 03, 2013
 */
public class Athlete {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellijustice.core.Manifest.NAME);

    private final long    id;
    private final String  firstName;
    private final String  middleName;
    private final String  lastName;
    private final String  firstNameLocale;
    private final String  middleNameLocale;
    private final String  lastNameLocale;
    private final long    birthday;
    private final long    birthplace;
    private final boolean sex;       /* thue is male, false is female */
    
    private final long    language;

    /**
     * Constructs new Athletic object.
     *
     * @param id         the database id for the athlete (zero for new entry).
     * @param firstName  the firth name of the athlete.
     * @param middleName the middle name of the athlete.
     * @param lastName   the last name of the athlete.
     * @param firstNameLocale  the first name of the athlete in local language.
     * @param middleNameLocale the middle name of the athlete in local language.
     * @param lastNameLocale   the second name of the athlete in local language.
     * @param birthday   the date of birth of the athlete.
     * @param birthplace the place of birth of the athlete.
     * @param sex        the sex of the athlete (true for male).
     * @param language   the ISO representation of local language.
     */
    public Athlete(final long id, final String firstName, 
            final String middleName, final String lastName, 
            final String firstNameLocale, final String middleNameLocale,
            final String lastNameLocale, final long birthday,
            final long birthplace, final boolean sex, final long language) {
        this.id               = id;
        
        this.firstName        = firstName;
        this.middleName       = middleName;
        this.lastName         = lastName;
        
        this.firstNameLocale  = firstNameLocale;
        this.middleNameLocale = middleNameLocale;
        this.lastNameLocale   = lastNameLocale;
        
        this.birthday         = birthday;
        this.birthplace       = birthplace;
        this.sex              = sex;
        
        this.language         = language;
    }

    /**
     * Returns the database id for the athlete. If id value is zero, athlete
     * entry don't exists in database.
     *
     * @return the database id.
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the first name of the athlete.
     *
     * @return the first name of the athlete
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * Returns the middle name of the athlete.
     *
     * @return the middle name of the athlete.
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Returns the last name of the athlete.
     *
     * @return the last name of the athlete.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the localized first name of the athlete.
     *
     * @return the localized first name of the athlete
     */
    public String getFirstNameLocalized() {
        return firstNameLocale;
    }
    
    /**
     * Returns the localized middle name of the athlete.
     *
     * @return the localized middle name of the athlete.
     */
    public String getMiddleNameLocalized() {
        return middleNameLocale;
    }

    /**
     * Returns the localized last name of the athlete.
     *
     * @return the localized last name of the athlete.
     */
    public String getLastNameLocalized() {
        return lastNameLocale;
    }

    /**
     * Returns the date of birth of the athlete.
     *
     * @return the date of birth of the athlete.
     */
    public long getBirthday() {
        return birthday;
    }
    
    /**
     * Returns the place of birth of the athlete.
     *
     * @return the place of birth of the athlete.
     */
    public long getBirthplace() {
        return birthplace;
    }

    /**
     * Returns the sex of the athlete, where true represents male and false
     * represents female value.
     *
     * @return the sex of the athlete.
     */
    public boolean getSex() {
        return sex;
    }
    
    /**
     * Returns the identifier of the local language.
     * 
     * @return the identifier of the local language.
     */
    public long getLanguage() {
        return language;
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

        final Athlete other = (Athlete) obj;
        if (this.id != other.id) {
            return false;
        }

        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        
        if (!Objects.equals(this.middleName, other.middleName)) {
            return false;
        }

        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }

        if (this.birthday != other.birthday) {
            return false;
        }
        
        if (this.birthplace != other.birthplace) {
            return false;
        }

        if (this.sex != other.sex) {
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

        hash = 59 * hash + this.id;
        hash = 59 * hash + Objects.hashCode(this.firstName);
        hash = 59 * hash + Objects.hashCode(this.secondName);
        hash = 59 * hash + (int) (this.birthday ^ (this.birthday >>> 32));
        hash = 59 * hash + (this.sex ? 1 : 0);
        hash = 59 * hash + Objects.hashCode(this.country);*/

        return (int) id;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        final StringBuilder builder   = new StringBuilder(126);
        final DateFormat    formatter = new SimpleDateFormat("dd-MMM-yyyy");

        /* 01 S FistName LastName 01-01-1970 */
        builder.append((sex == true) ? "M" : "F").append(" "
                ).append(firstName).append(" "
                ).append(middleName).append(" "
                ).append(lastName).append(" "
                ).append(formatter.format(birthday));
        
        return builder.toString();
    }
}
