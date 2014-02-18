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

package com.onlineathletics.web.auth;

import com.sun.appserv.security.AppservRealm;
import com.sun.enterprise.security.auth.realm.BadRealmException;
import com.sun.enterprise.security.auth.realm.InvalidOperationException;
import com.sun.enterprise.security.auth.realm.NoSuchRealmException;
import com.sun.enterprise.security.auth.realm.NoSuchUserException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.jvnet.hk2.annotations.Service;
import org.openbsd.security.BCrypt;

/**
 * The collection of validation methods.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      OnlineAthleticsRealm.java
 * %date      10:15:00 PM, Jan 12, 2014
 */
@Service(name = OnlineAthleticsRealm.SERVICE_NAME)
public class OnlineAthleticsRealm extends AppservRealm {
    
    private static final java.util.logging.Logger LOG = _logger;
    
    protected static final String SERVICE_NAME = "OnlineAthleticsRealm";
    
    private static final String PARAM_JNDI_DATASOURCE  = "datasource-jndi";
    private static final String PARAM_PRINCIPAL_QUERY  = "password-query";
    private static final String PARAM_SECURITY_QUERY   = "password-hash-query";
    private static final String PARAM_ROLES_QUERY      = "security-roles-query";
    
    private static final String DEFAULT_JNDI_DATASOURCE  = "jdbc/OnlineAthletics";
    private static final String DEFAULT_PRINCIPAL_QUERY  = "{CALL auth_validate_credentials(?, ?)}";
    private static final String DEFAULT_SECURITY_QUERY   = "{CALL auth_get_hash(?)}";
    private static final String DEFAULT_ROLES_QUERY      = "{CALL auth_get_group_list(?)}";
    
    private static final Map<String, String> OPTIONAL_PROPERTIES = new HashMap<>(16);
    static {
        OPTIONAL_PROPERTIES.put(PARAM_JNDI_DATASOURCE, DEFAULT_JNDI_DATASOURCE);

        OPTIONAL_PROPERTIES.put(PARAM_PRINCIPAL_QUERY, DEFAULT_PRINCIPAL_QUERY);
        OPTIONAL_PROPERTIES.put(PARAM_SECURITY_QUERY,  DEFAULT_SECURITY_QUERY);
        OPTIONAL_PROPERTIES.put(PARAM_ROLES_QUERY,     DEFAULT_ROLES_QUERY);
    }
    
    /**
     * Initializing OnlineAthleticsRealm variables. This method is invoked 
     * during server startup when realm is initially loaded. If the method 
     * returns without an exception, the GlassFish Server assumes that the realm
     * is ready to service authentication requests. If an exception is thrown,
     * the realm is disabled.
     * 
     * @param parameters the list of properties.
     * 
     * @throws BadRealmException    the source of an exception.
     * @throws NoSuchRealmException the source of an exception.
     */
    @Override 
    protected void init(final Properties parameters) 
            throws BadRealmException, NoSuchRealmException {
        super.init(parameters);
        LOG.log(Level.FINE, "Initializing {0}", this.getClass().getSimpleName());

        /*
         * Among the other custom properties, there is a property jaas-context 
         * This property should be set using the call setProperty method 
         * implemented in the parent class.
         */
        validateProperty(JAAS_CONTEXT_PARAM, parameters);

        for (Map.Entry<String, String> entry : OPTIONAL_PROPERTIES.entrySet()) {
            setOptionalProperty(entry.getKey(), parameters, entry.getValue());
        }
    }
    
    /**
     * Returns a descriptive string representing the type of authentication 
     * done by this realm.
     * 
     * @return a descriptive string representing the type of authentication 
     * done by this realm.
     */
    @Override
    public String getAuthType() {
        return "OnlineAthletics JDBC Realm";
    }

    /**
     * Returns the list of groups for given user.
     * 
     * @param username the name of the user.
     * @return         the list of groups for specific user.
     * 
     * @throws InvalidOperationException the source of an exception.
     * @throws NoSuchUserException       the source of an exception.
     */
    @Override
    public Enumeration<String> getGroupNames(final String username) 
            throws InvalidOperationException, NoSuchUserException {
        return Collections.enumeration(getGroups(username));
    }
    
    /**
     * Authenticate by specific credentials.
     * 
     * @param username the user name value.
     * @param password the password value.
     * 
     * @return the list of groups for the user.
     */
    public String[] authenticate(final String username, final String password) {
        LOG.log(Level.FINEST, "Authenticating user {0}", username);

        final boolean  authenticated = validateCredentials(username, password);
        final String[] groups        = authenticated ? 
                convertToArray(getGroups(username)) : null;

        LOG.log(Level.FINEST, "User {0}, authenticated {1} has groups {2}", 
                new String[] {username, Boolean.toString(authenticated), 
                    Arrays.deepToString(groups)});
        
        return groups;
    }
    
