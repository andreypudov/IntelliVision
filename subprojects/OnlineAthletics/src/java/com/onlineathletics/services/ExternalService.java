/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.onlineathletics.services;

import com.onlineathletics.util.Database;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * The external web service provides public methods to obtain technical
 * information about Online Athletics and IntelliJustice status.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      ExternalService.java
 * %date      10:30:00 PM, Dec 25, 2013
 */
@WebService(serviceName = "ExternalService")
public class ExternalService {
    
    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.onlineathletics.core.Manifest.NAME);
    
    /**
     * Adds athlete entry and returns identification number.
     * 
     * @param firstName  the firth name of the athlete.
     * @param secondName the second name of the athlete.
     * @param birthday   the birthday of the athlete.
     * @param sex        the sex of the athlete (true for male).
     * @param userName   the name value to authenticate query. 
     * @param password   the password value to authenticate query.
     * 
     * @return the database id for the athlete.
     * 
     * @throws InvalidArgumentException the provided arguments are illegal.
     */
    @WebMethod(operationName = "addAthlete")
    public int addAthlete(
            @WebParam(name = "firstName")  final String  firstName,
            @WebParam(name = "secondName") final String  secondName,
            @WebParam(name = "birthday")   final long    birthday,
            @WebParam(name = "sex")        final boolean sex,
            @WebParam(name = "userName")   final String  userName,
            @WebParam(name = "password")   final String  password)
            
            throws Exception {
        
        /* the database stored procedure statement */
        final CallableStatement statement;
        
        try {
            statement = Database.getConnection().prepareCall(
                    "{CALL `onlineathletics`.`add_athlete` (?, ?, ?, ?, ?, ?)}");
            
            statement.setString("first_nm_arg",    firstName);
            statement.setString("second_nm_arg",   secondName);
            statement.setTimestamp("birthday_arg", new Timestamp(birthday));
            statement.setInt("sex_arg",            (sex) ? 1 : 0);
            
            statement.setString("user_nm_arg",     userName);
            statement.setString("pass_ph_arg",     password);
            
            try (final ResultSet set = statement.executeQuery()) {
                return set.getInt(1);
            }
        } catch (SQLException e) {
            throw new Exception(e.getMessage());
        }
    }
}
