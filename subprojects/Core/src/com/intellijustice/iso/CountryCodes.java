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

package com.intellijustice.iso;

import java.util.Collections;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * The ISO 3166-1 country codes.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      CountryCodes.java
 * %date      09:45:00 PM, Nov 08, 2014
 */
public class CountryCodes {
    
    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellijustice.core.Manifest.NAME);
    
    private static final SortedMap<Integer, Property> COUNTRIES;
    private static final SortedMap<String, Integer>   ALPHA3;
    
    static {
        final SortedMap<Integer, Property> countries = new TreeMap<>();
        final SortedMap<String, Integer>   alpha3    = new TreeMap<>();
        
        countries.put(4, new Property("Afghanistan", "AF", "AFG"));
        countries.put(248, new Property("Åland Islands", "AX", "ALA"));
        countries.put(0, new Property("Albania", "AL", "ALB"));
        countries.put(12, new Property("Algeria", "DZ", "DZA"));
        countries.put(16, new Property("American Samoa", "AS", "ASM"));
        countries.put(20, new Property("Andorra", "AD", "AND"));
        countries.put(24, new Property("Angola", "AO", "AGO"));
        countries.put(660, new Property("Anguilla", "AI", "AIA"));
        countries.put(10, new Property("Antarctica", "AQ", "ATA"));
        countries.put(28, new Property("Antigua and Barbuda", "AG", "ATG"));
        countries.put(32, new Property("Argentina", "AR", "ARG"));
        countries.put(51, new Property("Armenia", "AM", "ARM"));
        countries.put(533, new Property("Aruba", "AW", "ABW"));
        countries.put(36, new Property("Australia", "AU", "AUS"));
        countries.put(40, new Property("Austria", "AT", "AUT"));
        countries.put(31, new Property("Azerbaijan", "AZ", "AZE"));
        countries.put(44, new Property("Bahamas", "BS", "BHS"));
        countries.put(48, new Property("Bahrain", "BH", "BHR"));
        countries.put(50, new Property("Bangladesh", "BD", "BGD"));
        countries.put(52, new Property("Barbados", "BB", "BRB"));
        countries.put(112, new Property("Belarus", "BY", "BLR"));
        countries.put(56, new Property("Belgium", "BE", "BEL"));
        countries.put(84, new Property("Belize", "BZ", "BLZ"));
        countries.put(204, new Property("Benin", "BJ", "BEN"));
        countries.put(60, new Property("Bermuda", "BM", "BMU"));
        countries.put(64, new Property("Bhutan", "BT", "BTN"));
        countries.put(68, new Property("Bolivia, Plurinational State of", "BO", "BOL"));
        countries.put(535, new Property("Bonaire, Sint Eustatius and Saba", "BQ", "BES"));
        countries.put(70, new Property("Bosnia and Herzegovina", "BA", "BIH"));
        countries.put(72, new Property("Botswana", "BW", "BWA"));
        countries.put(74, new Property("Bouvet Island", "BV", "BVT"));
        countries.put(76, new Property("Brazil", "BR", "BRA"));
        countries.put(86, new Property("British Indian Ocean Territory", "IO", "IOT"));
        countries.put(96, new Property("Brunei Darussalam", "BN", "BRN"));
        countries.put(100, new Property("Bulgaria", "BG", "BGR"));
        countries.put(854, new Property("Burkina Faso", "BF", "BFA"));
        countries.put(108, new Property("Burundi", "BI", "BDI"));
        countries.put(116, new Property("Cambodia", "KH", "KHM"));
        countries.put(120, new Property("Cameroon", "CM", "CMR"));
        countries.put(124, new Property("Canada", "CA", "CAN"));
        countries.put(132, new Property("Cabo Verde", "CV", "CPV"));
        countries.put(136, new Property("Cayman Islands", "KY", "CYM"));
        countries.put(140, new Property("Central African Republic", "CF", "CAF"));
        countries.put(148, new Property("Chad", "TD", "TCD"));
        countries.put(152, new Property("Chile", "CL", "CHL"));
        countries.put(156, new Property("China", "CN", "CHN"));
        countries.put(162, new Property("Christmas Island", "CX", "CXR"));
        countries.put(166, new Property("Cocos (Keeling) Islands", "CC", "CCK"));
        countries.put(170, new Property("Colombia", "CO", "COL"));
        countries.put(174, new Property("Comoros", "KM", "COM"));
        countries.put(178, new Property("Congo", "CG", "COG"));
        countries.put(180, new Property("Congo, the Democratic Republic of the", "CD", "COD"));
        countries.put(184, new Property("Cook Islands", "CK", "COK"));
        countries.put(188, new Property("Costa Rica", "CR", "CRI"));
        countries.put(384, new Property("Côte d'Ivoire", "CI", "CIV"));
        countries.put(191, new Property("Croatia", "HR", "HRV"));
        countries.put(192, new Property("Cuba", "CU", "CUB"));
        countries.put(531, new Property("Curaçao", "CW", "CUW"));
        countries.put(196, new Property("Cyprus", "CY", "CYP"));
        countries.put(203, new Property("Czech Republic", "CZ", "CZE"));
        countries.put(208, new Property("Denmark", "DK", "DNK"));
        countries.put(262, new Property("Djibouti", "DJ", "DJI"));
        countries.put(212, new Property("Dominica", "DM", "DMA"));
        countries.put(214, new Property("Dominican Republic", "DO", "DOM"));
        countries.put(218, new Property("Ecuador", "EC", "ECU"));
        countries.put(818, new Property("Egypt", "EG", "EGY"));
        countries.put(222, new Property("El Salvador", "SV", "SLV"));
        countries.put(226, new Property("Equatorial Guinea", "GQ", "GNQ"));
        countries.put(232, new Property("Eritrea", "ER", "ERI"));
        countries.put(233, new Property("Estonia", "EE", "EST"));
        countries.put(231, new Property("Ethiopia", "ET", "ETH"));
        countries.put(238, new Property("Falkland Islands (Malvinas)", "FK", "FLK"));
        countries.put(234, new Property("Faroe Islands", "FO", "FRO"));
        countries.put(242, new Property("Fiji", "FJ", "FJI"));
        countries.put(246, new Property("Finland", "FI", "FIN"));
        countries.put(250, new Property("France", "FR", "FRA"));
        countries.put(254, new Property("French Guiana", "GF", "GUF"));
        countries.put(258, new Property("French Polynesia", "PF", "PYF"));
        countries.put(260, new Property("French Southern Territories", "TF", "ATF"));
        countries.put(266, new Property("Gabon", "GA", "GAB"));
        countries.put(270, new Property("Gambia", "GM", "GMB"));
        countries.put(268, new Property("Georgia", "GE", "GEO"));
        countries.put(276, new Property("Germany", "DE", "DEU"));
        countries.put(288, new Property("Ghana", "GH", "GHA"));
        countries.put(292, new Property("Gibraltar", "GI", "GIB"));
        countries.put(300, new Property("Greece", "GR", "GRC"));
        countries.put(304, new Property("Greenland", "GL", "GRL"));
        countries.put(308, new Property("Grenada", "GD", "GRD"));
        countries.put(312, new Property("Guadeloupe", "GP", "GLP"));
        countries.put(316, new Property("Guam", "GU", "GUM"));
        countries.put(320, new Property("Guatemala", "GT", "GTM"));
        countries.put(831, new Property("Guernsey", "GG", "GGY"));
        countries.put(324, new Property("Guinea", "GN", "GIN"));
        countries.put(624, new Property("Guinea-Bissau", "GW", "GNB"));
        countries.put(328, new Property("Guyana", "GY", "GUY"));
        countries.put(332, new Property("Haiti", "HT", "HTI"));
        countries.put(334, new Property("Heard Island and McDonald Islands", "HM", "HMD"));
        countries.put(336, new Property("Holy See (Vatican City State)", "VA", "VAT"));
        countries.put(340, new Property("Honduras", "HN", "HND"));
        countries.put(344, new Property("Hong Kong", "HK", "HKG"));
        countries.put(348, new Property("Hungary", "HU", "HUN"));
        countries.put(352, new Property("Iceland", "IS", "ISL"));
        countries.put(356, new Property("India", "IN", "IND"));
        countries.put(360, new Property("Indonesia", "ID", "IDN"));
        countries.put(364, new Property("Iran, Islamic Republic of", "IR", "IRN"));
        countries.put(368, new Property("Iraq", "IQ", "IRQ"));
        countries.put(372, new Property("Ireland", "IE", "IRL"));
        countries.put(833, new Property("Isle of Man", "IM", "IMN"));
        countries.put(376, new Property("Israel", "IL", "ISR"));
        countries.put(380, new Property("Italy", "IT", "ITA"));
        countries.put(388, new Property("Jamaica", "JM", "JAM"));
        countries.put(392, new Property("Japan", "JP", "JPN"));
        countries.put(832, new Property("Jersey", "JE", "JEY"));
        countries.put(400, new Property("Jordan", "JO", "JOR"));
        countries.put(398, new Property("Kazakhstan", "KZ", "KAZ"));
        countries.put(404, new Property("Kenya", "KE", "KEN"));
        countries.put(296, new Property("Kiribati", "KI", "KIR"));
        countries.put(408, new Property("Korea, Democratic People's Republic of", "KP", "PRK"));
        countries.put(410, new Property("Korea, Republic of", "KR", "KOR"));
        countries.put(414, new Property("Kuwait", "KW", "KWT"));
        countries.put(417, new Property("Kyrgyzstan", "KG", "KGZ"));
        countries.put(418, new Property("Lao People's Democratic Republic", "LA", "LAO"));
        countries.put(428, new Property("Latvia", "LV", "LVA"));
        countries.put(422, new Property("Lebanon", "LB", "LBN"));
        countries.put(426, new Property("Lesotho", "LS", "LSO"));
        countries.put(430, new Property("Liberia", "LR", "LBR"));
        countries.put(434, new Property("Libya", "LY", "LBY"));
        countries.put(438, new Property("Liechtenstein", "LI", "LIE"));
        countries.put(440, new Property("Lithuania", "LT", "LTU"));
        countries.put(442, new Property("Luxembourg", "LU", "LUX"));
        countries.put(446, new Property("Macao", "MO", "MAC"));
        countries.put(807, new Property("Macedonia, the former Yugoslav Republic of", "MK", "MKD"));
        countries.put(450, new Property("Madagascar", "MG", "MDG"));
        countries.put(454, new Property("Malawi", "MW", "MWI"));
        countries.put(458, new Property("Malaysia", "MY", "MYS"));
        countries.put(462, new Property("Maldives", "MV", "MDV"));
        countries.put(466, new Property("Mali", "ML", "MLI"));
        countries.put(470, new Property("Malta", "MT", "MLT"));
        countries.put(584, new Property("Marshall Islands", "MH", "MHL"));
        countries.put(474, new Property("Martinique", "MQ", "MTQ"));
        countries.put(478, new Property("Mauritania", "MR", "MRT"));
        countries.put(480, new Property("Mauritius", "MU", "MUS"));
        countries.put(175, new Property("Mayotte", "YT", "MYT"));
        countries.put(484, new Property("Mexico", "MX", "MEX"));
        countries.put(583, new Property("Micronesia, Federated States of", "FM", "FSM"));
        countries.put(498, new Property("Moldova, Republic of", "MD", "MDA"));
        countries.put(492, new Property("Monaco", "MC", "MCO"));
        countries.put(496, new Property("Mongolia", "MN", "MNG"));
        countries.put(499, new Property("Montenegro", "ME", "MNE"));
        countries.put(500, new Property("Montserrat", "MS", "MSR"));
        countries.put(504, new Property("Morocco", "MA", "MAR"));
        countries.put(508, new Property("Mozambique", "MZ", "MOZ"));
        countries.put(104, new Property("Myanmar", "MM", "MMR"));
        countries.put(516, new Property("Namibia", "NA", "NAM"));
        countries.put(520, new Property("Nauru", "NR", "NRU"));
        countries.put(524, new Property("Nepal", "NP", "NPL"));
        countries.put(528, new Property("Netherlands", "NL", "NLD"));
        countries.put(540, new Property("New Caledonia", "NC", "NCL"));
        countries.put(554, new Property("New Zealand", "NZ", "NZL"));
        countries.put(558, new Property("Nicaragua", "NI", "NIC"));
        countries.put(562, new Property("Niger", "NE", "NER"));
        countries.put(566, new Property("Nigeria", "NG", "NGA"));
        countries.put(570, new Property("Niue", "NU", "NIU"));
        countries.put(574, new Property("Norfolk Island", "NF", "NFK"));
        countries.put(580, new Property("Northern Mariana Islands", "MP", "MNP"));
        countries.put(578, new Property("Norway", "NO", "NOR"));
        countries.put(512, new Property("Oman", "OM", "OMN"));
        countries.put(586, new Property("Pakistan", "PK", "PAK"));
        countries.put(585, new Property("Palau", "PW", "PLW"));
        countries.put(275, new Property("Palestine, State of", "PS", "PSE"));
        countries.put(591, new Property("Panama", "PA", "PAN"));
        countries.put(598, new Property("Papua New Guinea", "PG", "PNG"));
        countries.put(600, new Property("Paraguay", "PY", "PRY"));
        countries.put(604, new Property("Peru", "PE", "PER"));
        countries.put(608, new Property("Philippines", "PH", "PHL"));
        countries.put(612, new Property("Pitcairn", "PN", "PCN"));
        countries.put(616, new Property("Poland", "PL", "POL"));
        countries.put(620, new Property("Portugal", "PT", "PRT"));
        countries.put(630, new Property("Puerto Rico", "PR", "PRI"));
        countries.put(634, new Property("Qatar", "QA", "QAT"));
        countries.put(638, new Property("Réunion", "RE", "REU"));
        countries.put(642, new Property("Romania", "RO", "ROU"));
        countries.put(643, new Property("Russian Federation", "RU", "RUS"));
        countries.put(646, new Property("Rwanda", "RW", "RWA"));
        countries.put(652, new Property("Saint Barthélemy", "BL", "BLM"));
        countries.put(654, new Property("Saint Helena, Ascension and Tristan da Cunha", "SH", "SHN"));
        countries.put(659, new Property("Saint Kitts and Nevis", "KN", "KNA"));
        countries.put(662, new Property("Saint Lucia", "LC", "LCA"));
        countries.put(663, new Property("Saint Martin (French part)", "MF", "MAF"));
        countries.put(666, new Property("Saint Pierre and Miquelon", "PM", "SPM"));
        countries.put(670, new Property("Saint Vincent and the Grenadines", "VC", "VCT"));
        countries.put(882, new Property("Samoa", "WS", "WSM"));
        countries.put(674, new Property("San Marino", "SM", "SMR"));
        countries.put(678, new Property("Sao Tome and Principe", "ST", "STP"));
        countries.put(682, new Property("Saudi Arabia", "SA", "SAU"));
        countries.put(686, new Property("Senegal", "SN", "SEN"));
        countries.put(688, new Property("Serbia", "RS", "SRB"));
        countries.put(690, new Property("Seychelles", "SC", "SYC"));
        countries.put(694, new Property("Sierra Leone", "SL", "SLE"));
        countries.put(702, new Property("Singapore", "SG", "SGP"));
        countries.put(534, new Property("Sint Maarten (Dutch part)", "SX", "SXM"));
        countries.put(703, new Property("Slovakia", "SK", "SVK"));
        countries.put(705, new Property("Slovenia", "SI", "SVN"));
        countries.put(90, new Property("Solomon Islands", "SB", "SLB"));
        countries.put(706, new Property("Somalia", "SO", "SOM"));
        countries.put(710, new Property("South Africa", "ZA", "ZAF"));
        countries.put(239, new Property("South Georgia and the South Sandwich Islands", "GS", "SGS"));
        countries.put(728, new Property("South Sudan", "SS", "SSD"));
        countries.put(724, new Property("Spain", "ES", "ESP"));
        countries.put(144, new Property("Sri Lanka", "LK", "LKA"));
        countries.put(729, new Property("Sudan", "SD", "SDN"));
        countries.put(740, new Property("Suriname", "SR", "SUR"));
        countries.put(744, new Property("Svalbard and Jan Mayen", "SJ", "SJM"));
        countries.put(748, new Property("Swaziland", "SZ", "SWZ"));
        countries.put(752, new Property("Sweden", "SE", "SWE"));
        countries.put(756, new Property("Switzerland", "CH", "CHE"));
        countries.put(760, new Property("Syrian Arab Republic", "SY", "SYR"));
        countries.put(158, new Property("Taiwan, Province of China", "TW", "TWN"));
        countries.put(762, new Property("Tajikistan", "TJ", "TJK"));
        countries.put(834, new Property("Tanzania, United Republic of", "TZ", "TZA"));
        countries.put(764, new Property("Thailand", "TH", "THA"));
        countries.put(626, new Property("Timor-Leste", "TL", "TLS"));
        countries.put(768, new Property("Togo", "TG", "TGO"));
        countries.put(772, new Property("Tokelau", "TK", "TKL"));
        countries.put(776, new Property("Tonga", "TO", "TON"));
        countries.put(780, new Property("Trinidad and Tobago", "TT", "TTO"));
        countries.put(788, new Property("Tunisia", "TN", "TUN"));
        countries.put(792, new Property("Turkey", "TR", "TUR"));
        countries.put(795, new Property("Turkmenistan", "TM", "TKM"));
        countries.put(796, new Property("Turks and Caicos Islands", "TC", "TCA"));
        countries.put(798, new Property("Tuvalu", "TV", "TUV"));
        countries.put(800, new Property("Uganda", "UG", "UGA"));
        countries.put(804, new Property("Ukraine", "UA", "UKR"));
        countries.put(784, new Property("United Arab Emirates", "AE", "ARE"));
        countries.put(826, new Property("United Kingdom", "GB", "GBR"));
        countries.put(840, new Property("United States", "US", "USA"));
        countries.put(581, new Property("United States Minor Outlying Islands", "UM", "UMI"));
        countries.put(858, new Property("Uruguay", "UY", "URY"));
        countries.put(860, new Property("Uzbekistan", "UZ", "UZB"));
        countries.put(548, new Property("Vanuatu", "VU", "VUT"));
        countries.put(862, new Property("Venezuela, Bolivarian Republic of", "VE", "VEN"));
        countries.put(704, new Property("Viet Nam", "VN", "VNM"));
        countries.put(92, new Property("Virgin Islands, British", "VG", "VGB"));
        countries.put(850, new Property("Virgin Islands, U.S.", "VI", "VIR"));
        countries.put(876, new Property("Wallis and Futuna", "WF", "WLF"));
        countries.put(732, new Property("Western Sahara", "EH", "ESH"));
        countries.put(887, new Property("Yemen", "YE", "YEM"));
        countries.put(894, new Property("Zambia", "ZM", "ZMB"));
        countries.put(716, new Property("Zimbabwe", "ZW", "ZWE"));

        alpha3.put("AFG", 4);
        alpha3.put("ALA", 248);
        alpha3.put("ALB", 8);
        alpha3.put("DZA", 12);
        alpha3.put("ASM", 16);
        alpha3.put("AND", 20);
        alpha3.put("AGO", 24);
        alpha3.put("AIA", 660);
        alpha3.put("ATA", 10);
        alpha3.put("ATG", 28);
        alpha3.put("ARG", 32);
        alpha3.put("ARM", 51);
        alpha3.put("ABW", 533);
        alpha3.put("AUS", 36);
        alpha3.put("AUT", 40);
        alpha3.put("AZE", 31);
        alpha3.put("BHS", 44);
        alpha3.put("BHR", 48);
        alpha3.put("BGD", 50);
        alpha3.put("BRB", 52);
        alpha3.put("BLR", 112);
        alpha3.put("BEL", 56);
        alpha3.put("BLZ", 84);
        alpha3.put("BEN", 204);
        alpha3.put("BMU", 60);
        alpha3.put("BTN", 64);
        alpha3.put("BOL", 68);
        alpha3.put("BES", 535);
        alpha3.put("BIH", 70);
        alpha3.put("BWA", 72);
        alpha3.put("BVT", 74);
        alpha3.put("BRA", 76);
        alpha3.put("IOT", 86);
        alpha3.put("BRN", 96);
        alpha3.put("BGR", 100);
        alpha3.put("BFA", 854);
        alpha3.put("BDI", 108);
        alpha3.put("KHM", 116);
        alpha3.put("CMR", 120);
        alpha3.put("CAN", 124);
        alpha3.put("CPV", 132);
        alpha3.put("CYM", 136);
        alpha3.put("CAF", 140);
        alpha3.put("TCD", 148);
        alpha3.put("CHL", 152);
        alpha3.put("CHN", 156);
        alpha3.put("CXR", 162);
        alpha3.put("CCK", 166);
        alpha3.put("COL", 170);
        alpha3.put("COM", 174);
        alpha3.put("COG", 178);
        alpha3.put("COD", 180);
        alpha3.put("COK", 184);
        alpha3.put("CRI", 188);
        alpha3.put("CIV", 384);
        alpha3.put("HRV", 191);
        alpha3.put("CUB", 192);
        alpha3.put("CUW", 531);
        alpha3.put("CYP", 196);
        alpha3.put("CZE", 203);
        alpha3.put("DNK", 208);
        alpha3.put("DJI", 262);
        alpha3.put("DMA", 212);
        alpha3.put("DOM", 214);
        alpha3.put("ECU", 218);
        alpha3.put("EGY", 818);
        alpha3.put("SLV", 222);
        alpha3.put("GNQ", 226);
        alpha3.put("ERI", 232);
        alpha3.put("EST", 233);
        alpha3.put("ETH", 231);
        alpha3.put("FLK", 238);
        alpha3.put("FRO", 234);
        alpha3.put("FJI", 242);
        alpha3.put("FIN", 246);
        alpha3.put("FRA", 250);
        alpha3.put("GUF", 254);
        alpha3.put("PYF", 258);
        alpha3.put("ATF", 260);
        alpha3.put("GAB", 266);
        alpha3.put("GMB", 270);
        alpha3.put("GEO", 268);
        alpha3.put("DEU", 276);
        alpha3.put("GHA", 288);
        alpha3.put("GIB", 292);
        alpha3.put("GRC", 300);
        alpha3.put("GRL", 304);
        alpha3.put("GRD", 308);
        alpha3.put("GLP", 312);
        alpha3.put("GUM", 316);
        alpha3.put("GTM", 320);
        alpha3.put("GGY", 831);
        alpha3.put("GIN", 324);
        alpha3.put("GNB", 624);
        alpha3.put("GUY", 328);
        alpha3.put("HTI", 332);
        alpha3.put("HMD", 334);
        alpha3.put("VAT", 336);
        alpha3.put("HND", 340);
        alpha3.put("HKG", 344);
        alpha3.put("HUN", 348);
        alpha3.put("ISL", 352);
        alpha3.put("IND", 356);
        alpha3.put("IDN", 360);
        alpha3.put("IRN", 364);
        alpha3.put("IRQ", 368);
        alpha3.put("IRL", 372);
        alpha3.put("IMN", 833);
        alpha3.put("ISR", 376);
        alpha3.put("ITA", 380);
        alpha3.put("JAM", 388);
        alpha3.put("JPN", 392);
        alpha3.put("JEY", 832);
        alpha3.put("JOR", 400);
        alpha3.put("KAZ", 398);
        alpha3.put("KEN", 404);
        alpha3.put("KIR", 296);
        alpha3.put("PRK", 408);
        alpha3.put("KOR", 410);
        alpha3.put("KWT", 414);
        alpha3.put("KGZ", 417);
        alpha3.put("LAO", 418);
        alpha3.put("LVA", 428);
        alpha3.put("LBN", 422);
        alpha3.put("LSO", 426);
        alpha3.put("LBR", 430);
        alpha3.put("LBY", 434);
        alpha3.put("LIE", 438);
        alpha3.put("LTU", 440);
        alpha3.put("LUX", 442);
        alpha3.put("MAC", 446);
        alpha3.put("MKD", 807);
        alpha3.put("MDG", 450);
        alpha3.put("MWI", 454);
        alpha3.put("MYS", 458);
        alpha3.put("MDV", 462);
        alpha3.put("MLI", 466);
        alpha3.put("MLT", 470);
        alpha3.put("MHL", 584);
        alpha3.put("MTQ", 474);
        alpha3.put("MRT", 478);
        alpha3.put("MUS", 480);
        alpha3.put("MYT", 175);
        alpha3.put("MEX", 484);
        alpha3.put("FSM", 583);
        alpha3.put("MDA", 498);
        alpha3.put("MCO", 492);
        alpha3.put("MNG", 496);
        alpha3.put("MNE", 499);
        alpha3.put("MSR", 500);
        alpha3.put("MAR", 504);
        alpha3.put("MOZ", 508);
        alpha3.put("MMR", 104);
        alpha3.put("NAM", 516);
        alpha3.put("NRU", 520);
        alpha3.put("NPL", 524);
        alpha3.put("NLD", 528);
        alpha3.put("NCL", 540);
        alpha3.put("NZL", 554);
        alpha3.put("NIC", 558);
        alpha3.put("NER", 562);
        alpha3.put("NGA", 566);
        alpha3.put("NIU", 570);
        alpha3.put("NFK", 574);
        alpha3.put("MNP", 580);
        alpha3.put("NOR", 578);
        alpha3.put("OMN", 512);
        alpha3.put("PAK", 586);
        alpha3.put("PLW", 585);
        alpha3.put("PSE", 275);
        alpha3.put("PAN", 591);
        alpha3.put("PNG", 598);
        alpha3.put("PRY", 600);
        alpha3.put("PER", 604);
        alpha3.put("PHL", 608);
        alpha3.put("PCN", 612);
        alpha3.put("POL", 616);
        alpha3.put("PRT", 620);
        alpha3.put("PRI", 630);
        alpha3.put("QAT", 634);
        alpha3.put("REU", 638);
        alpha3.put("ROU", 642);
        alpha3.put("RUS", 643);
        alpha3.put("RWA", 646);
        alpha3.put("BLM", 652);
        alpha3.put("SHN", 654);
        alpha3.put("KNA", 659);
        alpha3.put("LCA", 662);
        alpha3.put("MAF", 663);
        alpha3.put("SPM", 666);
        alpha3.put("VCT", 670);
        alpha3.put("WSM", 882);
        alpha3.put("SMR", 674);
        alpha3.put("STP", 678);
        alpha3.put("SAU", 682);
        alpha3.put("SEN", 686);
        alpha3.put("SRB", 688);
        alpha3.put("SYC", 690);
        alpha3.put("SLE", 694);
        alpha3.put("SGP", 702);
        alpha3.put("SXM", 534);
        alpha3.put("SVK", 703);
        alpha3.put("SVN", 705);
        alpha3.put("SLB", 90);
        alpha3.put("SOM", 706);
        alpha3.put("ZAF", 710);
        alpha3.put("SGS", 239);
        alpha3.put("SSD", 728);
        alpha3.put("ESP", 724);
        alpha3.put("LKA", 144);
        alpha3.put("SDN", 729);
        alpha3.put("SUR", 740);
        alpha3.put("SJM", 744);
        alpha3.put("SWZ", 748);
        alpha3.put("SWE", 752);
        alpha3.put("CHE", 756);
        alpha3.put("SYR", 760);
        alpha3.put("TWN", 158);
        alpha3.put("TJK", 762);
        alpha3.put("TZA", 834);
        alpha3.put("THA", 764);
        alpha3.put("TLS", 626);
        alpha3.put("TGO", 768);
        alpha3.put("TKL", 772);
        alpha3.put("TON", 776);
        alpha3.put("TTO", 780);
        alpha3.put("TUN", 788);
        alpha3.put("TUR", 792);
        alpha3.put("TKM", 795);
        alpha3.put("TCA", 796);
        alpha3.put("TUV", 798);
        alpha3.put("UGA", 800);
        alpha3.put("UKR", 804);
        alpha3.put("ARE", 784);
        alpha3.put("GBR", 826);
        alpha3.put("USA", 840);
        alpha3.put("UMI", 581);
        alpha3.put("URY", 858);
        alpha3.put("UZB", 860);
        alpha3.put("VUT", 548);
        alpha3.put("VEN", 862);
        alpha3.put("VNM", 704);
        alpha3.put("VGB", 92);
        alpha3.put("VIR", 850);
        alpha3.put("WLF", 876);
        alpha3.put("ESH", 732);
        alpha3.put("YEM", 887);
        alpha3.put("ZMB", 894);
        alpha3.put("ZWE", 716);

        COUNTRIES = Collections.unmodifiableSortedMap(countries);
        ALPHA3    = Collections.unmodifiableSortedMap(alpha3);
    }

    /* do not let anyone instantiate this class */
    private CountryCodes() {
    }
    
    /**
     * Returns a name for given country code, or empty string if country
     * is not present in standard.
     * 
     * @param code the numeric country code.
     * 
     * @return     the name of the country for given country code, or 
     *             empty strings not present.
     */
    public static String getName(final int code) {
        final Property property = COUNTRIES.get(code);
        
        /* negative value on null */
        if (property == null) {
            return "";
        }
        
        return property.getName();
    }
    
    /**
     * Returns numeric code for given three letters country code, or negative 
     * value if country is not present in standard.
     * 
     * @param alpha3 the three letters country code.
     * 
     * @return       the numeric code for given three letters country code, or 
     *               negative value if country is not present in standard
     */
    public static int getCodeByAlpha3(final String alpha3) {
        final Integer code = ALPHA3.get(alpha3);
        if (code == null) {
            return -1;
        }
        
        return code;
    }
    
    /**
     * Returns two letters code for given country code, or empty string if 
     * country is not present in standard.
     * 
     * @param code the numeric country code.
     * 
     * @return     the two letters code of the country for given country code, 
     *             or empty strings not present.
     */
    public static String getAlpha2(final int code) {
        final Property property = COUNTRIES.get(code);
        
        /* negative value on null */
        if (property == null) {
            return "";
        }
        
        return property.getAlpha2();
    }
    
    /**
     * Returns three letters code for given country code, or empty string if 
     * country is not present in standard.
     * 
     * @param code the numeric country code.
     * 
     * @return     the three letters code of the country for given country code, 
     *             or empty strings not present.
     */
    public static String getAlpha3(final int code) {
        final Property property = COUNTRIES.get(code);
        
        /* negative value on null */
        if (property == null) {
            return "";
        }
        
        return property.getAlpha3();
    }

    /**
     * Country wrapper structure to work with country properties such as
     * country code and name.
     */
    private static class Property {

        private final String name;
        private final String alpha2;
        private final String alpha3;

        /**
         * Creates country property instance.
         * 
         * @param name  the long name of the country.
         * @param alpha2 the two letters code of the country.
         * @param alpha3 the three letters code of the country.
         */
        Property(final String name, final String alpha2, final String alpha3) {
            this.name   = name;
            this.alpha2 = alpha2;
            this.alpha3 = alpha3;
        }

        /**
         * Returns the long name of the country.
         * 
         * @return the long name of the country.
         */
        public String getName() {
            return name;
        }
        
        /**
         * Return the two letters code of the country.
         * 
         * @return the two letters code of the country.
         */
        public String getAlpha2() {
            return alpha2;
        }
        
        /**
         * Return the three letters code of the country.
         * 
         * @return the three letters code of the country.
         */
        public String getAlpha3() {
            return alpha3;
        }
    }
}