    /**
     * Returns the list of groups for specific user.
     * 
     * @param username the name of the account which groups to return.
     * @return         the list of groups for specific user.
     */
    private List<String> getGroups(final String username) {
        final List<String> groupNames = new ArrayList<>(16);
        final String       rolesQuery = getProperty(PARAM_ROLES_QUERY);

        try (final Connection        connection = getConnection();
             final CallableStatement statement  = connection.prepareCall(rolesQuery)) {
            LOG.log(Level.FINEST, "Executing query ''{0}'' with username {1}", 
                new String[] {rolesQuery, username});
            
            statement.setString(1, username);

            try (final ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    groupNames.add(resultSet.getString(1));
                }
            }
        } catch (final SQLException e) {
            LOG.log(Level.INFO, "Group list obtaining failed for user {0}: {1}.", 
                    new String[] {username, e.getMessage()});
            
            throw new IllegalStateException(e);
        }
        
        LOG.log(Level.FINEST, "User {0} has groups {1}", 
                new String[] {username, groupNames.toString()});
        
        return groupNames;
    }
    
    /**
     * Returns the hashed password value for a given user account.
     * 
     * @param username the user name value.
     * @return         the hashed password value.
     */
    private String getHash(final String username) {
        final String securityQuery = getProperty(PARAM_SECURITY_QUERY);
        
        try (final Connection        connection = getConnection();
             final CallableStatement statement  = connection.prepareCall(securityQuery)) {
            
            LOG.log(Level.FINEST, "Executing query ''{0}'' with username {1}", 
                new String[] {securityQuery, username});

            statement.setString(1, username);
            
            try (final ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getString(1);
            }
        } catch (final SQLException e) {
            LOG.log(Level.INFO, "Hash obtaining failed for user {0}: {1}.", 
                    new String[] {username, e.getMessage()});
            
            throw new IllegalStateException(e);
        }
    }
    
    /**
     * Validates user credentials and returns true if credentials are valid,
     * and false otherwise.
     * 
     * @param username the user name value.
     * @param password the password value.
     * 
     * @return true if credentials are valid, and false otherwise.
     */
    private boolean validateCredentials(final String username, 
            final String password) {
        final String principalQuery = getProperty(PARAM_PRINCIPAL_QUERY);
        
        try (final Connection        connection = getConnection();
             final CallableStatement statement  = connection.prepareCall(principalQuery)) {
            
            LOG.log(Level.FINEST, "Executing query ''{0}'' with username {1}", 
                new String[] {principalQuery, username});

            /* hash function accepts already hashed password as salt */
            statement.setString(1, username);
            statement.setString(2, BCrypt.hashpw(password, getHash(username)));
            
            statement.executeQuery();
            
            LOG.log(Level.FINEST, "Username {0} has valid password.", username);
            
            return true;
        } catch (final SQLException e) {
            LOG.log(Level.INFO, "Authentication failed for user {0}: {1}.", 
                    new String[] {username, e.getMessage()});
            
            return false;
        }
    }
   
    /**
     * Returns database connection.
     * 
     * @return the database connection.
     */
    private Connection getConnection() {
        final String dataSourceJndi = getProperty(PARAM_JNDI_DATASOURCE);
        
        try {
            final InitialContext context = new InitialContext();
            final DataSource     source  = (DataSource) context.lookup(dataSourceJndi);
            
            return source.getConnection();
        } catch (final NamingException | SQLException e) {
            throw new IllegalStateException("Error retrieving connection", e);
        }
    }
 
    /**
     * Sets value of optional property.
     * 
     * @param name         the name of the property to set.
     * @param parameters   the list of properties.
     * @param defaultValue the default value of the property.
     * 
     * @throws BadRealmException the source of an exception.
     */
    private void setOptionalProperty(final String name, final Properties parameters, 
            final String defaultValue) throws BadRealmException {
        validateProperty(name, parameters.getProperty(name, defaultValue));
    }
    
    /**
     * Validates property and set the value.
     * 
     * @param name       the name of the property.
     * @param parameters the list of properties.
     * 
     * @throws BadRealmException the source of an exception.
     */
    private void validateProperty(final String name, 
            final Properties parameters) throws BadRealmException {
        validateProperty(name, parameters.getProperty(name));
    }
    
    /**
     * Validates property and set the value.
     * 
     * @param name  the name of the property.
     * @param value the value of the property.
     * 
     * @throws BadRealmException the source of an exception.
     */
    private void validateProperty(final String name, final String value) 
            throws BadRealmException {
        if (value == null) {
            final String message = sm.getString("realm.missingprop", name, SERVICE_NAME);
            throw new BadRealmException(message);
        }
        
        LOG.log(Level.FINE, "Setting property {0} to ''{1}''", 
                new String[] {name, value});

        super.setProperty(name, value);
    }
    
    /**
     * Converts a list to an array of strings.
     * 
     * @param list the list to convert.
     * @return     the array of strings.
     */
    private String[] convertToArray(final List<String> list) {
        final String[] groupsArray = new String[list.size()];
        
        list.toArray(groupsArray);
        
        return groupsArray;
    }
}
