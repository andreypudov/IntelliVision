cls
javac -classpath lib/dom4j-1.6.1.jar;lib/itextpdf-5.4.2.jar;lib/poi-3.9-20121203.jar;lib/poi-ooxml-3.9-20121203.jar;lib/poi-ooxml-schemas-3.9-20121203.jar;lib/stax-api-1.0.1.jar;lib/xmlbeans-2.3.0.jar AthletsParser.java
jar cfm AthletsParser.jar manifest.mf *.class
java -jar .\AthletsParser.jar --source=source.pdf --report=REPORT.XLSX