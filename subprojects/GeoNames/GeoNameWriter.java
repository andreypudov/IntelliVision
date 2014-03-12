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

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;

/**
 * Writes geographical names to SQL file.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      GeoNameWriter.java
 * %date      03:20:00 PM, Mar 05, 2014
 */
public class GeoNameWriter {

	private static final int MAX_SQL_LINE_LENGTH = 800_000;

	private final BufferedWriter writer;

	public GeoNameWriter(final BufferedWriter writer) {
		this.writer = writer;
	}

	public void write(final Iterator<GeoName> iterator) throws IOException {
		writer.write("USE onlineathletics;\n\n");
		writer.write(SQLFormat.SQL_FILE_HEADER + "\n");
		writer.write("LOCK TABLES oa_geo_country_tbl WRITE;\n");
		writer.write("INSERT INTO oa_geo_country_tbl(geo_nm_id, name, feature_class, feature_code, country_code, admin1_code, admin2_code) VALUES\n\t");

		String buffer = "";
		int    length = 0;

		while (iterator.hasNext()) {
			final StringBuilder builder = new StringBuilder(126);
			final GeoName       name    = iterator.next();

			builder.append("(").append(name.getGeoNameId()).append(", '"
				).append(name.getName().replace("'", "\\'")).append("', '"
				).append(name.getFeatureClass()).append("', '"
				).append(name.getFeatureCode()).append("', '"
				).append(name.getCountryCode()).append("', '"
				).append(name.getAdministrativeCode1().replace("'", "\\'")).append("', '"
				).append(name.getAdministrativeCode2().replace("'", "\\'"));
			buffer  = builder.toString();
			length += buffer.length();

			if (iterator.hasNext()) {
				if (length <= MAX_SQL_LINE_LENGTH) {
					writer.write(buffer + "'),");
				} else {
					writer.write(buffer + "');\n");
					writer.write("INSERT INTO oa_geo_country_tbl(geo_nm_id, name, feature_class, feature_code, country_code, admin1_code, admin2_code) VALUES\n\t");
					length = 0;
				}
			} else {
				writer.write(buffer + "');\n");
			}
		}

		writer.write("UNLOCK TABLES;");
		writer.write("\n" + SQLFormat.SQL_FILE_FOOTER + "\n");

		writer.flush();
	}
}