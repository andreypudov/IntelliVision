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
import java.util.Collections;
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
                final JsonObject object = list.getJsonObject(index);
                
                final String firstName  = object.getString("First Name");
                final String middleName = object.getString("Middle Name");
                final String lastName   = object.getString("Last Name");
                final String firstNameLocale  = object.getString("Localized First Name");
                final String middleNameLocale = object.getString("Localized Middle Name");
                final String lsatNameLocale   = object.getString("Localized Last Name");
                final String birthday   = object.getString("Birthday");
                final String birthplace = object.getString("Birthplace");
                final String sex        = object.getString("Sex");
                final String region1    = object.getString("Home Region 1");
                final String region2    = object.getString("Home Region 2");
                final String language   = object.getString("Language");
                
                Transliterator transliterator = Transliterator.getInstance("ru", "en");
                if (transliterator == null) {
                    return null;
                }
                
                String ru_en = transliterator.transliterate("СМЕЛИК Сергей\n" +
                    "БОДРОВ Игорь\n" +
                    "САКАЛАУСКАС Ритис\n" +
                    "КОРЖ Виталий\n" +
                    "ПОЛОВИНКИН Максим\n" +
                    "ПЕТРЯШОВ Константин\n" +
                    "СВАРАЙ Джош\n" +
                    "ШЕВЕЛЕВ Вячеслав");
                String en_ru = Transliterator.getInstance("en", "ru").transliterate("Andrey");
                
                System.out.println(ru_en);
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
}
