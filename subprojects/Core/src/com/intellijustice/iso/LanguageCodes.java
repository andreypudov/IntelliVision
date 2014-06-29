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
    
    static {
        final SortedMap<String, Property> languages = new TreeMap<>();
        
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
        
        LANGUAGES = Collections.unmodifiableSortedMap(languages);
    }

    /* do not let anyone instantiate this class */
    private LanguageCodes() {
    }
    
    /**
     * Returns an index for given language name, or negative value if language
     * is not present in standard.
     * 
     * @param name the short name of the language.
     * 
     * @return     the index of the language for given language name, or 
     *             negative value if language is not present.
     */
    public static int getIndex(final String name) {
        final Property property = LANGUAGES.get(name);
        
        /* negative value on null */
        if (property == null) {
            return -1;
        }
        
        return property.getIndex();
    }
    
    /**
     * Returns a long name of the language by its short variant.
     * 
     * @param name the short name of the language.
     * 
     * @return     the long name of the language for given language name, or
     *             the same short name if language is not present.
     */
    public static String getName(final String name) {
        final Property property = LANGUAGES.get(name);
        
        /* negative value on null */
        if (property == null) {
            return name;
        }
        
        return property.getName();
    }
    
    /**
     * Returns a script of the language by its short name.
     * 
     * @param name the short name of the language.
     * 
     * @return     the script of the language or, or
     *             the same short name if language is not present.
     */
    public static String getScript(final String name) {
        final Property property = LANGUAGES.get(name);
        
        /* negative value on null */
        if (property == null) {
            return name;
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
