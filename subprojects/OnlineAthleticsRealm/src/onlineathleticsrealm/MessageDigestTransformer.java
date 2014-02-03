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

package onlineathleticsrealm;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * A password transformer.
 * 
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      MessageDigestTransformer.java
 * %date      08:30:00 PM, Feb 03, 2014
 */
public class MessageDigestTransformer implements PasswordTransformer {
    
    private final String digestAlgorithm;
    private final Charset charset;
    private final Encoder encoder;
    
    private Map<String, Encoder> encoders = new HashMap<>(16);
    
    {
        encoders.put("base64", new Base64Encoder());
        encoders.put("hex",    new HexEncoder());
    }
    
    /**
     * Creates message digest transform instance.
     * 
     * @param digestAlgorithm the algorithm to use for the digest
     * @param digestEncoding  the digest encoding value.
     * @param charset         the charset used for converting the password to bytes.
     * 
     * @throws IllegalArgumentException if the digest encoding is not supported
     */
    MessageDigestTransformer(final String digestAlgorithm, 
            final String digestEncoding, final Charset charset) {
        this.encoder         = encoders.get(digestEncoding);
        this.digestAlgorithm = digestAlgorithm;
        this.charset         = charset;

        if (this.encoder == null) {
            throw new IllegalArgumentException(digestEncoding 
                    + " is not supported. Only " + encoders.keySet() 
                    + " are supported");
        }
    }
    
    /**
     * Transforms the password string.
     *
     * @param password the value of original password.
     * @return         the value of transformed password.
     */
    @Override
    public char[] transform(final char[] password) {
        MessageDigest messageDigest = createMessageDigest();
        byte[]        passwordBytes = new String(password).getBytes(charset);
        
        messageDigest.reset();

        byte[] hash          = messageDigest.digest(passwordBytes);
        String encodedDigest = encoder.encode(hash);
        
        return encodedDigest.toCharArray();
    }
    
    /**
     * Creates digest instance.
     * 
     * @return the digest instance.
     */
    private MessageDigest createMessageDigest() {
        try {
            return MessageDigest.getInstance(digestAlgorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(digestAlgorithm 
                    + " is not supported on the current platform.");
        }
    }
}
