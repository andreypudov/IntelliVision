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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Geographic name data entry representation.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      GeoName.java
 * %date      10:10:00 PM, Mar 02, 2014
 */
public class GeoName {

	private static final int FIEDLS_NUMBER = 19;

	private final int      geonameid;
	private final String   name;
	private final String   asciiname;
	private final String[] alternatenames;
	private final double   latitude;
	private final double   longtitude;
	private final char     featureClass;
	private final String   featureCode;
	private final String   countryCode;
	private final String[] cc2;
	private final String   admin1Code;
	private final String   admin2Code;
	private final String   admin3Code;
	private final String   admin4Code;
	private final long     population;
	private final int      elevation;
	private final String   dem;
	private final String   timezone;
	private final long     modificationDate;

	public GeoName(final int geonameid, final String name, final String asciiname,
			final String[] alternatenames, final double latitude, final double longtitude,
			final char featureClass, final String featureCode, final String countryCode,
			final String[] cc2, final String admin1Code, final String admin2Code, 
			final String admin3Code, final String admin4Code, final long population,
			final int elevation, final String dem, final String timezone, final long modificationDate) {
		this.geonameid        = geonameid;
		this.name             = name;
		this.asciiname        = asciiname;
		this.alternatenames   = Arrays.copyOf(alternatenames, alternatenames.length);
		this.latitude         = latitude;
		this.longtitude       = longtitude;
		this.featureClass     = featureClass;
		this.featureCode      = featureCode;
		this.countryCode      = countryCode;
		this.cc2              = Arrays.copyOf(cc2, cc2.length);
		this.admin1Code       = admin1Code;
		this.admin2Code       = admin2Code;
		this.admin3Code       = admin3Code;
		this.admin4Code       = admin4Code;
		this.population       = population;
		this.elevation        = elevation;
		this.dem              = dem;
		this.timezone         = timezone;
		this.modificationDate = modificationDate;
	}

	public int getGeoNameId() {
		return geonameid;
	}

	public String getName() {
		return name;
	}

	public String getASCIIName() {
		return asciiname;
	}

	public String[] getAlternativeNames() {
		return Arrays.copyOf(alternatenames, alternatenames.length);
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongtitude() {
		return longtitude;
	}

	public char getFeatureClass() {
		return featureClass;
	}

	public String getFeatureCode() {
		return featureCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String[] getAlternativeCountryCodes() {
		return Arrays.copyOf(cc2, cc2.length);
	}

	public String getAdministrativeCode1() {
		return admin1Code;
	}

	public String getAdministrativeCode2() {
		return admin2Code;
	}

	public String getAdministrativeCode3() {
		return admin3Code;
	}

	public String getAdministrativeCode4() {
		return admin4Code;
	}

	public long getPopulation() {
		return population;
	}

	public int getElevation() {
		return elevation;
	}

	public String getDigitalElevation() {
		return dem;
	}

	public String getTimeZone() {
		return timezone;
	}

	public long getModificationDate() {
		return modificationDate;
	}

	public static GeoName valueOf(final String value) throws IOException {
		final String[] list = value.split("\t");

		if (list.length != FIEDLS_NUMBER) {
			throw new IOException(
				"The incorrect number of fields in geographical names data file. [required: " 
				+ FIEDLS_NUMBER + ", found: " + list.length + "]");
		}

		try {
			return new GeoName(
				(list[0].length() > 0) ? Integer.parseInt(list[0]) : 0, 
				list[1], list[2], list[3].split(","), 
				(list[4].length() > 0) ? Double.parseDouble(list[4]) : 0.0, 
				(list[5].length() > 0) ? Double.parseDouble(list[5]) : 0.0, 
				(list[6].length() > 0) ? list[6].charAt(0) : ' ', 
				list[7], list[8], list[9].split(","), 
				list[10], list[11], list[12], list[13], 
				(list[14].length() > 0) ? Long.parseLong(list[14]) : 0L,
				(list[15].length() > 0) ? Integer.parseInt(list[15]) : 0, 
				list[16], list[17],
				(list[18].length() > 0) ? new SimpleDateFormat("yyyy-MM-dd").parse(list[18]).getTime() : 0L);
		} catch (final ParseException e) {
			throw new IOException("Incorrect modification date format. [" + list[18] + "]");
		}
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder(126);

		return builder.append("[" + geonameid +"] "
			).append("[" + name + "] "
			).append("[" + asciiname + "] "
			).append(Arrays.toString(alternatenames)
			).append("[" + latitude + "] "
			).append("[" + longtitude + "] "
			).append("[" + featureClass + "] "
			).append("[" + featureCode + "] "
			).append("[" + countryCode + "] "
			).append(Arrays.toString(cc2)
			).append("[" + admin1Code + "] "
			).append("[" + admin2Code + "] "
			).append("[" + admin3Code + "] "
			).append("[" + admin4Code + "] "
			).append("[" + population + "] "
			).append("[" + elevation + "] "
			).append("[" + dem + "] "
			).append("[" + timezone + "] "
			).append("[" + new SimpleDateFormat("yyyy-MM-dd").format(new Date(modificationDate)) + "]").toString();
	}
}