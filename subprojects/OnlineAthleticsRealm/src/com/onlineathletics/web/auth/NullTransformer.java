/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.onlineathletics.web.auth;

/**
 * A password transformer implements Null-Object pattern with no transformation.
 * 
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      NullTransformer.java
 * %date      08:40:00 PM, Feb 03, 2014
 */
public class NullTransformer implements PasswordTransformer {
 
    /**
     * Transforms the password string.
     *
     * @param password the value of original password.
     * @return         the value of transformed password.
     */
    @Override
    public char[] transform(final char[] password) {
        return password;
    }
}
