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
 * Alternative geographic name data entry representation.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      AlternativeName.java
 * %date      04:00:00 PM, Mar 05, 2014
 */
public class AlternativeName {

	private static final int FIEDLS_NUMBER_MIN = 4;
	private static final int FIEDLS_NUMBER_MAX = 8;

	private final int      alternativeNameId;
	private final int      geonameid;
	private final String   isolangauge;
	private final String   alternativeName;
	private final boolean  isPreferredName;
	private final boolean  isShortName;
	private final boolean  isColloquial;
	private final boolean  isHistoric;

	public AlternativeName(final int alternativeNameId, final int geonameid,
			final String isolangauge, final String alternativeName, 
			final boolean isPreferredName, final boolean isShortName,
			final boolean isColloquial, final boolean isHistoric) {
		this.alternativeNameId = alternativeNameId;
		this.geonameid         = geonameid;
		this.isolangauge       = isolangauge;
		this.alternativeName   = alternativeName;
		this.isPreferredName   = isPreferredName;
		this.isShortName       = isShortName;
		this.isColloquial      = isColloquial;
		this.isHistoric        = isHistoric;
	}

	public int getAlternativeNameId() {
		return alternativeNameId;
	}

	public int getGeoNameId() {
		return geonameid;
	}

	public String getISOLangiage() {
		return isolangauge;
	}

	public String getAlternativeName() {
		return alternativeName;
	}

	public boolean isPreferredName() {
		return isPreferredName;
	}

	public boolean isShortName() {
		return isShortName;
	}

	public boolean isColloquial() {
		return isColloquial;
	}

	public boolean isHistoric() {
		return isHistoric;
	}

	public static AlternativeName valueOf(final String value) throws IOException {
		final String[] buffer = value.split("\t");
		final String[] list   = new String[] {"", "", "", "", "", "", "", ""};

		if ((list.length < FIEDLS_NUMBER_MIN) || (list.length > FIEDLS_NUMBER_MAX))  {
			throw new IOException(
				"The incorrect number of fields in alternative geographical names data file. [required: " 
				+ FIEDLS_NUMBER_MIN + ", found: " + list.length + "]");
		}

		for (int index = 0; index <= list.length; ++index) {
			if (buffer.length < index + 1) {
				break;
			}

			list[index] = buffer[index];
		}
		
		return new AlternativeName(
			(list[0].length() > 0) ? Integer.parseInt(list[0]) : 0,
			(list[1].length() > 0) ? Integer.parseInt(list[1]) : 0,
			list[2], list[3],
			(list[4].length() > 0) ? Boolean.parseBoolean(list[4]) : false,
			(list[5].length() > 0) ? Boolean.parseBoolean(list[5]) : false,
			(list[6].length() > 0) ? Boolean.parseBoolean(list[6]) : false,
			(list[7].length() > 0) ? Boolean.parseBoolean(list[7]) : false);
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(126);

		return builder.append("[" + alternativeNameId +"] "
			).append("[" + geonameid + "] "
			).append("[" + isolangauge + "] "
			).append("[" + alternativeName + "] "
			).append("[" + isPreferredName + "] "
			).append("[" + isShortName + "] "
			).append("[" + isColloquial + "] "
			).append("[" + isHistoric + "]").toString();
	}
}