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

import com.sun.appserv.security.AppservPasswordLoginModule;
import java.util.Arrays;
import java.util.logging.Level;
import javax.security.auth.login.LoginException;

/**
 * OnlineAthletics JDBC login module.
 * 
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      OnlineAthleticsLoginModule.java
 * %date      11:11:00 AM, Feb 03, 2014
 */
public class OnlineAthleticsLoginModule extends AppservPasswordLoginModule {
    
    private static final java.util.logging.Logger LOG = _logger;

    @Override
    protected void authenticateUser() throws LoginException {
        validateRealm();
        validateUser();
        
        final OnlineAthleticsRealm realm = (OnlineAthleticsRealm) _currentRealm;
        final String[]             list  = realm.authenticate(_username, 
                new String(getPasswordChar()));

        if (list == null) {
            throw new LoginException("No groups found for user.");
        }

        if (LOG.isLoggable(Level.FINEST)) {
            LOG.log(Level.FINEST,
                    "OnlineAthletics login succeeded for: {0} groups: {1}", 
                    new Object[]{_username, Arrays.toString(list)});
        }

        commitUserAuthentication(list);
    }
    
    /**
     * Validate realm instance.
     * 
     * @throws LoginException the source of an exception. Fired when realm is 
     *                        not the expected OnlineAthleticsRealm instance.
     */
    private void validateRealm() throws LoginException {
        if ((_currentRealm instanceof OnlineAthleticsRealm) == false) {
            throw new LoginException("Invalid realm. Expected a " 
                    + OnlineAthleticsRealm.class.getName());
        }
    }
    
    /**
     * Validates database user instance.
     * 
     * @throws LoginException the source of an exception. Fired when username 
     *                        is null or empty.
     */
    private void validateUser() throws LoginException {
        /* a JDBC user must have a name not null and non-empty */
        if (_username == null || _username.isEmpty()) {
            throw new LoginException("Username must have a value.");
        }
    }
}
