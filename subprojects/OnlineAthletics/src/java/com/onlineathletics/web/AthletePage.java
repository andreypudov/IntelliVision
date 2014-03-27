/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.onlineathletics.web;

import com.onlineathletics.core.Athlete;
import com.onlineathletics.services.DatabaseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * The managed bean implements contact us form functionality.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      ContactForm.java
 * %date      05:50:00 PM, Jan 03, 2014
 */
@Named("athletePage")
@RequestScoped
public class AthletePage {
    
    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.onlineathletics.core.Manifest.NAME);
    
    private Athlete athlete;

    public void init(final long id) throws DatabaseException {
        athlete = Athlete.getAthleteById(id);
    }
    
    public long getId() {
        return athlete.getId();
    }
    
    public String getFirstName() {
        return athlete.getFirstName();
    }
    
    public String getMiddleName() {
        return athlete.getMiddleName();
    }
    
    public String getLastName() {
        return athlete.getLastName();
    }
    
    public String getFirstNameLocalized() {
        return athlete.getFirstNameLocalized();
    }
    
    public String getMiddleNameLocalized() {
        return athlete.getMiddleNameLocalized();
    }
    
    public String getLastNameLocalized() {
        return athlete.getLastNameLocalized();
    }
    
    public String getBirthday() {
        final SimpleDateFormat formatter = new SimpleDateFormat("MMMM d, yyyy");
        
        return formatter.format(new Date(athlete.getBirthday()));
    }

    public boolean getSex() {
        return athlete.getSex();
    }
}