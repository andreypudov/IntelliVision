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
 * The ISO 639-1 language codes with internal language indexes.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      LanguageCodes.java
 * %date      05:50:00 PM, Apr 05, 2014
 */
public class LanguageCodes {
    
    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellijustice.core.Manifest.NAME);
    
    private static final SortedMap<String, Property> LANGUAGES;
    private static final SortedMap<Integer, String>  INDEXES;
    
    static {
        final SortedMap<String, Property> languages = new TreeMap<>();
        final SortedMap<Integer, String>  indexes   = new TreeMap<>();
        
        languages.put("ab", new Property(0 , "Abkhaz", Script.CYRILLIC));  
        languages.put("aa", new Property(1 , "Afar", Script.LATIN));   
        languages.put("af", new Property(2 , "Afrikaans", Script.LATIN));   
        languages.put("ak", new Property(3 , "Akan", Script.LATIN));   
        languages.put("sq", new Property(4 , "Albanian", Script.LATIN));   
        languages.put("am", new Property(5 , "Amharic", Script.GEEZ));    
        languages.put("ar", new Property(6 , "Arabic", Script.ARABIC));  
        languages.put("an", new Property(7 , "Aragonese", Script.LATIN));   
        languages.put("hy", new Property(8 , "Armenian", Script.ARMENIAN)); 
        languages.put("as", new Property(9 , "Assamese", Script.BENGALI));
        languages.put("av", new Property(10, "Avaric", Script.CYRILLIC));  
        languages.put("ae", new Property(11, "Avestan", Script.AVESTAN));
        languages.put("ay", new Property(12, "Aymara", Script.LATIN));   
        languages.put("az", new Property(13, "Azerbaijani", Script.LATIN));   
        languages.put("bm", new Property(14, "Bambara", Script.LATIN));   
        languages.put("ba", new Property(15, "Bashkir", Script.CYRILLIC));  
        languages.put("eu", new Property(16, "Basque", Script.LATIN));   
        languages.put("be", new Property(17, "Belarusian", Script.CYRILLIC));  
        languages.put("bn", new Property(18, "Bengali", Script.BENGALI));
        languages.put("bh", new Property(19, "Bihari", Script.DEVANGARI));
        languages.put("bi", new Property(20, "Bislama", Script.LATIN));   
        languages.put("bs", new Property(21, "Bosnian", Script.LATIN));   
        languages.put("br", new Property(22, "Breton", Script.LATIN));   
        languages.put("bg", new Property(23, "Bulgarian", Script.CYRILLIC));  
        languages.put("my", new Property(24, "Burmese", Script.MON));     
        languages.put("ca", new Property(25, "Catalan", Script.LATIN));   
        languages.put("ch", new Property(26, "Chamorro", Script.LATIN));   
        languages.put("ce", new Property(27, "Chechen", Script.CYRILLIC));  
        languages.put("ny", new Property(28, "Chichewa", Script.LATIN));   
        languages.put("zh", new Property(29, "Chinese", Script.CHINESE));
        languages.put("cv", new Property(30, "Chuvash", Script.CYRILLIC));  
        languages.put("kw", new Property(31, "Cornish", Script.LATIN));   
        languages.put("co", new Property(32, "Corsican", Script.LATIN));   
        languages.put("cr", new Property(33, "Cree", Script.LATIN));   
        languages.put("hr", new Property(34, "Croatian", Script.LATIN));   
        languages.put("cs", new Property(35, "Czech", Script.LATIN));   
        languages.put("da", new Property(36, "Danish", Script.LATIN));   
        languages.put("dv", new Property(37, "Divehi", Script.DIVEHI));  
        languages.put("nl", new Property(38, "Dutch", Script.LATIN));   
        languages.put("dz", new Property(39, "Dzongkha", Script.TIBETAN)); 
        languages.put("en", new Property(40, "English", Script.LATIN));   
        languages.put("eo", new Property(41, "Esperanto", Script.LATIN));   
        languages.put("et", new Property(42, "Estonian", Script.LATIN));   
        languages.put("ee", new Property(43, "Ewe", Script.LATIN));   
        languages.put("fo", new Property(44, "Faroese", Script.LATIN));   
        languages.put("fj", new Property(45, "Fijian", Script.LATIN));   
        languages.put("fi", new Property(46, "Finnish", Script.LATIN));   
        languages.put("fr", new Property(47, "French", Script.LATIN));   
        languages.put("ff", new Property(48, "Fula", Script.LATIN));   
        languages.put("gl", new Property(49, "Galician", Script.LATIN));   
        languages.put("ka", new Property(50, "Georgian", Script.GEORGIAN));
        languages.put("de", new Property(51, "German", Script.LATIN));   
        languages.put("el", new Property(52, "Greek", Script.GREEK)); 
        languages.put("gn", new Property(53, "Guaran", Script.LATIN));   
        languages.put("gu", new Property(54, "Gujarati", Script.BRAHMIC)); 
        languages.put("ht", new Property(55, "Haitian", Script.LATIN));   
        languages.put("ha", new Property(56, "Hausa", Script.LATIN));   
        languages.put("he", new Property(57, "Hebrew", Script.HEBREW));
        languages.put("hz", new Property(58, "Herero", Script.LATIN));   
        languages.put("hi", new Property(59, "Hindi", Script.BRAHMIC)); 
        languages.put("ho", new Property(60, "Hiri Motu", Script.LATIN));   
        languages.put("hu", new Property(61, "Hungarian", Script.LATIN));   
        languages.put("ia", new Property(62, "Interlingua", Script.LATIN));   
        languages.put("id", new Property(63, "Indonesian", Script.LATIN));   
        languages.put("ie", new Property(64, "Interlingue", Script.LATIN));   
        languages.put("ga", new Property(65, "Irish", Script.LATIN));   
        languages.put("ig", new Property(66, "Igbo", Script.LATIN));   
        languages.put("ik", new Property(67, "Inupiaq", Script.LATIN));   
        languages.put("io", new Property(68, "Ido", Script.LATIN));   
        languages.put("is", new Property(69, "Icelandic", Script.LATIN));   
        languages.put("it", new Property(70, "Italian", Script.LATIN));   
        languages.put("iu", new Property(71, "Inuktitut", Script.INUKTITUT));
        languages.put("ja", new Property(72, "Japanese", Script.CHINESE)); 
        languages.put("jv", new Property(73, "Javanese", Script.LATIN));   
        languages.put("kl", new Property(74, "Kalaallisut", Script.LATIN));   
        languages.put("kn", new Property(75, "Kannada", Script.KANNADA));
        languages.put("kr", new Property(76, "Kanuri", Script.LATIN));   
        languages.put("ks", new Property(77, "Kashmiri", Script.ARABIC));  
        languages.put("kk", new Property(78, "Kazakh", Script.CYRILLIC));  
        languages.put("km", new Property(79, "Khmer", Script.KHMER));   
        languages.put("ki", new Property(80, "Kikuyu", Script.LATIN));   
        languages.put("rw", new Property(81, "Kinyarwanda", Script.LATIN));   
        languages.put("ky", new Property(82, "Kyrgyz", Script.CYRILLIC));  
        languages.put("kv", new Property(83, "Komi", Script.CYRILLIC));  
        languages.put("kg", new Property(84, "Kongo", Script.LATIN));   
        languages.put("ko", new Property(85, "Korean", Script.HANGUL));
        languages.put("ku", new Property(86, "Kurdish", Script.LATIN));   
        languages.put("kj", new Property(87, "Kwanyama", Script.LATIN));   
        languages.put("la", new Property(88, "Latin", Script.LATIN));   
        languages.put("lb", new Property(89, "Luxembourgish", Script.LATIN));   
        languages.put("lg", new Property(90, "Ganda", Script.LATIN));   
        languages.put("li", new Property(91, "Limburgish", Script.LATIN));   
        languages.put("ln", new Property(92, "Lingala", Script.LATIN));   
        languages.put("lo", new Property(93, "Lao", Script.LAO));    
        languages.put("lt", new Property(94, "Lithuanian", Script.LATIN));   
        languages.put("lu", new Property(95, "Luba-Katanga", Script.LATIN));   
        languages.put("lv", new Property(96, "Latvian", Script.LATIN));   
        languages.put("gv", new Property(97, "Manx", Script.LATIN));   
        languages.put("mk", new Property(98, "Macedonian", Script.CYRILLIC));  
        languages.put("mg", new Property(99, "Malagasy", Script.LATIN));   
        languages.put("ms", new Property(100, "Malay", Script.LATIN));   
        languages.put("ml", new Property(101, "Malayalam", Script.BRAHMIC)); 
        languages.put("mt", new Property(102, "Maltese", Script.LATIN));   
        languages.put("mi", new Property(103, "Māori", Script.LATIN));   
        languages.put("mr", new Property(104, "Marathi", Script.DEVANGARI));
        languages.put("mh", new Property(105, "Marshallese", Script.LATIN));   
        languages.put("mn", new Property(106, "Mongolian", Script.CYRILLIC));  
        languages.put("na", new Property(107, "Nauru", Script.LATIN));   
        languages.put("nv", new Property(108, "Navajo", Script.LATIN));   
        languages.put("nd", new Property(109, "Northern Ndebele", Script.LATIN));   
        languages.put("ne", new Property(110, "Nepali", Script.DEVANGARI));
        languages.put("ng", new Property(111, "Ndonga", Script.LATIN));   
        languages.put("nb", new Property(112, "Norwegian Bokmål", Script.LATIN));   
        languages.put("nn", new Property(113, "Norwegian Nynorsk", Script.LATIN));   
        languages.put("no", new Property(114, "Norwegian", Script.LATIN));   
        languages.put("ii", new Property(115, "Nuosu", Script.YI));
        languages.put("nr", new Property(116, "Southern Ndebele", Script.LATIN));   
        languages.put("oc", new Property(117, "Occitan", Script.LATIN));   
        languages.put("oj", new Property(118, "Ojibwe", Script.LATIN));   
        languages.put("cu", new Property(119, "Old Church Slavonic", Script.GLAGOLITIC));
        languages.put("om", new Property(120, "Oromo", Script.LATIN));   
        languages.put("or", new Property(121, "Oriya", Script.ORIYA));
        languages.put("os", new Property(122, "Ossetian", Script.CYRILLIC));  
        languages.put("pa", new Property(123, "Panjabi", Script.BRAHMIC)); 
        languages.put("pi", new Property(124, "Pāli", Script.BRAHMIC)); 
        languages.put("fa", new Property(125, "Persian", Script.ARABIC));  
        languages.put("pl", new Property(126, "Polish", Script.LATIN));   
        languages.put("ps", new Property(127, "Pashto", Script.ARABIC));  
        languages.put("pt", new Property(128, "Portuguese", Script.LATIN));   
        languages.put("qu", new Property(129, "Quechua", Script.LATIN));   
        languages.put("rm", new Property(130, "Romansh", Script.LATIN));   
        languages.put("rn", new Property(131, "Kirundi", Script.LATIN));   
        languages.put("ro", new Property(132, "Romanian", Script.LATIN));   
        languages.put("ru", new Property(133, "Russian", Script.CYRILLIC));  
        languages.put("sa", new Property(134, "Sanskrit", Script.DEVANGARI));
        languages.put("sc", new Property(135, "Sardinian", Script.LATIN));   
        languages.put("sd", new Property(136, "Sindhi", Script.ARABIC));  
        languages.put("se", new Property(137, "Northern Sami", Script.LATIN));   
        languages.put("sm", new Property(138, "Samoan", Script.LATIN));   
        languages.put("sg", new Property(139, "Sango", Script.LATIN));   
        languages.put("sr", new Property(140, "Serbian", Script.CYRILLIC));  
        languages.put("gd", new Property(141, "Scottish Gaelic", Script.LATIN));   
        languages.put("sn", new Property(142, "Shona", Script.LATIN));   
        languages.put("si", new Property(143, "Sinhala", Script.BRAHMIC)); 
        languages.put("sk", new Property(144, "Slovak", Script.LATIN));   
        languages.put("sl", new Property(145, "Slovene", Script.LATIN));   
        languages.put("so", new Property(146, "Somali", Script.LATIN));   
        languages.put("st", new Property(147, "Southern Sotho", Script.LATIN));   
        languages.put("es", new Property(148, "Spanish", Script.LATIN));   
        languages.put("su", new Property(149, "Sundanese", Script.LATIN));   
        languages.put("sw", new Property(150, "Swahili", Script.LATIN));   
        languages.put("ss", new Property(151, "Swati", Script.LATIN));   
        languages.put("sv", new Property(152, "Swedish", Script.LATIN));   
        languages.put("ta", new Property(153, "Tamil", Script.BRAHMIC)); 
        languages.put("te", new Property(154, "Telugu", Script.BRAHMIC)); 
        languages.put("tg", new Property(155, "Tajik", Script.CYRILLIC));  
        languages.put("th", new Property(156, "Thai", Script.THAI));  
        languages.put("ti", new Property(157, "Tigrinya", Script.GEEZ));    
        languages.put("bo", new Property(158, "Tibetan", Script.TIBETAN)); 
        languages.put("tk", new Property(159, "Turkmen", Script.LATIN));   
        languages.put("tl", new Property(160, "Tagalog", Script.LATIN));   
        languages.put("tn", new Property(161, "Tswana", Script.LATIN));   
        languages.put("to", new Property(162, "Tonga", Script.LATIN));   
        languages.put("tr", new Property(163, "Turkish", Script.LATIN));   
        languages.put("ts", new Property(164, "Tsonga", Script.LATIN));   
        languages.put("tt", new Property(165, "Tatar", Script.CYRILLIC));  
        languages.put("tw", new Property(166, "Twi", Script.LATIN));   
        languages.put("ty", new Property(167, "Tahitian", Script.LATIN));   
        languages.put("ug", new Property(168, "Uyghur", Script.ARABIC));  
        languages.put("uk", new Property(169, "Ukrainian", Script.CYRILLIC));  
        languages.put("ur", new Property(170, "Urdu", Script.ARABIC));  
        languages.put("uz", new Property(171, "Uzbek", Script.LATIN));   
        languages.put("ve", new Property(172, "Venda", Script.LATIN));   
        languages.put("vi", new Property(173, "Vietnamese", Script.LATIN));   
        languages.put("vo", new Property(174, "Volapük", Script.LATIN));   
        languages.put("wa", new Property(175, "Walloon", Script.LATIN));   
        languages.put("cy", new Property(176, "Welsh", Script.LATIN));   
        languages.put("wo", new Property(177, "Wolof", Script.LATIN));   
        languages.put("fy", new Property(178, "Western Frisian", Script.LATIN));   
        languages.put("xh", new Property(179, "Xhosa", Script.LATIN));   
        languages.put("yi", new Property(180, "Yiddish", Script.HEBREW));  
        languages.put("yo", new Property(181, "Yoruba", Script.LATIN));   
        languages.put("za", new Property(182, "Zhuang", Script.LATIN));   
        languages.put("zu", new Property(183, "Zulu", Script.LATIN));
        
        indexes.put(0 , "ab");
        indexes.put(1 , "aa");
        indexes.put(2 , "af");
        indexes.put(3 , "ak");
        indexes.put(4 , "sq");
        indexes.put(5 , "am");
        indexes.put(6 , "ar");
        indexes.put(7 , "an");
        indexes.put(8 , "hy");
        indexes.put(9 , "as");
        indexes.put(10, "av");
        indexes.put(11, "ae");
        indexes.put(12, "ay");
        indexes.put(13, "az");
        indexes.put(14, "bm");
        indexes.put(15, "ba");
        indexes.put(16, "eu");
        indexes.put(17, "be");
        indexes.put(18, "bn");
        indexes.put(19, "bh");
        indexes.put(20, "bi");
        indexes.put(21, "bs");
        indexes.put(22, "br");
        indexes.put(23, "bg");
        indexes.put(24, "my");
        indexes.put(25, "ca");
        indexes.put(26, "ch");
        indexes.put(27, "ce");
        indexes.put(28, "ny");
        indexes.put(29, "zh");
        indexes.put(30, "cv");
        indexes.put(31, "kw");
        indexes.put(32, "co");
        indexes.put(33, "cr");
        indexes.put(34, "hr");
        indexes.put(35, "cs");
        indexes.put(36, "da");
        indexes.put(37, "dv");
        indexes.put(38, "nl");
        indexes.put(39, "dz");
        indexes.put(40, "en");
        indexes.put(41, "eo");
        indexes.put(42, "et");
        indexes.put(43, "ee");
        indexes.put(44, "fo");
        indexes.put(45, "fj");
        indexes.put(46, "fi");
        indexes.put(47, "fr");
        indexes.put(48, "ff");
        indexes.put(49, "gl");
        indexes.put(50, "ka");
        indexes.put(51, "de");
        indexes.put(52, "el");
        indexes.put(53, "gn");
        indexes.put(54, "gu");
        indexes.put(55, "ht");
        indexes.put(56, "ha");
        indexes.put(57, "he");
        indexes.put(58, "hz");
        indexes.put(59, "hi");
        indexes.put(60, "ho");
        indexes.put(61, "hu");
        indexes.put(62, "ia");
        indexes.put(63, "id");
        indexes.put(64, "ie");
        indexes.put(65, "ga");
        indexes.put(66, "ig");
        indexes.put(67, "ik");
        indexes.put(68, "io");
        indexes.put(69, "is");
        indexes.put(70, "it");
        indexes.put(71, "iu");
        indexes.put(72, "ja");
        indexes.put(73, "jv");
        indexes.put(74, "kl");
        indexes.put(75, "kn");
        indexes.put(76, "kr");
        indexes.put(77, "ks");
        indexes.put(78, "kk");
        indexes.put(79, "km");
        indexes.put(80, "ki");
        indexes.put(81, "rw");
        indexes.put(82, "ky");
        indexes.put(83, "kv");
        indexes.put(84, "kg");
        indexes.put(85, "ko");
        indexes.put(86, "ku");
        indexes.put(87, "kj");
        indexes.put(88, "la");
        indexes.put(89, "lb");
        indexes.put(90, "lg");
        indexes.put(91, "li");
        indexes.put(92, "ln");
        indexes.put(93, "lo");
        indexes.put(94, "lt");
        indexes.put(95, "lu");
        indexes.put(96, "lv");
        indexes.put(97, "gv");
        indexes.put(98, "mk");
        indexes.put(99, "mg");
        indexes.put(100, "ms");
        indexes.put(101, "ml");
        indexes.put(102, "mt");
        indexes.put(103, "mi");
        indexes.put(104, "mr");
        indexes.put(105, "mh");
        indexes.put(106, "mn");
        indexes.put(107, "na");
        indexes.put(108, "nv");
        indexes.put(109, "nd");
        indexes.put(110, "ne");
        indexes.put(111, "ng");
        indexes.put(112, "nb");
        indexes.put(113, "nn");
        indexes.put(114, "no");
        indexes.put(115, "ii");
        indexes.put(116, "nr");
        indexes.put(117, "oc");
        indexes.put(118, "oj");
        indexes.put(119, "cu");
        indexes.put(120, "om");
        indexes.put(121, "or");
        indexes.put(122, "os");
        indexes.put(123, "pa");
        indexes.put(124, "pi");
        indexes.put(125, "fa");
        indexes.put(126, "pl");
        indexes.put(127, "ps");
        indexes.put(128, "pt");
        indexes.put(129, "qu");
        indexes.put(130, "rm");
        indexes.put(131, "rn");
        indexes.put(132, "ro");
        indexes.put(133, "ru");
        indexes.put(134, "sa");
        indexes.put(135, "sc");
        indexes.put(136, "sd");
        indexes.put(137, "se");
        indexes.put(138, "sm");
        indexes.put(139, "sg");
        indexes.put(140, "sr");
        indexes.put(141, "gd");
        indexes.put(142, "sn");
        indexes.put(143, "si");
        indexes.put(144, "sk");
        indexes.put(145, "sl");
        indexes.put(146, "so");
        indexes.put(147, "st");
        indexes.put(148, "es");
        indexes.put(149, "su");
        indexes.put(150, "sw");
        indexes.put(151, "ss");
        indexes.put(152, "sv");
        indexes.put(153, "ta");
        indexes.put(154, "te");
        indexes.put(155, "tg");
        indexes.put(156, "th");
        indexes.put(157, "ti");
        indexes.put(158, "bo");
        indexes.put(159, "tk");
        indexes.put(160, "tl");
        indexes.put(161, "tn");
        indexes.put(162, "to");
        indexes.put(163, "tr");
        indexes.put(164, "ts");
        indexes.put(165, "tt");
        indexes.put(166, "tw");
        indexes.put(167, "ty");
        indexes.put(168, "ug");
        indexes.put(169, "uk");
        indexes.put(170, "ur");
        indexes.put(171, "uz");
        indexes.put(172, "ve");
        indexes.put(173, "vi");
        indexes.put(174, "vo");
        indexes.put(175, "wa");
        indexes.put(176, "cy");
        indexes.put(177, "wo");
        indexes.put(178, "fy");
        indexes.put(179, "xh");
        indexes.put(180, "yi");
        indexes.put(181, "yo");
        indexes.put(182, "za");
        indexes.put(183, "zu");
        
        LANGUAGES = Collections.unmodifiableSortedMap(languages);
        INDEXES   = Collections.unmodifiableSortedMap(indexes);
    }

    /* do not let anyone instantiate this class */
    private LanguageCodes() {
    }
    
    /**
     * Returns a code of the language by its index.
     * 
     * @param index the index of the language.
     * 
     * @return     the language code for given language index, or
     *             empty string if language is not present.
     */
    public static String getCode(final int index) {
        final String code = INDEXES.get(index);
        
        /* negative value on null */
        if (code == null) {
            return "";
        }
        
        return code;
    }
    
    /**
     * Returns an index for given language name, or negative value if language
     * is not present in standard.
     * 
     * @param code the code of the language.
     * 
     * @return     the index of the language for given language name, or 
     *             negative value if language is not present.
     */
    public static int getIndex(final String code) {
        final Property property = LANGUAGES.get(code);
        
        /* negative value on null */
        if (property == null) {
            return -1;
        }
        
        return property.getIndex();
    }
    
    /**
     * Returns a long name of the language by its code.
     * 
     * @param code the code of the language.
     * 
     * @return     the long name of the language for given language name, or
     *             the same short name if language is not present.
     */
    public static String getName(final String code) {
        final Property property = LANGUAGES.get(code);
        
        /* negative value on null */
        if (property == null) {
            return code;
        }
        
        return property.getName();
    }
    
    /**
     * Returns a script of the language by its code.
     * 
     * @param code the code of the language.
     * 
     * @return     the script of the language or, or
     *             the same short name if language is not present.
     */
    public static String getScript(final String code) {
        final Property property = LANGUAGES.get(code);
        
        /* negative value on null */
        if (property == null) {
            return code;
        }
        
        return property.getScript().toString();
    }
    
    /**
     * The language writing system.
     */
    private enum Script {
        
        ARABIC("Arabic"),       ARMENIAN("Armenian"),   AVESTAN("Avestan"), 
        BENGALI("Bengali"),     BRAHMIC("Brahmic"),     CHINESE("Chinese"), 
        CYRILLIC("Cyrillic"),   DEVANGARI("Devangari"), DIVEHI("Divehi"), 
        GEEZ("Geez"),           GEORGIAN("Georgian"),   GLAGOLITIC("Glagolitic"), 
        GREEK("Greek"),         HANGUL("Hangul"),       HEBREW("Hebrew"), 
        INUKTITUT("Inuktitut"), KANNADA("Kannada"),     KHMER("Khmer"), 
        LAO("Lao"),             LATIN("Latin"),         MON("Mon"),
        ORIYA("Oriya"),         THAI("Thai"),           TIBETAN("Tibetan"), 
        YI("Yi");
        
        private final String value;
        
        Script(final String value) {
            this.value = value;
        }
        
        /**
         * Returns a string representation of the object.
         *
         * @return a string representation of the object.
         */
        @Override
        public String toString() {
            return value;
        }
    }
    
    /**
     * Language wrapper structure to work with language properties such as
     * language code, name and script.
     */
    private static class Property {
        
        private final int    index;
        private final String name;
        private final Script script;
 
        /**
         * Creates language property instance.
         * 
         * @param index  the index of the language.
         * @param name   the long name of the language.
         * @param script the script of the language.
         */
        Property(final int index, final String name, 
                final Script script) {
            this.index  = index;
            this.name   = name;
            this.script = script;
        }
        
        /**
         * Returns the index of the language.
         * 
         * @return the index of the language.
         */
        public int getIndex() {
            return index;
        }
        
        /**
         * Returns the long name of the language.
         * 
         * @return the long name of the language.
         */
        public String getName() {
            return name;
        }
        
        /**
         * Returns the script of the language.
         * 
         * @return the script of the language.
         */
        public Script getScript() {
            return script;
        }
    }
}
