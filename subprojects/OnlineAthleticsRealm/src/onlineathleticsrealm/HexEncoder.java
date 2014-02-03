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

/**
 * Hex encoder.
 * 
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      HexEncoder.java
 * %date      08:10:00 PM, Feb 03, 2014
 */
public class HexEncoder implements Encoder {
    
    /* reference table */
    private static final char[] DIGITS_NUMBERS =
          {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
              'A', 'B', 'C', 'D', 'E', 'F'};
    
    /**
     * Encodes given password by HEX function and returns encoded value.
     * 
     * @param password the password to encode.
     * @return         the value of encoded password.
     */
    @Override 
    public String encode(final byte[] password) {
        final StringBuilder builder = new StringBuilder(password.length * 2);
        
        for (final byte item : password) {
            builder.append(DIGITS_NUMBERS[(0xF0 & item) >>> 4]
                ).append(DIGITS_NUMBERS[0x0F & item]);
        }
        
        return builder.toString();
     }
}
