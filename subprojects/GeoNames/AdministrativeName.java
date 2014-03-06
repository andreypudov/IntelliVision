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

import java.io.IOException;

/**
 * Administrative name data entry representation.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      AdministrativeName.java
 * %date      12:20:A0 PM, Mar 06, 2014
 */
public class AdministrativeName {

	private static final int FIEDLS_NUMBER = 4;

	private final String countryCode;
	private final String adminCode;
	private final String name;
	private final String asciiname;
	private final int    geonameid;

	public AdministrativeName(final String countryCode, final String adminCode,
			final String name, final String asciiname, final int geonameid) {
		this.countryCode = countryCode;
		this.adminCode   = adminCode;
		this.name        = name;
		this.asciiname   = asciiname;
		this.geonameid   = geonameid;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getAdminCode() {
		return adminCode;
	}

	public String getName() {
		return name;
	}

	public String getASCIIName() {
		return name;
	}

	public int getGeoNameId() {
		return geonameid;
	}

	public static AdministrativeName valueOf(final String value) 
			throws IOException {
		final String[] list = value.split("\t");

		if (list.length < FIEDLS_NUMBER)  {
			throw new IOException(
				"The incorrect number of fields in administrative names data file. [required: " 
				+ FIEDLS_NUMBER + ", found: " + list.length + "]");
		}

		final String[] sublist = list[0].split("\\.");
		if (sublist.length < 2) {
			throw new IOException("The incorrect administrative data file format.");
		}

		return new AdministrativeName(
			sublist[0], sublist[1], list[1], list[2],
			(list[3].length() > 0) ? Integer.parseInt(list[3]) : 0);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(126);

		return builder.append("[" + countryCode +"] "
			).append("[" + adminCode + "] "
			).append("[" + name + "] "
			).append("[" + asciiname + "] "
			).append("[" + geonameid + "]").toString();
	}
}