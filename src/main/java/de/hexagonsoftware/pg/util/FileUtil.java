/*
 * Copyright (c) 2021, Hexagon Software
 * Licensed under BSD-3 Clause.
 * <https://hexagon-software.github.io/Polygon/license.html>
 */

package de.hexagonsoftware.pg.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Properties;

public class FileUtil {
    /**
     * Loads a Properties file from a Jar-File
     *
     * @param path The internal path to the file.
     * @param CLASS The class to use for loading.
     * @author Felix Eckert
     * */
    public static Properties loadPropertiesFromJAR(String path, Class<?> CLASS) {
        Properties properties = new Properties();
        BufferedInputStream stream = null;
        try {
            stream = new BufferedInputStream(CLASS.getResourceAsStream(path));
            properties.load(stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * Loads a Properties file from a Jar-File as
     * a PGProerties
     *
     * @param path The internal path to the file.
     * @param CLASS The class to use for loading.
     * @author Felix Eckert
     * */
    public static PGProperties loadPGPropertiesFromJAR(String path, Class<?> CLASS) {
        PGProperties properties = new PGProperties();
        BufferedInputStream stream = null;
        try {
            stream = new BufferedInputStream(CLASS.getResourceAsStream(path));
            properties.load(stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
