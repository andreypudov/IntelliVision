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

package com.onlineathletics.web;

import com.onlineathletics.core.Athlete;
import com.onlineathletics.util.Transliterator;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 * The managed bean implements athletes data import control.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      AthletesInputForm.java
 * %date      05:50:00 PM, Apr 05, 2014
 */
@Named("athletesInputForm")
@RequestScoped
public class AthletesInputForm {
    
    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.onlineathletics.core.Manifest.NAME);
    
    /* the source athlets data from client */
    private String request;
    
    /**
     * Returns the processed client request.
     * 
     * @return the processed client request.
     */
    public String getRequest() {
        return request;
    }

    /**
     * Sets the client request value.
     * 
     * @param request the client request value.
     */
    public void setRequest(final String request) {
        this.request = request;
    }
    
    /**
     * The form submit handler.
     */
    public void submit() {
        //request = "UPDATED";
    }
    
    /**
     * The form request validation handler.
     */
    public void validate() {
        //request = "VALIDATED";
        
        parse(request);
    }
    
    /**
     * Parses a list of athletes in JSON representation.
     * 
     * @param json the list of athletes in JSON string format.
     * @return     the list of athletes.
     */
    private List<Athlete> parse(final String json) {
        final List<String> errors = new ArrayList<>(16);
        
        try (final JsonReader reader = Json.createReader(new StringReader(json))) {
            final JsonArray  list   = reader.readArray();
            
            for (int index = 0; index < list.size(); ++index) {
                final JsonObject      object  = list.getJsonObject(index);
                final AthleteInstance athlete = new AthleteInstance(object);
                
            }
        }
        
        return null;
    }
    
    /**
     * Generates a JSON string represents a list of athletes.
     * 
     * @param list the list of athletes.
     * @return     the JSON string representation.
     */
    private String generate(final List<Athlete> list) {
        //JsonObject object = new JsonObject();
        
        return "";
    }
       
    /**
     * Validates an athlete representation and returns validation status.
     * 
     * @param firstName  the firth name of the athlete.
     * @param middleName the middle name of the athlete.
     * @param lastName   the last name of the athlete.
     * @param firstNameLocale  the first name of the athlete in local language.
     * @param middleNameLocale the middle name of the athlete in local language.
     * @param lastNameLocale   the second name of the athlete in local language.
     * @param birthday   the date of birth of the athlete.
     * @param birthplace the place of birth of the athlete.
     * @param sex        the sex of the athlete.
     * @param region1    the home region of the athlete.
     * @param region2    the home region of the athlete.
     * @param language   the ISO representation of local language.
     * 
     * @param errors     the list of errors.
     * 
     * @return true if athlete representation is valid, and false otherwise.
     */
    private boolean validateAthlete(final String firstName, 
            final String middleName, final String lastName, 
            final String firstNameLocale, final String middleNameLocale,
            final String lastNameLocale, final String birthday, 
            final String birthplace, final String sex,
            final String region1, final String region2, final String language,
            final List<String> errors) {
        return false;
    }

    /**
     * The athlete instance of JSON athlete record.
     */
    private static class AthleteInstance {
        
        private String firstName;
        private String middleName;
        private String lastName;
        private String firstNameLocale;
        private String middleNameLocale;
        private String lastNameLocale;
        private String birthday;
        private String birthplace;
        private String sex;
        private String region1;
        private String region2;
        private String language;
        
        private boolean valid;
        
        /**
         * Creates the athlete instance of JSON athlete record.
         */
        AthleteInstance(final JsonObject object) {
            firstName  = object.getString("First Name", "");
            middleName = object.getString("Middle Name", "");
            lastName   = object.getString("Last Name", "");
            firstNameLocale  = object.getString("Localized First Name", "");
            middleNameLocale = object.getString("Localized Middle Name", "");
            lastNameLocale   = object.getString("Localized Last Name", "");
            birthday   = object.getString("Birthday", "");
            birthplace = object.getString("Birthplace", "");
            sex        = object.getString("Sex", "");
            region1    = object.getString("Home Region 1", "");
            region2    = object.getString("Home Region 2", "");
            language   = object.getString("Language", "");
            
            transliterate();
        }
        
        /**
         * Fill an empty properties with translated values.
         */
        private void transliterate() {
            final Transliterator transliteratorForward;
            final Transliterator transliteratorBackware;
            
            /* set default language value to English */
            if (language.isEmpty()) {
                language = "en";
            }
            
            transliteratorForward  = Transliterator.getInstance(language, "en");
            transliteratorBackware = Transliterator.getInstance("en", language);
            
            if (transliteratorForward != null) {
                if (firstName.isEmpty() && (firstNameLocale.isEmpty() == false)) {
                    firstName = transliteratorForward.transliterate(firstNameLocale);
                }

                if (middleName.isEmpty() && (middleNameLocale.isEmpty() == false)) {
                    middleName = transliteratorForward.transliterate(middleNameLocale);
                }

                if (lastName.isEmpty() && (lastNameLocale.isEmpty() == false)) {
                    lastName = transliteratorForward.transliterate(lastNameLocale);
                }
            }
            
            if (transliteratorBackware != null) {
                if (firstNameLocale.isEmpty() && (firstName.isEmpty() == false)) {
                    firstNameLocale = transliteratorBackware.transliterate(firstName);
                }

                if (middleNameLocale.isEmpty() && (middleName.isEmpty() == false)) {
                    middleNameLocale = transliteratorBackware.transliterate(middleName);
                }

                if (lastNameLocale.isEmpty() && (lastName.isEmpty() == false)) {
                    lastNameLocale = transliteratorBackware.transliterate(lastName);
                }
            }
        }
        
        /**
         * Validates athlete instance properties and sets status value.
         */
        private void validate() {
            valid = true;
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
         * @return the last name of the athlete
         */
        public String getLastName() {
            return lastName;
        }

        /**
         * Returns the first localized name of the athlete.
         * 
         * @return the first localized name of the athlete
         */
        public String getFirstNameLocale() {
            return firstNameLocale;
        }

        /**
         * Returns the middle localized name of the athlete.
         * 
         * @return the middle localized name of the athlete
         */
        public String getMiddleNameLocale() {
            return middleNameLocale;
        }

        /**
         * Returns the last localized name of the athlete.
         * 
         * @return the last localized name of the athlete
         */
        public String getLsatNameLocale() {
            return lastNameLocale;
        }

        /**
         * Returns the birthday of the athlete.
         * 
         * @return the birthday of the athlete
         */
        public String getBirthday() {
            return birthday;
        }

        /**
         * Returns the birthplace of the athlete.
         * 
         * @return the birthplace of the athlete
         */
        public String getBirthplace() {
            return birthplace;
        }

        /**
         * Returns the sex of the athlete.
         * 
         * @return the sex of the athlete
         */
        public String getSex() {
            return sex;
        }

        /**
         * Returns the first home region of the athlete.
         * 
         * @return the first home region of the athlete
         */
        public String getRegion1() {
            return region1;
        }

        /**
         * Returns the second home region of the athlete.
         * 
         * @return the second home region of the athlete
         */
        public String getRegion2() {
            return region2;
        }

        /**
         * Returns the language of the athlete.
         * 
         * @return the language of the athlete
         */
        public String getLanguage() {
            return language;
        }
    }
}
