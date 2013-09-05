/*
 * IntelliJustice Intelligent Referee Assistant System
 *
 * The MIT License
 *
 * Copyright 2013 Andrey Pudov.
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Produces a Excel worksheet with a table of athlets listed in given PDF file.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      AthletsParser.java
 * %date      01:45:00 PM, Jul 03, 2013
 */
public class AthletsParser {

    private static class Competition {
        private String name;

        private String world;
        private String area;
        private String national;
        private String junior;
        private String juniorBest;
        private String mediterranean;
        private String season;

        private List<Athlete> athleteList;

        public Competition() {
            this("", "", "", "", "", "", "", "");
        }

        public Competition(final String name,          final String world,
                           final String area,          final String national,
                           final String junior,        final String juniorBest,
                           final String mediterranean, final String season) {
            this.name          = name;

            this.world         = world;
            this.area          = area;
            this.national      = national;
            this.junior        = junior;
            this.juniorBest    = juniorBest;
            this.mediterranean = mediterranean;
            this.season        = season;

            athleteList = new ArrayList<Athlete>(16);
        }

        public void addAthlete(Athlete athlete) {
            athleteList.add(athlete);
        }

        public List<Athlete> getAthleteList() {
            return Collections.unmodifiableList(athleteList);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getWorldRecord() {
            return world;
        }

        public void setWorldRecord(String world) {
            this.world = world;
        }

        public String getAreaRecord() {
            return area;
        }

        public void setAreaRecord(String area) {
            this.area = area;
        }

        public String getNationalRecord() {
            return national;
        }

        public void setNationalRecord(String national) {
            this.national = national;
        }

        public String getJuniorRecord() {
            return junior;
        }

        public void setJuniorRecord(String junior) {
            this.junior = junior;
        }

        public String getJuniorBest() {
            return juniorBest;
        }

        public void setJuniorBest(String juniorBest) {
            this.juniorBest = juniorBest;
        }

        public String getMediterraneanRecord() {
            return mediterranean;
        }

        public void setMediterraneanRecord(String mediterranean) {
            this.mediterranean = mediterranean;
        }

        public String getSeasonBest() {
            return season;
        }

        public void setSeasonBest(String season) {
            this.season = season;
        }

        @Override
        public String toString() {
            final StringBuilder result = new StringBuilder();

            return result.append(name).append(" "
                ).append(world).append(" "
                ).append(area).append(" "
                ).append(national).append(" "
                ).append(junior).append(" "
                ).append(mediterranean).append(" "
                ).append(season).toString();
        }
    }

    private static class Athlete {
        private final String firstName;
        private final String lastName;
        private final String country;
        private final String personal;
        private final String season;

        private String birthday;

