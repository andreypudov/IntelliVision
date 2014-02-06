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
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
    
    protected static final String PARAM_DIGEST_ALGORITHM = "digest-algorithm";
    protected static final String PARAM_DIGEST_ENCODING  = "digest-encoding";
    protected static final String PARAM_PASSWORD_CHARSET = "password-charset";
    protected static final String PARAM_PRINCIPAL_QUERY  = "password-query";
    protected static final String PARAM_SECURITY_ROLES_QUERY = "security-roles-query";
    protected static final String PARAM_JNDI_DATASOURCE  = "datasource-jndi";
    
    private static final String DEFAULT_DIGEST_ALGORITHM = "SHA-512";
    private static final String DEFAULT_DIGEST_ENCODING  = "hex";
    
    private static final String DEFAULT_JNDI_DATASOURCE  = "jdbc/OnlineAthletics";
    private static final String DEFAULT_PRINCIPAL_QUERY  = "SELECT pass_phrase FROM oa_accnt_user_tbl WHERE user_name = ?";
    private static final String DEFAULT_SECURITY_ROLES_QUERY = "SELECT group_name FROM oa_accnt_groups_tbl WHERE user_name = ?";
    
    private static final Map<String, String> OPTIONAL_PROPERTIES = new HashMap<>(16);
    
    static {
        OPTIONAL_PROPERTIES.put(PARAM_DIGEST_ALGORITHM, DEFAULT_DIGEST_ALGORITHM);
        OPTIONAL_PROPERTIES.put(PARAM_DIGEST_ENCODING, DEFAULT_DIGEST_ENCODING);
        OPTIONAL_PROPERTIES.put(PARAM_PASSWORD_CHARSET, Charset.defaultCharset().name());

        OPTIONAL_PROPERTIES.put(PARAM_JNDI_DATASOURCE, DEFAULT_JNDI_DATASOURCE);

        OPTIONAL_PROPERTIES.put(PARAM_PRINCIPAL_QUERY, DEFAULT_PRINCIPAL_QUERY);
        OPTIONAL_PROPERTIES.put(PARAM_SECURITY_ROLES_QUERY, DEFAULT_SECURITY_ROLES_QUERY);
    }
    
    /* the password transformer instance */
    private PasswordTransformer transformer;
    
    /**
     * Initializing OnlineAthleticsRealm variables.
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

        final String digestAlgorithm = getProperty(PARAM_DIGEST_ALGORITHM);
        switch (digestAlgorithm) {
            case "none":
                transformer = new NullTransformer();
                break;
            default:
                transformer = new MessageDigestTransformer(
                        digestAlgorithm, getProperty(PARAM_DIGEST_ENCODING), 
                        Charset.forName(getProperty(PARAM_PASSWORD_CHARSET)));
                break;
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
     * Returns the list of groups for specific user.
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
    public String[] authenticate(final String username, final char[] password) {
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
     * @param username the name of the user.
     * @return         the list of groups for specific user.
     */
    private List<String> getGroups(final String username) {
        final List<String> groupNames         = new ArrayList<>(16);
        final String       securityRolesQuery = getProperty(PARAM_SECURITY_ROLES_QUERY);
        LOG.log(Level.FINEST, "Executing query ''{0}'' with username {1}", 
                new String[] {securityRolesQuery, username});

        try (final Connection        connection = getConnection();
             final PreparedStatement statement = connection.prepareStatement(securityRolesQuery)) {
            statement.setString(1, username);

            try (final ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    groupNames.add(resultSet.getString(1));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        
        LOG.log(Level.FINEST, "User {0} has groups {1}", 
                new String[] {username, groupNames.toString()});
        
        return groupNames;
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
            final char[] password) {
        final String principalQuery = getProperty(PARAM_PRINCIPAL_QUERY);
        
        LOG.log(Level.FINEST, "Executing query ''{0}'' with username {1}", 
                new String[] {principalQuery, username});

        try (final Connection        connection = getConnection();
             final PreparedStatement statement = connection.prepareStatement(principalQuery)) {

            statement.setString(1, username);
            
            try (final ResultSet resultSet = statement.executeQuery()) {
                /* the method next() is in the validation method */
                return validatePassword(username, password, resultSet);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
    
    /**
     * Validates password for specific user account.
     * 
     * @param username  the user name value.
     * @param password  the password value.
     * @param resultSet the result set.
     * 
     * @return true if password is valid for given user, and false otherwise.
     * 
     * @throws SQLException the source of an exception.
     */
    private boolean validatePassword(final String username, 
            final char[] password, final ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            LOG.log(Level.INFO, "No user found for username {0}!", username);
            return false;
        }

        String databasePassword = resultSet.getString(1);
        if (databasePassword == null) {
            /* Password should be required so log with warning */
            LOG.log(Level.WARNING, "Username {0} has NO password!", username);
            return false;
        }
        
        char[] transformedPassword     = transformer.transform(password);
        char[] trimmedDatabasePassword = databasePassword.trim().toCharArray();
        
        LOG.log(Level.WARNING, "PASSWORDS: 1) ''{0}'' 2) ''{1}''", 
                new String[] {new String(trimmedDatabasePassword), new String(transformedPassword)});

        boolean passwordsEqual = Arrays.equals(trimmedDatabasePassword, transformedPassword);
        if (passwordsEqual == false) {
            LOG.log(Level.INFO, "Invalid Password entered for username {0}!", username);
            return false;
        }

        LOG.log(Level.FINEST, "Username {0} has valid password.", username);

        return true;
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
        } catch (NamingException | SQLException e) {
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
