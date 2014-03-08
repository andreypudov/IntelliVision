#!/bin/sh
echo 'Compilation started...'
javac Translator.java GeoName.java GeoNameIterator.java GeoNameReader.java CountryCodes.java GeoNameWriter.java AlternativeName.java AlternativeNameIterator.java AlternativeNameReader.java SQLFormat.java AdministrativeFirstName.java AdministrativeFirstNameIterator.java AdministrativeFirstNameReader.java AdministrativeSecondName.java AdministrativeSecondNameIterator.java AdministrativeSecondNameReader.java AdministrativeSecondNameWriter.java
echo 'Compilation finished...'
echo 'Type "java Translator" to execute the program.'