        public Athlete(final String firstName, final String lastName,
                       final String country,   final String personal,
                       final String season) {
            this.firstName = firstName;
            this.lastName  = lastName;
            this.country   = country;
            this.personal  = personal;
            this.season    = season;
            this.birthday  = "";
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getCountry() {
            return country;
        }

        public String getPersonalBest() {
            return personal;
        }

        public String getSeasonBest() {
            return season;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(final String birthday) {
            this.birthday = birthday;
        }

        @Override
        public String toString() {
            final StringBuilder result = new StringBuilder();

            return result.append(firstName).append(" "
                ).append(lastName).append(" "
                ).append(country).append(" "
                ).append(personal).append(" "
                ).append(season).append(" "
                ).append(birthday).toString();
        }
    }

    private static boolean isCountry(final String string) {
        final String[] countryList = {"AFG",  "ALB",  "ALG",  "ASA",  "AND",
            "ANG",  "ANT",  "ARG",  "ARM",  "ARU",  "AUS",  "AUT",  "AZE",
            "BAH",  "BRN",  "BAN",  "BAR",  "BLR",  "BEL",  "BIZ",  "BER",
            "BEN",  "BHU",  "BOL",  "BIH",  "BOT",  "BRA",  "IVB",  "BRU",
            "BUL",  "BUR",  "BDI",  "CAM",  "CMR",  "CAN",  "CPV",  "CAY",
            "CAF",  "CHA",  "CHI",  "CHN",  "COL",  "COM",  "CGO",  "COD",
            "COK",  "CRC",  "CIV",  "CRO",  "CUB",  "CYP",  "CZE",  "DEN",
            "DJI",  "DMA",  "DOM",  "TLS",  "ECU",  "EGY",  "ESA",  "GEQ",
            "ERI",  "EST",  "ETH",  "FIJ",  "FIN",  "FRA",  "GAB",  "GAM",
            "GEO",  "GER",  "GHA",  "GRE",  "GRN",  "GUM",  "GUA",  "GUI",
            "GBS",  "GUY",  "HAI",  "HON",  "HKG",  "HUN",  "ISL",  "IND",
            "INA",  "IRI",  "IRQ",  "IRL",  "ISR",  "ITA",  "JAM",  "JPN",
            "JOR",  "KAZ",  "KEN",  "KIR",  "PRK",  "KOR",  "KUW",  "KGZ",
            "LAO",  "LAT",  "LIB",  "LES",  "LBR",  "LBA",  "LIE",  "LTU",
            "LUX",  "MKD",  "MAD",  "MAW",  "MAS",  "MDV",  "MLI",  "MLT",
            "MHL",  "MTN",  "MRI",  "MEX",  "FSM",  "MDA",  "MON",  "MGL",
            "MNE",  "MAR",  "MOZ",  "MYA",  "NAM",  "NRU",  "NEP",  "NED",
            "NZL",  "NCA",  "NIG",  "NGR",  "NOR",  "OMA",  "PAK",  "PLW",
            "PLE",  "PAN",  "PNG",  "PAR",  "PER",  "PHI",  "POL",  "POR",
            "PUR",  "QAT",  "ROU",  "RUS",  "RWA",  "SKN",  "LCA",  "VIN",
            "SAM",  "SMR",  "STP",  "KSA",  "SEN",  "SRB",  "SEY",  "SLE",
            "SIN",  "SVK",  "SLO",  "SOL",  "SOM",  "RSA",  "ESP",  "SRI",
            "SUD",  "SUR",  "SWZ",  "SWE",  "SUI",  "SYR",  "TPE",  "TJK",
            "TAN",  "THA",  "TOG",  "TGA",  "TRI",  "TUN",  "TUR",  "TKM",
            "TUV",  "UGA",  "UKR",  "UAE",  "GBR",  "USA",  "URU",  "UZB",
            "VAN",  "VEN",  "VIE",  "ISV",  "YEM",  "ZAM",  "ZIM"};

        for (String country : countryList) {
            if (country.equals(string)) {
                return true;
            }
        }

        return false;
    }

    private static boolean isFirstName(final String string) {
        char[] buffer = string.toCharArray();

        if ((buffer.length < 1) || (Character.isUpperCase(buffer[0]) == false)) {
            return true;
        }

        for (int index = 1; index < buffer.length; ++index) {
            if (Character.isLowerCase(buffer[index]) == false) {
                return false;
            }
        }

        return true;
    }

    private static boolean isLastName(final String string) {
        for (char character : string.toCharArray()) {
            if (Character.isUpperCase(character) == false) {
                return false;
            }
        }

        return true;
    }

    private static List<Competition> read(final File source) throws IOException {
        PdfReader              preader = new PdfReader(source.getAbsolutePath());
        PdfReaderContentParser parser  = new PdfReaderContentParser(preader);
        TextExtractionStrategy strategy;

        List<Competition> compList  = new ArrayList<Competition>(16);
        BufferedReader    reader    = null;
        String            line      = null;

        boolean menFlag    = false;
        boolean startList  = false;
        boolean startTitle = false;
        boolean recordList = false;

        for (int pageNumber = 1;
                pageNumber < preader.getNumberOfPages();
                ++pageNumber) {
            strategy = parser.processContent(pageNumber, new SimpleTextExtractionStrategy());
            reader   = new BufferedReader(new StringReader(strategy.getResultantText()));

            try {
                Competition competition = null;

                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("Men's")) {
                        menFlag = true;
                    } else if (line.startsWith("Women's")) {
                        menFlag = false;
                    }

                    if (line.startsWith("Start list")) {
                        competition = new Competition();
                        startList   = true;

                        competition.setName(((menFlag) ? "Men's " : "Women's ")
                            + line.substring("Start list ".length(),
                              line.indexOf(' ', "Start list ".length() + 1)));

                        continue;
                    }

                    if (startList && line.equals("Records")) {
                        startList  = false;
                        startTitle = false;
                        recordList = true;

                        continue;
                    }

                    /* validate start list title */
                    if (startList && !startTitle) {
                        if ((line.equals("Lane Bib Athlete Nat PB SB") == false)
                            && (line.equals("Order Bib Athlete Nat PB SB") == false)) {
                            System.err.printf("Error during parsing start list: title %s\n",
                                              line);
                            startList  = false;
                            startTitle = false;
                            recordList = true;
                        }

                        startTitle = true;

                        continue;
                    }

                    /* parsing start list content */
                    if (startList && startTitle) {
                        String[] list = line.split(" ");
                        int countryColumn = 0;

                        /*
                         * parsing strategy:
                         *  - find country column
                         *  - find first name
                         *  - find last name
                         *  - find personal best
                         *  - find season best if exists
                         */

                        /* incorrect input */
                        if (list.length < 6) {
                            continue;
                        }

                        for (int index = 4; index <= 6; ++index) {
                            if (isCountry(list[index])) {
                                countryColumn = index;
                                break;
                            }
                        }

                        /* can't find country column */
                        if (countryColumn == 0) {
                            continue;
                        }

                        String firstName = "";
                        String lastName  = "";
                        for (int index = 2; index < countryColumn; ++index) {
                            if (isFirstName(list[index])) {
                                firstName += list[index] + " ";
                            }

                            if (isLastName(list[index])) {
                                lastName += list[index] + " ";
                            }
                        }

                        Athlete athlete = new Athlete(firstName.trim(),
                            lastName.trim(), list[countryColumn],
                            (list.length > (countryColumn + 1) ? list[countryColumn + 1] : ""),
                            (list.length > (countryColumn + 2) ? list[countryColumn + 2] : ""));
                        competition.addAthlete(athlete);
                    }

                    /* parsing record list content */
                    if (recordList) {
                        if (line.startsWith("WR")) {
                            competition.setWorldRecord(line.substring("WR ".length(),
                                line.indexOf(' ', "WR ".length() + 1)));
                        } else if (line.startsWith("AR")) {
                            competition.setAreaRecord(line.substring("AR ".length(),
                                line.indexOf(' ', "AR ".length() + 1)));
                        } else if (line.startsWith("NR")) {
                            competition.setNationalRecord(line.substring("NR ".length(),
                                line.indexOf(' ', "NR ".length() + 1)));
                        } else if (line.startsWith("WJR")) {
                            competition.setJuniorRecord(line.substring("WJR ".length(),
                                line.indexOf(' ', "WJR ".length() + 1)));
                        } else if (line.startsWith("WJB")) {
                            competition.setJuniorBest(line.substring("WJB ".length(),
                                line.indexOf(' ', "WJB ".length() + 1)));
                        } else if (line.startsWith("MR")) {
                            competition.setMediterraneanRecord(line.substring("MR ".length(),
                                line.indexOf(' ', "MR ".length() + 1)));
                        } else if (line.startsWith("SB")) {
                            competition.setSeasonBest(line.substring("SB ".length(),
                                line.indexOf(' ', "SB ".length() + 1)));
                        }  else {
                            recordList = false;
                            compList.add(competition);
                        }
                    }
                }
            } catch(IOException e) {
                System.err.printf("Error during parsing %s\n",
                                  e.getMessage());
            }
        }

