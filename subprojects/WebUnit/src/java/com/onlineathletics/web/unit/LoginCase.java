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

package com.onlineathletics.web.unit;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      LoginCase.java
 * %date      11:00:00 PM, Feb 05, 2014
 */
@Named("loginCase")
@RequestScoped
public class LoginCase {
    
    private static final String PARAM_JNDI_DATASOURCE    = "jdbc/OnlineAthletics";
    //private static final String PARAM_PRINCIPAL_QUERY  = "SELECT pass_phrase FROM oa_accnt_user_tbl WHERE user_name = ?";
    private static final String PARAM_PRINCIPAL_QUERY  = "{CALL authenticate(?, ?)}";
 
    public boolean beginCase1() {
        final Connection conenction = getConnection();
        
        return (conenction != null);
    }
    
    public String beginCase2() {
        try (final Connection        connection = getConnection();
             //final PreparedStatement statement = connection.prepareStatement(PARAM_PRINCIPAL_QUERY)) {
             final CallableStatement statement = connection.prepareCall(PARAM_PRINCIPAL_QUERY)) {

            statement.setString(1, "apudov");
            statement.setString(2, "dfgiwr@lk5f$oiu%5e4r");
            
            statement.executeQuery();
            return "SUCCESS";
        } catch (SQLException e) {
            return "FAILED " + e.getMessage();
        }
    }
    
    /**
     * Returns database connection.
     * 
     * @return the database connection.
     */
    private Connection getConnection() {
        final String dataSourceJndi = PARAM_JNDI_DATASOURCE;
        
        try {
            final InitialContext context = new InitialContext();
            final DataSource     source  = (DataSource) context.lookup(dataSourceJndi);
            
            return source.getConnection();
        } catch (NamingException | SQLException e) {
            throw new IllegalStateException("Error retrieving connection", e);
        }
    }
}
