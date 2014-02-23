/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.onlineathletics.web;

import com.onlineathletics.core.Athlete;
import java.io.IOException;
import java.sql.SQLException;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
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

    public void init(final int id) throws IOException {
        final FacesContext context = FacesContext.getCurrentInstance();
        
        try {
            athlete = Athlete.getAthleteById(id);
        } catch (final SQLException e) {
            context.getExternalContext().redirect("error404");
        }
    }
    
    public int getId() {
        return athlete.getId();
    }
    
    public String getFirstName() {
        return athlete.getFirstName();
    }
    
    public String getSecondName() {
        return athlete.getSecondName();
    }
}