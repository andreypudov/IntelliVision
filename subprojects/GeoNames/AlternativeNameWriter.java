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
 * Writes alternative geographical names to SQL file.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      AlternativeNameWriter.java
 * %date      04:30:00 PM, Mar 05, 2014
 */
public class AlternativeNameWriter {

	private static final int MAX_SQL_LINE_LENGTH = 800_000;

	private final BufferedWriter writer;

	public AlternativeNameWriter(final BufferedWriter writer) {
		this.writer = writer;
	}

	public void write(final Iterator<AlternativeName> iterator) throws IOException {
		writer.write("USE onlineathletics;\n\n");
		writer.write(SQLFormat.SQL_FILE_HEADER + "\n");
		writer.write("LOCK TABLES oa_geo_alternative_tbl WRITE;\n");
		writer.write("INSERT INTO oa_geo_alternative_tbl(alt_nm_id, geo_nm_key, language, alt_name, is_preferred, is_short_nm, is_colloquial, is_historic) VALUES\n\t");

		String buffer = "";
		int    length = 0;

		while (iterator.hasNext()) {
			final StringBuilder   builder = new StringBuilder(126);
			final AlternativeName name    = iterator.next();

			/* accept only required translations */
			if ((name.getISOLangiage().equals("") == false)
					&& (name.getISOLangiage().equalsIgnoreCase("en") == false)
					&& (name.getISOLangiage().equalsIgnoreCase("ru") == false)) {
				continue;
			}

			builder.append("(").append(name.getAlternativeNameId()).append(", "
				).append(name.getGeoNameId()).append(", '"
				).append(name.getISOLangiage()).append("', '"
				).append(name.getAlternativeName().replace("'", "\\'")).append("', "
				).append(name.isPreferredName() ? 1 : 0).append(", "
				).append(name.isShortName() ? 1 : 0).append(", "
				).append(name.isColloquial() ? 1 : 0).append(", "
				).append(name.isHistoric() ? 1 : 0);
			buffer  = builder.toString();
			length += buffer.length();

			if (iterator.hasNext()) {
				if (length <= MAX_SQL_LINE_LENGTH) {
					writer.write(buffer + "),");
				} else {
					writer.write(buffer + ");\n");
					writer.write("INSERT INTO oa_geo_alternative_tbl(alt_nm_id, geo_nm_key, language, alt_name, is_preferred, is_short_nm, is_colloquial, is_historic) VALUES\n\t");
					length = 0;
				}
			} else {
				writer.write(buffer + ");\n");
			}
		}

		writer.write("UNLOCK TABLES;");
		writer.write("\n" + SQLFormat.SQL_FILE_FOOTER + "\n");

		writer.flush();
	}
}