        reader.close();

        return compList;
    }

    private static void write(final File report, final List<Competition> list) {
        Workbook workbook = new XSSFWorkbook();
        Sheet   sheet     = null;
        Header  header    = null;
        Cell    cell      = null;
        Row     row       = null;

        for (Competition competition : list) {
            int rowIndex = 0;

            sheet = workbook.getSheet(competition.getName());
            if (sheet == null) {
                sheet = workbook.createSheet(competition.getName());
            }

            sheet.setColumnWidth(0, 32 * 1000);
            sheet.setColumnWidth(0, 10 * 1000);
            sheet.setColumnWidth(0, 10 * 1000);
            sheet.setColumnWidth(0, 10 * 1000);

            header = sheet.getHeader();
            header.setLeft(HSSFHeader.font("Arial", "Bold")
                + HSSFHeader.fontSize((short) 14)
                + competition.getName());

            Font fontHeader = workbook.createFont();
            fontHeader.setBoldweight(Font.BOLDWEIGHT_BOLD);
            fontHeader.setFontHeightInPoints((short) 14);

            Font fontTitle = workbook.createFont();
            fontTitle.setBoldweight(Font.BOLDWEIGHT_BOLD);
            fontTitle.setFontHeightInPoints((short) 11);

            Font fontName = workbook.createFont();
            fontName.setBoldweight(Font.BOLDWEIGHT_BOLD);
            fontName.setFontHeightInPoints((short) 11);

            CellStyle cellStyleHeader = workbook.createCellStyle();
            cellStyleHeader.setFont(fontHeader);

            CellStyle cellStyleTitle = workbook.createCellStyle();
            cellStyleTitle.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyleTitle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cellStyleTitle.setFont(fontTitle);

            CellStyle cellStyleTitleMiddle = workbook.createCellStyle();
            cellStyleTitleMiddle.setAlignment(CellStyle.ALIGN_CENTER);
            cellStyleTitleMiddle.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyleTitleMiddle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
            cellStyleTitleMiddle.setFont(fontTitle);

            CellStyle cellStyleName = workbook.createCellStyle();
            cellStyleName.setVerticalAlignment(CellStyle.VERTICAL_TOP);
            cellStyleName.setFont(fontName);

            CellStyle cellStyleMiddle = workbook.createCellStyle();
            cellStyleMiddle.setAlignment(CellStyle.ALIGN_CENTER);
            cellStyleMiddle.setVerticalAlignment(CellStyle.VERTICAL_TOP);

            row = sheet.createRow(rowIndex);
            cell = row.createCell(0);
            cell.setCellValue(competition.getName());
            cell.setCellStyle(cellStyleHeader);

            row = sheet.createRow(++rowIndex);
            cell = row.createCell(0);
            cell.setCellValue(((competition.getWorldRecord().length() > 0) ? ("WR: " + competition.getWorldRecord() + " ") : "")
                + ((competition.getAreaRecord().length() > 0)          ? ("AR: "  + competition.getAreaRecord() + " ") : "")
                + ((competition.getNationalRecord().length() > 0)      ? ("NR: "  + competition.getNationalRecord() + " ") : "")
                + ((competition.getJuniorRecord().length() > 0)        ? ("WJR: " + competition.getJuniorRecord() + " ") : "")
                + ((competition.getJuniorBest().length() > 0)          ? ("WJB: " + competition.getJuniorBest() + " ") : "")
                + ((competition.getMediterraneanRecord().length() > 0) ? ("MR: "  + competition.getMediterraneanRecord() + " ") : "")
                + ((competition.getSeasonBest().length() > 0)          ? ("SB: "  + competition.getSeasonBest() + " ") : ""));

            rowIndex += 2;
            row = sheet.createRow(rowIndex);
            row.setHeight((short) 580);
            cell = row.createCell(0);
            cell.setCellValue("Athlete");
            cell.setCellStyle(cellStyleTitle);

            cell = row.createCell(1);
            cell.setCellValue("Country");
            cell.setCellStyle(cellStyleTitleMiddle);

            cell = row.createCell(2);
            cell.setCellValue("PB");
            cell.setCellStyle(cellStyleTitleMiddle);

            cell = row.createCell(3);
            cell.setCellValue("SB");
            cell.setCellStyle(cellStyleTitleMiddle);

            for (Athlete athlete : competition.getAthleteList()) {
                rowIndex++;
                row = sheet.createRow(rowIndex);
                row.setHeight((short) 580);

                cell = row.createCell(0);
                cell.setCellValue(athlete.getLastName() + " " + athlete.getFirstName());
                cell.setCellStyle(cellStyleName);

                cell = row.createCell(1);
                cell.setCellValue(athlete.getCountry());
                cell.setCellStyle(cellStyleMiddle);

                cell = row.createCell(2);
                cell.setCellValue(athlete.getPersonalBest());
                cell.setCellStyle(cellStyleMiddle);

                cell = row.createCell(3);
                cell.setCellValue(athlete.getSeasonBest());
                cell.setCellStyle(cellStyleMiddle);
            }
        }

        try (FileOutputStream stream = new FileOutputStream(report)) {
            workbook.write(stream);
        } catch (IOException e) {
            System.err.printf("Unable to open given report file %s during writing\n",
                              report.getAbsolutePath());
            System.exit(Integer.MIN_VALUE);
        }
    }

