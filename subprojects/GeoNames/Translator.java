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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

/**
 * Trasnlates GeoNames data to MySQL script.
 *
 * Required data files:
 *     allCountries.txt (geoname table representation):
 *         geonameid         : integer id of record in geonames database
 *         name              : name of geographical point (utf8) varchar(200)
 *         asciiname         : name of geographical point in plain ascii characters, varchar(200)
 *         alternatenames    : alternatenames, comma separated, ascii names automatically transliterated, convenience attribute from alternatename table, varchar(8000)
 *         latitude          : latitude in decimal degrees (wgs84)
 *         longitude         : longitude in decimal degrees (wgs84)
 *         feature class     : see http://www.geonames.org/export/codes.html, char(1)
 *         feature code      : see http://www.geonames.org/export/codes.html, varchar(10)
 *         country code      : ISO-3166 2-letter country code, 2 characters
 *         cc2               : alternate country codes, comma separated, ISO-3166 2-letter country code, 60 characters
 *         admin1 code       : fipscode (subject to change to iso code), see exceptions below, see file admin1Codes.txt for display names of this code; varchar(20)
 *         admin2 code       : code for the second administrative division, a county in the US, see file admin2Codes.txt; varchar(80) 
 *         admin3 code       : code for third level administrative division, varchar(20)
 *         admin4 code       : code for fourth level administrative division, varchar(20)
 *         population        : bigint (8 byte int) 
 *         elevation         : in meters, integer
 *         dem               : digital elevation model, srtm3 or gtopo30, average elevation of 3''x3'' (ca 90mx90m) or 30''x30'' (ca 900mx900m) area in meters, integer. srtm processed by cgiar/ciat.
 *         timezone          : the timezone id (see file timeZone.txt) varchar(40)
 *         modification date : date of last modification in yyyy-MM-dd format
 *
 *     admin1CodesASCII.txt (display names)
 *         country code      : ISO-3166 2-letter country code, 2 characters
 *         admin1 code       : fipscode (subject to change to iso code)
 *         name              : name of geographical point (utf8) varchar(200)
 *         asciiname         : name of geographical point in plain ascii characters, varchar(200)
 *         geonameid         : integer id of record in geonames database represents this entry
 *
 *    alternateNames.txt (alternative names)
 *         alternateNameId   : the id of this alternate name, int
 *         geonameid         : geonameId referring to id in table 'geoname', int
 *         isolanguage       : iso 639 language code 2- or 3-characters; 4-characters 'post' for postal codes and 'iata','icao' and faac for airport codes, fr_1793 for French Revolution names,  abbr for abbreviation, link for a website, varchar(7)
 *         alternate name    : alternate name or name variant, varchar(200)
 *         isPreferredName   : '1', if this alternate name is an official/preferred name
 *         isShortName       : '1', if this is a short name like 'California' for 'State of California'
 *         isColloquial      : '1', if this alternate name is a colloquial or slang term
 *         isHistoric        : '1', if this alternate name is historic and was used in the past
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      Translator.java
 * %date      09:50:00 PM, Mar 02, 2014
 */
public class Translator {

	public static void main(final String[] args) {
		final File countriesInput             = new File("allCountries.txt");
		final File alternativesInput          = new File("alternateNames.txt");
		final File administrationFirstInput   = new File("admin1CodesASCII.txt");
		/*final File administrationSecondInput  = new File("admin2Codes.txt");*/
		final File countriesOutput            = new File("oa_geo_country_tbl.sql");
		final File alternativesOutput         = new File("oa_geo_alternative_tbl.sql");
		final File administrationFirstOutput  = new File("oa_geo_administration_first_tbl.sql");
		/*final File administrationSecondOutput = new File("oa_geo_administration_second_tbl.sql");*/

		if ((countriesInput.exists() == false) || (countriesInput.isFile() == false)) {
			System.err.println("Countries file doesn't exists.");
			System.exit(Integer.MIN_VALUE);
		}

		if ((alternativesInput.exists() == false) || (alternativesInput.isFile() == false)) {
			System.err.println("Alternative country names file doesn't exists.");
			System.exit(Integer.MIN_VALUE);
		}

		if ((administrationFirstInput.exists() == false) || (administrationFirstInput.isFile() == false)) {
			System.err.println("Administrative names [first layer] file doesn't exists.");
			System.exit(Integer.MIN_VALUE);
		}

		/*if ((administrationSecondInput.exists() == false) || (administrationSecondInput.isFile() == false)) {
			System.err.println("Administrative names [second layer] file doesn't exists.");
			System.exit(Integer.MIN_VALUE);
		}*/

		try {
			final GeoNameReader                  geoReader         = new GeoNameReader(new BufferedReader(new FileReader(countriesInput)));
			final AlternativeNameReader          altReader         = new AlternativeNameReader(new BufferedReader(new FileReader(alternativesInput)));
			final AdministrativeFirstNameReader  adminFirstReader  = new AdministrativeFirstNameReader(new BufferedReader(new FileReader(administrationFirstInput)));
			/*final AdministrativeSecondNameReader adminSecondReader = new AdministrativeSecondNameReader(new BufferedReader(new FileReader(administrationSecondInput)));*/
			final GeoNameWriter                  geoWriter         = new GeoNameWriter(new BufferedWriter(new FileWriter(countriesOutput)));
			final AlternativeNameWriter          altWriter         = new AlternativeNameWriter(new BufferedWriter(new FileWriter(alternativesOutput)));
			final AdministrativeFirstNameWriter  adminFirstWriter  = new AdministrativeFirstNameWriter(new BufferedWriter(new FileWriter(administrationFirstOutput)));
			/*final AdministrativeSecondNameWriter adminSecondWriter = new AdministrativeSecondNameWriter(new BufferedWriter(new FileWriter(administrationSecondOutput)));*/

			final Iterator<GeoName>                  geoIterator         = geoReader.iterator();
			final Iterator<AlternativeName>          altIterator         = altReader.iterator();
			final Iterator<AdministrativeFirstName>  adminFirstIterator  = adminFirstReader.iterator();
			/*final Iterator<AdministrativeSecondName> adminSecondIterator = adminSecondReader.iterator();*/

			System.out.println("Translating geographical data...");
			geoWriter.write(geoIterator);

			System.out.println("Translating alternative geographical names data...");
			altWriter.write(altIterator);

			System.out.println("Translating administrative names data [first layer]...");
			adminFirstWriter.write(adminFirstIterator);

			/*System.out.println("Translating administrative names data [second layer]...");
			adminSecondWriter.write(adminSecondIterator);*/
		} catch (final IOException e) {
			System.err.println(e.getMessage());
			System.exit(Integer.MIN_VALUE);
		}
	}
}