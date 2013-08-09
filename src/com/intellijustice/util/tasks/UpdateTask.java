/*
 * IntelliJustice Intelligent Referee Assistant System
 *
 * The MIT License
 *
 * Copyright 2011-2013 Andrey Pudov.
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

package com.intellijustice.util.tasks;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.ProcessBuilder.Redirect;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.text.MessageFormat;

import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

/**
 * The application updating task.
 *
 * Binary updating algorithm:
 * 0) Remove temporary files named with 'download' extension.
 * 1) Downloading manifest file to the updates directory and construct package
 *    manifest representation based on this file.
 * 2) Downloads a binary file from the link given in manifest file.
 * 3) If file is already exists, cancel updating process.
 * 4) Validate checksum of the file by value given in manifest file.
 * 5) Extract content of downloaded update to updates directory.
 *
 * @author    Andrey Pudov        <mail@andreypudov.com>
 * @version   0.00.00
 * %name      UpdateTask.java
 * %date      09:30:00 AM, Aug 07, 2013
 */
public class UpdateTask implements Runnable {

    private static final java.util.logging.Logger LOG
            = java.util.logging.Logger.getLogger(
            com.intellijustice.core.Manifest.NAME);

    /* updates configuration */
    private static final String PACKAGE_MANIFEST  =
            "http://onlineathletics.github.com/packages/manifest.xml";
    private static final String MANIFEST_LOCATION =
            "com/intellijustice/resources/schemas/Manifest.xml";

    /* configuration file structure */
    private static final String FILE_SEPARATOR =
            System.getProperty("file.separator");
    private static final String HOME_DIRECTORY =
            System.getProperty("user.home");
    private static final String CONFIG_DIRECTORY = HOME_DIRECTORY
            + FILE_SEPARATOR + "." + com.intellijustice.core.Manifest.NAME;
    private static final String UPDATES_DIRECTORY = CONFIG_DIRECTORY
            + FILE_SEPARATOR + "updates";
    private static final String MANIFEST_FILE = UPDATES_DIRECTORY
            + FILE_SEPARATOR + "manifest.xml";

    static {
        final File[] directoryList = {
            /* .IntelliJustice */
            new File(CONFIG_DIRECTORY),
            new File(UPDATES_DIRECTORY)
        };

        for (File directory : directoryList) {
            if (!(directory.exists()) || !(directory.isDirectory())) {
                directory.delete();
                directory.mkdirs();
            }
        }
    }

    @Override
    public void run() {
        try {
            File d = new File("C:\\Users\\apudov\\Desktop\\ssss");
        d.createNewFile();

            /* downloads manifest file */
            downloadFile(new URL(PACKAGE_MANIFEST), MANIFEST_FILE);

            /* read manifest file */
            final Package pkg = getPackage();

            /* download update is available version is greater than current */
            if ((pkg != null) && (pkg.getVersion()
                    > getVersion(com.intellijustice.core.Manifest.VERSION))) {
                LOG.info("Updating application files...");
                
                /* .IntelliJustice/updates/IntelliJustice_1_ALPHA.zip */
                final String name = UPDATES_DIRECTORY + FILE_SEPARATOR
                        + "IntelliJustice_" + Long.toString(pkg.getVersion())
                        + "_" + pkg.getStage() + ".zip";
                final File download = new File(name + ".download");
                final File update   = new File(name);

                /* remove temporary files */
                for (File file : new File(UPDATES_DIRECTORY).listFiles(
                        new FileFilter() {
                            @Override
                            public boolean accept(File pathname) {
                                return pathname.getName().toLowerCase(
                                        ).endsWith(".download");
                            }})) {
                    file.delete();
                }

                /* prepare files to be installed */
                if (download.exists()) {
                    LOG.info("Update already installed.");
                    return;
                }

                /* download update and validate checksum */
                downloadFile(pkg.getLink(), download.getAbsolutePath());

                if (pkg.getChecksum().equalsIgnoreCase(
                        getChecksum(download)) != true) {
                    LOG.warning("Downloaded update is corrupted.");
                    return;
                }

                /* remove download extension */
                update.delete();
                download.renameTo(update);
                extractUpdate(update);

                LOG.info("Update has been installed.");
            }
        } catch (IOException | ParserConfigurationException
                | SAXException | NoSuchAlgorithmException e) {
            LOG.info("Could not download and install updates.");
            LOG.info(e.getMessage());
        }
    }