    public static void main(final String[] args) {
        /* the PDF file to read */
        File source = null;

        /* the report file to write */
        File report = null;

        /* the help message string */
        final String help = "Usage: parser --source=filename --report=filename\n"
            + "\t--source\tthe PDF file to read\n"
            + "\t--report\tthe Excel file to write\n"
            + "\t--help\t\tprint this message and exit";

        /* parse command line arguments */
        for (String arg : args) {
            if (arg.equals("--help")) {
                System.out.println(help);
                System.exit(0);
            } else if (arg.startsWith("--source")) {
                source = new File(arg.substring("--source=".length()));

                if ((source.exists() == false) || (source.canRead() == false)) {
                    System.err.printf("Unable to open given source file %s\n",
                                      source.getAbsolutePath());
                    System.exit(Integer.MIN_VALUE);
                }
            } else if (arg.startsWith("--report")) {
                report = new File(arg.substring("--report=".length()));

                try {
                    if ((report.exists() == false) && (report.createNewFile() == false)) {
                        System.err.printf("Unable to create report file %s\n",
                                          report.getAbsolutePath());
                        System.exit(Integer.MIN_VALUE);
                    }
                } catch (IOException e) {
                    System.err.printf("Error during parsing %s\n",
                                      e.getMessage());
                    System.exit(Integer.MIN_VALUE);
                }

                if ((report.exists() == true) && (report.canWrite() == false)) {
                    System.err.printf("Unable to open given report file %s\n",
                                      report.getAbsolutePath());
                    System.exit(Integer.MIN_VALUE);
                }
            }
        }

        if ((source == null) || (report == null)) {
            System.err.printf("Invalid arguments: source file %s, report file %s\n",
                              (source == null) ? "not specified" : source,
                              (report == null) ? "not specified" : report);
            System.exit(Integer.MIN_VALUE);
        }

        try {
            List<Competition> list = read(source);
            write(report, list);
        } catch (IOException e) {
            System.err.printf("Error during parsing %s\n",
                              e.getMessage());
            System.exit(Integer.MIN_VALUE);
        }
    }
}
