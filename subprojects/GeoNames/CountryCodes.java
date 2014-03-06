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

/**
 * ISO 3166 - codes for countries and their subdivisions.
 *
 * Alpha-2 code – a two-letter code that represents a country name, recommended
 *                as the general purpose code.
 * Alpha-3 code – a three-letter code that represents a country name, which is
 *                usually more closely related to the country name.
 * Alpha-4 code – a four-letter code that represents a country name that is no
 *                longer in use. The structure depends on the reason why the 
 *                country name was removed from ISO 3166-1 
 *                and added to ISO 3166-3.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      CountryCodes.java
 * %date      04:30:00 PM, Mar 03, 2014
 */
public class CountryCodes {

	/**
	 * A country code representation.
	 */
	static class CountryEntry {
		private final String alpha2;
		private final String alpha3;
		private final String name;

		private final int    code;

		public CountryEntry(final String alpha2, final String alpha3,
			final String name, final int code) {
			this.alpha2 = alpha2;
			this.alpha3 = alpha3;
			this.name   = name;

			this.code   = code;
		}

		public String getAlpha2() {
			return alpha2;
		}

		public String getAlpha3() {
			return alpha3;
		}

		public String getName() {
			return name;
		}

		public int getCode() {
			return code;
		}
	}

	/*
	 * A country codes sorted by alpha 2 codes.
	 */
	private static final CountryEntry[] codes = new CountryEntry[] {
		new CountryEntry("AD", "AND", "Andorra", 20),
		new CountryEntry("AE", "ARE", "United Arab Emirates", 784),
		new CountryEntry("AF", "AFG", "Afghanistan", 4),
		new CountryEntry("AG", "ATG", "Antigua and Barbuda", 28),
		new CountryEntry("AI", "AIA", "Anguilla", 660),
		new CountryEntry("AL", "ALB", "Albania", 8),
		new CountryEntry("AM", "ARM", "Armenia", 51),
		new CountryEntry("AO", "AGO", "Angola", 24),
		new CountryEntry("AQ", "ATA", "Antarctica", 10),
		new CountryEntry("AR", "ARG", "Argentina", 32),
		new CountryEntry("AS", "ASM", "American Samoa", 16),
		new CountryEntry("AT", "AUT", "Austria", 40),
		new CountryEntry("AU", "AUS", "Australia", 36),
		new CountryEntry("AW", "ABW", "Aruba", 533),
		new CountryEntry("AX", "ALA", "Åland Islands", 248),
		new CountryEntry("AZ", "AZE", "Azerbaijan", 31),
		new CountryEntry("BA", "BIH", "Bosnia and Herzegovina", 70),
		new CountryEntry("BB", "BRB", "Barbados", 52),
		new CountryEntry("BD", "BGD", "Bangladesh", 50),
		new CountryEntry("BE", "BEL", "Belgium", 56),
		new CountryEntry("BF", "BFA", "Burkina Faso", 854),
		new CountryEntry("BG", "BGR", "Bulgaria", 100),
		new CountryEntry("BH", "BHR", "Bahrain", 48),
		new CountryEntry("BI", "BDI", "Burundi", 108),
		new CountryEntry("BJ", "BEN", "Benin", 204),
		new CountryEntry("BL", "BLM", "Saint Barthélemy", 652),
		new CountryEntry("BM", "BMU", "Bermuda", 60),
		new CountryEntry("BN", "BRN", "Brunei Darussalam", 96),
		new CountryEntry("BO", "BOL", "Bolivia, Plurinational State of", 68),
		new CountryEntry("BQ", "BES", "Bonaire, Sint Eustatius and Saba", 535),
		new CountryEntry("BR", "BRA", "Brazil", 76),
		new CountryEntry("BS", "BHS", "Bahamas", 44),
		new CountryEntry("BT", "BTN", "Bhutan", 64),
		new CountryEntry("BV", "BVT", "Bouvet Island", 74),
		new CountryEntry("BW", "BWA", "Botswana", 72),
		new CountryEntry("BY", "BLR", "Belarus", 112),
		new CountryEntry("BZ", "BLZ", "Belize", 84),
		new CountryEntry("CA", "CAN", "Canada", 124),
		new CountryEntry("CC", "CCK", "Cocos (Keeling) Islands", 166),
		new CountryEntry("CD", "COD", "Congo, the Democratic Republic of the", 180),
		new CountryEntry("CF", "CAF", "Central African Republic", 140),
		new CountryEntry("CG", "COG", "Congo", 178),
		new CountryEntry("CH", "CHE", "Switzerland", 756),
		new CountryEntry("CI", "CIV", "Côte d'Ivoire", 384),
		new CountryEntry("CK", "COK", "Cook Islands", 184),
		new CountryEntry("CL", "CHL", "Chile", 152),
		new CountryEntry("CM", "CMR", "Cameroon", 120),
		new CountryEntry("CN", "CHN", "China", 156),
		new CountryEntry("CO", "COL", "Colombia", 170),
		new CountryEntry("CR", "CRI", "Costa Rica", 188),
		new CountryEntry("CU", "CUB", "Cuba", 192),
		new CountryEntry("CV", "CPV", "Cape Verde", 132),
		new CountryEntry("CW", "CUW", "Curaçao", 531),
		new CountryEntry("CX", "CXR", "Christmas Island", 162),
		new CountryEntry("CY", "CYP", "Cyprus", 196),
		new CountryEntry("CZ", "CZE", "Czech Republic", 203),
		new CountryEntry("DE", "DEU", "Germany", 276),
		new CountryEntry("DJ", "DJI", "Djibouti", 262),
		new CountryEntry("DK", "DNK", "Denmark", 208),
		new CountryEntry("DM", "DMA", "Dominica", 212),
		new CountryEntry("DO", "DOM", "Dominican Republic", 214),
		new CountryEntry("DZ", "DZA", "Algeria", 12),
		new CountryEntry("EC", "ECU", "Ecuador", 218),
		new CountryEntry("EE", "EST", "Estonia", 233),
		new CountryEntry("EG", "EGY", "Egypt", 818),
		new CountryEntry("EH", "ESH", "Western Sahara", 732),
		new CountryEntry("ER", "ERI", "Eritrea", 232),
		new CountryEntry("ES", "ESP", "Spain", 724),
		new CountryEntry("ET", "ETH", "Ethiopia", 231),
		new CountryEntry("FI", "FIN", "Finland", 246),
		new CountryEntry("FJ", "FJI", "Fiji", 242),
		new CountryEntry("FK", "FLK", "Falkland Islands (Malvinas)", 238),
		new CountryEntry("FM", "FSM", "Micronesia, Federated States of", 583),
		new CountryEntry("FO", "FRO", "Faroe Islands", 234),
		new CountryEntry("FR", "FRA", "France", 250),
		new CountryEntry("GA", "GAB", "Gabon", 266),
		new CountryEntry("GB", "GBR", "United Kingdom", 826),
		new CountryEntry("GD", "GRD", "Grenada", 308),
		new CountryEntry("GE", "GEO", "Georgia", 268),
		new CountryEntry("GF", "GUF", "French Guiana", 254),
		new CountryEntry("GG", "GGY", "Guernsey", 831),
		new CountryEntry("GH", "GHA", "Ghana", 288),
		new CountryEntry("GI", "GIB", "Gibraltar", 292),
		new CountryEntry("GL", "GRL", "Greenland", 304),
		new CountryEntry("GM", "GMB", "Gambia", 270),
		new CountryEntry("GN", "GIN", "Guinea", 324),
		new CountryEntry("GP", "GLP", "Guadeloupe", 312),
		new CountryEntry("GQ", "GNQ", "Equatorial Guinea", 226),
		new CountryEntry("GR", "GRC", "Greece", 300),
		new CountryEntry("GS", "SGS", "South Georgia and the South Sandwich Islands", 239),
		new CountryEntry("GT", "GTM", "Guatemala", 320),
		new CountryEntry("GU", "GUM", "Guam", 316),
		new CountryEntry("GW", "GNB", "Guinea-Bissau", 624),
		new CountryEntry("GY", "GUY", "Guyana", 328),
		new CountryEntry("HK", "HKG", "Hong Kong", 344),
		new CountryEntry("HM", "HMD", "Heard Island and McDonald Islands", 334),
		new CountryEntry("HN", "HND", "Honduras", 340),
		new CountryEntry("HR", "HRV", "Croatia", 191),
		new CountryEntry("HT", "HTI", "Haiti", 332),
		new CountryEntry("HU", "HUN", "Hungary", 348),
		new CountryEntry("ID", "IDN", "Indonesia", 360),
		new CountryEntry("IE", "IRL", "Ireland", 372),
		new CountryEntry("IL", "ISR", "Israel", 376),
		new CountryEntry("IM", "IMN", "Isle of Man", 833),
		new CountryEntry("IN", "IND", "India", 356),
		new CountryEntry("IO", "IOT", "British Indian Ocean Territory", 86),
		new CountryEntry("IQ", "IRQ", "Iraq", 368),
		new CountryEntry("IR", "IRN", "Iran, Islamic Republic of", 364),
		new CountryEntry("IS", "ISL", "Iceland", 352),
		new CountryEntry("IT", "ITA", "Italy", 380),
		new CountryEntry("JE", "JEY", "Jersey", 832),
		new CountryEntry("JM", "JAM", "Jamaica", 388),
		new CountryEntry("JO", "JOR", "Jordan", 400),
		new CountryEntry("JP", "JPN", "Japan", 392),
		new CountryEntry("KE", "KEN", "Kenya", 404),
		new CountryEntry("KG", "KGZ", "Kyrgyzstan", 417),
		new CountryEntry("KH", "KHM", "Cambodia", 116),
		new CountryEntry("KI", "KIR", "Kiribati", 296),
		new CountryEntry("KM", "COM", "Comoros", 174),
		new CountryEntry("KN", "KNA", "Saint Kitts and Nevis", 659),
		new CountryEntry("KP", "PRK", "Korea, Democratic People's Republic of", 408),
		new CountryEntry("KR", "KOR", "Korea, Republic of", 410),
		new CountryEntry("KW", "KWT", "Kuwait", 414),
		new CountryEntry("KY", "CYM", "Cayman Islands", 136),
		new CountryEntry("KZ", "KAZ", "Kazakhstan", 398),
		new CountryEntry("LA", "LAO", "Lao People's Democratic Republic", 418),
		new CountryEntry("LB", "LBN", "Lebanon", 422),
		new CountryEntry("LC", "LCA", "Saint Lucia", 662),
		new CountryEntry("LI", "LIE", "Liechtenstein", 438),
		new CountryEntry("LK", "LKA", "Sri Lanka", 144),
		new CountryEntry("LR", "LBR", "Liberia", 430),
		new CountryEntry("LS", "LSO", "Lesotho", 426),
		new CountryEntry("LT", "LTU", "Lithuania", 440),
		new CountryEntry("LU", "LUX", "Luxembourg", 442),
		new CountryEntry("LV", "LVA", "Latvia", 428),
		new CountryEntry("LY", "LBY", "Libya", 434),
		new CountryEntry("MA", "MAR", "Morocco", 504),
		new CountryEntry("MC", "MCO", "Monaco", 492),
		new CountryEntry("MD", "MDA", "Moldova, Republic of", 498),
		new CountryEntry("ME", "MNE", "Montenegro", 499),
		new CountryEntry("MF", "MAF", "Saint Martin (French part)", 663),
		new CountryEntry("MG", "MDG", "Madagascar", 450),
		new CountryEntry("MH", "MHL", "Marshall Islands", 584),
		new CountryEntry("MK", "MKD", "Macedonia, the former Yugoslav Republic of", 807),
		new CountryEntry("ML", "MLI", "Mali", 466),
		new CountryEntry("MM", "MMR", "Myanmar", 104),
		new CountryEntry("MN", "MNG", "Mongolia", 496),
		new CountryEntry("MO", "MAC", "Macao", 446),
		new CountryEntry("MP", "MNP", "Northern Mariana Islands", 580),
		new CountryEntry("MQ", "MTQ", "Martinique", 474),
		new CountryEntry("MR", "MRT", "Mauritania", 478),
		new CountryEntry("MS", "MSR", "Montserrat", 500),
		new CountryEntry("MT", "MLT", "Malta", 470),
		new CountryEntry("MU", "MUS", "Mauritius", 480),
		new CountryEntry("MV", "MDV", "Maldives", 462),
		new CountryEntry("MW", "MWI", "Malawi", 454),
		new CountryEntry("MX", "MEX", "Mexico", 484),
		new CountryEntry("MY", "MYS", "Malaysia", 458),
		new CountryEntry("MZ", "MOZ", "Mozambique", 508),
		new CountryEntry("NA", "NAM", "Namibia", 516),
		new CountryEntry("NC", "NCL", "New Caledonia", 540),
		new CountryEntry("NE", "NER", "Niger", 562),
		new CountryEntry("NF", "NFK", "Norfolk Island", 574),
		new CountryEntry("NG", "NGA", "Nigeria", 566),
		new CountryEntry("NI", "NIC", "Nicaragua", 558),
		new CountryEntry("NL", "NLD", "Netherlands", 528),
		new CountryEntry("NO", "NOR", "Norway", 578),
		new CountryEntry("NP", "NPL", "Nepal", 524),
		new CountryEntry("NR", "NRU", "Nauru", 520),
		new CountryEntry("NU", "NIU", "Niue", 570),
		new CountryEntry("NZ", "NZL", "New Zealand", 554),
		new CountryEntry("OM", "OMN", "Oman", 512),
		new CountryEntry("PA", "PAN", "Panama", 591),
		new CountryEntry("PE", "PER", "Peru", 604),
		new CountryEntry("PF", "PYF", "French Polynesia", 258),
		new CountryEntry("PG", "PNG", "Papua New Guinea", 598),
		new CountryEntry("PH", "PHL", "Philippines", 608),
		new CountryEntry("PK", "PAK", "Pakistan", 586),
		new CountryEntry("PL", "POL", "Poland", 616),
		new CountryEntry("PM", "SPM", "Saint Pierre and Miquelon", 666),
		new CountryEntry("PN", "PCN", "Pitcairn", 612),
		new CountryEntry("PR", "PRI", "Puerto Rico", 630),
		new CountryEntry("PS", "PSE", "Palestine, State of", 275),
		new CountryEntry("PT", "PRT", "Portugal", 620),
		new CountryEntry("PW", "PLW", "Palau", 585),
		new CountryEntry("PY", "PRY", "Paraguay", 600),
		new CountryEntry("QA", "QAT", "Qatar", 634),
		new CountryEntry("RE", "REU", "Réunion", 638),
		new CountryEntry("RO", "ROU", "Romania", 642),
		new CountryEntry("RS", "SRB", "Serbia", 688),
		new CountryEntry("RU", "RUS", "Russian Federation", 643),
		new CountryEntry("RW", "RWA", "Rwanda", 646),
		new CountryEntry("SA", "SAU", "Saudi Arabia", 682),
		new CountryEntry("SB", "SLB", "Solomon Islands", 90),
		new CountryEntry("SC", "SYC", "Seychelles", 690),
		new CountryEntry("SD", "SDN", "Sudan", 729),
		new CountryEntry("SE", "SWE", "Sweden", 752),
		new CountryEntry("SG", "SGP", "Singapore", 702),
		new CountryEntry("SH", "SHN", "Saint Helena, Ascension and Tristan da Cunha", 654),
		new CountryEntry("SI", "SVN", "Slovenia", 705),
		new CountryEntry("SJ", "SJM", "Svalbard and Jan Mayen", 744),
		new CountryEntry("SK", "SVK", "Slovakia", 703),
		new CountryEntry("SL", "SLE", "Sierra Leone", 694),
		new CountryEntry("SM", "SMR", "San Marino", 674),
		new CountryEntry("SN", "SEN", "Senegal", 686),
		new CountryEntry("SO", "SOM", "Somalia", 706),
		new CountryEntry("SR", "SUR", "Suriname", 740),
		new CountryEntry("SS", "SSD", "South Sudan", 728),
		new CountryEntry("ST", "STP", "Sao Tome and Principe", 678),
		new CountryEntry("SV", "SLV", "El Salvador", 222),
		new CountryEntry("SX", "SXM", "Sint Maarten (Dutch part)", 534),
		new CountryEntry("SY", "SYR", "Syrian Arab Republic", 760),
		new CountryEntry("SZ", "SWZ", "Swaziland", 748),
		new CountryEntry("TC", "TCA", "Turks and Caicos Islands", 796),
		new CountryEntry("TD", "TCD", "Chad", 148),
		new CountryEntry("TF", "ATF", "French Southern Territories", 260),
		new CountryEntry("TG", "TGO", "Togo", 768),
		new CountryEntry("TH", "THA", "Thailand", 764),
		new CountryEntry("TJ", "TJK", "Tajikistan", 762),
		new CountryEntry("TK", "TKL", "Tokelau", 772),
		new CountryEntry("TL", "TLS", "Timor-Leste", 626),
		new CountryEntry("TM", "TKM", "Turkmenistan", 795),
		new CountryEntry("TN", "TUN", "Tunisia", 788),
		new CountryEntry("TO", "TON", "Tonga", 776),
		new CountryEntry("TR", "TUR", "Turkey", 792),
		new CountryEntry("TT", "TTO", "Trinidad and Tobago", 780),
		new CountryEntry("TV", "TUV", "Tuvalu", 798),
		new CountryEntry("TW", "TWN", "Taiwan, Province of China", 158),
		new CountryEntry("TZ", "TZA", "Tanzania, United Republic of", 834),
		new CountryEntry("UA", "UKR", "Ukraine", 804),
		new CountryEntry("UG", "UGA", "Uganda", 800),
		new CountryEntry("UM", "UMI", "United States Minor Outlying Islands", 581),
		new CountryEntry("US", "USA", "United States", 840),
		new CountryEntry("UY", "URY", "Uruguay", 858),
		new CountryEntry("UZ", "UZB", "Uzbekistan", 860),
		new CountryEntry("VA", "VAT", "Holy See (Vatican City State)", 336),
		new CountryEntry("VC", "VCT", "Saint Vincent and the Grenadines", 670),
		new CountryEntry("VE", "VEN", "Venezuela, Bolivarian Republic of", 862),
		new CountryEntry("VG", "VGB", "Virgin Islands, British", 92),
		new CountryEntry("VI", "VIR", "Virgin Islands, U.S.", 850),
		new CountryEntry("VN", "VNM", "Viet Nam", 704),
		new CountryEntry("VU", "VUT", "Vanuatu", 548),
		new CountryEntry("WF", "WLF", "Wallis and Futuna", 876),
		new CountryEntry("WS", "WSM", "Samoa", 882),
		new CountryEntry("YE", "YEM", "Yemen", 887),
		new CountryEntry("YT", "MYT", "Mayotte", 175),
		new CountryEntry("ZA", "ZAF", "South Africa", 710),
		new CountryEntry("ZM", "ZMB", "Zambia", 894),
		new CountryEntry("ZW", "ZWE", "Zimbabwe", 716)
	};

	/**
	 * Returns country numeric code for a given alpha 2 code, or negative value
	 * if code doesn't found.
	 *
	 * @param alpha2 the country alpha2 code.
	 * @return       the country numeric code.
	 */
	public static int getNumericCode(final String alpha2) {
		int left   = 0;
		int middle = 0;
		int right  = codes.length - 1;
		int value  = 0;

		while (left <= right) {
			middle = (right + left) / 2;
			value  = codes[middle].getAlpha2().compareTo(alpha2);

			if (value < 0) {
				left = middle + 1;
			} else if (value > 0) {
				right = middle - 1;
			} else {
				return codes[middle].getCode();
			}
		}

		return -1;
	}
}