    /**
     * Launch latest application binary from updates if exists. Method obtaining
     * version of binary, extracted to the update directory, and if the version
     * of this binary is greater (newly) than version of application itself,
     * new Java Virtual Machine will be created to execute that binary.
     */
    public static void launch() {
        final String update = UPDATES_DIRECTORY + FILE_SEPARATOR
                + "IntelliJustice.jar";

        final long current = getVersion(com.intellijustice.core.Manifest.VERSION);
        final long binary  = getVersion(new File(update));

        if (binary > current) {
            LOG.log(Level.INFO, "Updated version has beed detected [{0}:{1}].",
                    new Object[] {current, binary});

            try {
                final String java = System.getProperty("java.home")
                    + FILE_SEPARATOR + "bin" + FILE_SEPARATOR + "java";

                final ProcessBuilder builder = new ProcessBuilder(
                        new String[] {java, "-jar", update});
                builder.redirectOutput(Redirect.INHERIT);
                builder.redirectError(Redirect.INHERIT);

                /* launch updated version */
                final Process process = builder.start();

                System.exit(process.waitFor());
            } catch (IOException | InterruptedException e) {
                LOG.log(Level.WARNING, "Could not execute updated version: {0}",
                        e.getMessage());
            }
        }
    }

    /**
     * Returns long represents given version in string. It's possible to provide
     * a string contains dots and any other characters as a separators.
     *
     * @param version the string contains version number.
     * @return        the long number represents given version.
     *                Zero will be returned on error.
     */
    private static long getVersion(final String version) {
        final StringBuilder builder = new StringBuilder(16);

        for (char character : version.toCharArray()) {
            if (Character.isDigit(character)) {
                builder.append(character);
            }
        }

        try {
            return Long.parseLong(builder.toString());
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    /**
     * Returns version of specified binary. Method reads a manifest file inside
     * given jar and returns value for version key.
     *
     * @param binary the application binary.
     * @return       the long number represents given version.
     *               Zero will be returned on error.
     */
    private static long getVersion(final File binary) {
        try {
            final ZipFile                         zipFile = new ZipFile(binary);
            final Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while(entries.hasMoreElements()){
                final ZipEntry entry = entries.nextElement();

                if (entry.getName().equals(MANIFEST_LOCATION)) {
                    final Properties manifest = new Properties();

                    /* parse manifest file */
                    manifest.loadFromXML(zipFile.getInputStream(entry));

                    return getVersion(manifest.getProperty("VERSION"));
                }
            }
        } catch (IOException | NumberFormatException e) {
            return 0L;
        }

        return 0L;
    }

    /**
     * Downloads file from given network location to the specified location.
     *
     * @param link     the link to the file.
     * @param location the file name to save downloaded content.
     *
     * @throws MalformedURLException the source of an exception.
     * @throws IOException           the source of an exception.
     */
    private void downloadFile(final URL address, final String location)
            throws MalformedURLException, IOException {
        final HttpURLConnection connection = getConnection(address);

        HttpURLConnection.setFollowRedirects(true);
        connection.setConnectTimeout(3 * 1000);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent",
                "Mozilla/5.0 (iPad; CPU OS 6_0 like Mac OS X) "
                + "AppleWebKit/536.26 (KHTML, like Gecko) "
                + "Version/6.0 Mobile/10A5355d Safari/8536.25");
        connection.connect();

        try (OutputStream stream = new FileOutputStream(location)) {
            int    read;
            byte[] bytes = new byte[1024];

            while ((read = connection.getInputStream().read(bytes)) != -1) {
                stream.write(bytes, 0, read);
            }
        }

        connection.disconnect();
    }

    /**
     * Reads manifest file and constructs manifest representation.
     *
     * @return the package information.
     *
     * @throws ParserConfigurationException the source of an exception.
     * @throws SAXException                 the source of an exception.
     * @throws IOException                  the source of an exception.
     */
    private Package getPackage()
            throws ParserConfigurationException, SAXException, IOException {
        /* the manifest file parser */
        final PackageHandler handler = new PackageHandler();

        /* the manifest file parser */
        final SAXParserFactory factory = SAXParserFactory.newInstance();
        final SAXParser parser = factory.newSAXParser();

        /* the manifest file format definition */
        final SchemaFactory schemaFactory = SchemaFactory.newInstance(
                XMLConstants.W3C_XML_SCHEMA_NS_URI);
        final Schema schema = schemaFactory.newSchema(
                UpdateTask.class.getResource(
                "/com/intellijustice/resources/schemas/PackageSchema.xsd"));

        /* the manifest file validator */
        final Validator validator = schema.newValidator();

        /* validate and parse manifest file */
        validator.validate(new StreamSource(new File(MANIFEST_FILE)));

        parser.parse(new File(MANIFEST_FILE), handler);

        return handler.getPackage();
    }

    /**
     * Determines proxy server settings and establish HTTP connection.
     *
     * @param url the URL to the manifest file.
     * @return    the HTTP connection.
     *
     * @throws MalformedURLException the source of an exception.
     * @throws IOException           the source of an exception.
     */
    private HttpURLConnection getConnection(final URL url)
            throws MalformedURLException, IOException {
        HttpURLConnection connection = null;

        try {
            List<Proxy> list = ProxySelector.getDefault().select(
                    new URI("http://onlineathletics.github.com/"));

            for (Proxy entry : list) {
                InetSocketAddress address = (InetSocketAddress) entry.address();
                if (address != null) {
                    LOG.log(Level.INFO,
                        "Proxy server {0}:{1} will be used to download updates.",
                         new Object[] {address.getHostName(), address.getPort()});
                    return (HttpURLConnection) url.openConnection(entry);
                }
            }
        } catch (URISyntaxException e) {
            return null;
        }

        return (HttpURLConnection) url.openConnection();
    }

    /**
     * Generate SHA-512 hash to specified file.
     *
     * @param file the file to generate hash.
     * @return    the string contains SHA-512 hash.
     *
     * @throws NoSuchAlgorithmException the source of an exception.
     * @throws IOException              the source of an exception.
     */
    private String getChecksum(final File file)
            throws NoSuchAlgorithmException, IOException {
        MessageDigest message = MessageDigest.getInstance("SHA-512");
        StringBuilder builder = new StringBuilder(128);


        try (InputStream stream = new FileInputStream(file)) {
          DigestInputStream dis = new DigestInputStream(stream, message);

          /* read stream to EOF as normal... */
          while (dis.read() != -1) {
          }
        }

        byte[] digest = message.digest();

        for (int index = 0; index < digest.length; ++index) {
            builder.append(Integer.toString((digest[index] & 0xff ) + 0x100, 16
                    ).substring(1));
        }

        return builder.toString();
    }

    /**
     * Extracts downloaded update.
     *
     * @param update the file name of downloaded update archive.
     *
     * @throws ZipException the source of an exception.
     * @throws IOException  the source of an exception.
     */
    private void extractUpdate(final File update)
            throws ZipException, IOException {
        final int BUFFER_LENGTH = 2048;

        final ZipFile zip    = new ZipFile(update);
        final File    parent = update.getParentFile();

        new File(update.getParent()).mkdir();
        @SuppressWarnings("unchecked")
        Enumeration<ZipEntry> zipFileEntries = (Enumeration<ZipEntry>) zip.entries();

        while (zipFileEntries.hasMoreElements()) {
            ZipEntry entry        = zipFileEntries.nextElement();
            String   currentEntry = entry.getName();
            File     destFile     = new File(parent, currentEntry);
            //destFile = new File(newPath, destFile.getName());
            File destinationParent = destFile.getParentFile();

            // create the parent directory structure if needed
            destinationParent.mkdirs();

            if (entry.isDirectory() == false) {
                BufferedInputStream inputStream = new BufferedInputStream(
                        zip.getInputStream(entry));
                int currentByte;
                // establish buffer for writing file
                byte buffer[] = new byte[BUFFER_LENGTH];

                // write the current file to disk
                BufferedOutputStream outputStream = new BufferedOutputStream(
                        new FileOutputStream(destFile), BUFFER_LENGTH);

                // read and write until last byte is encountered
                while ((currentByte
                        = inputStream.read(buffer, 0, BUFFER_LENGTH)) != -1) {
                    outputStream.write(buffer, 0, currentByte);
                }
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            }

            if (currentEntry.endsWith(".zip")) {
                // found a zip file, try to open
                extractUpdate(destFile);
            }
        }
    }
}
