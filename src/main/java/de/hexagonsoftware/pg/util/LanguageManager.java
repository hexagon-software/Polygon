/*
 * Copyright (c) 2021, Hexagon Software
 * Licensed under BSD-3 Clause.
 * <https://hexagon-software.github.io/Polygon/license.html>
 */

package de.hexagonsoftware.pg.util;

import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

/**
 * The LanguageManager is used for easily translating
 * games using JSON files, this is achieved by, instead
 * of defining the strings for e.g. GUIs on spot, you
 * request a String from the LanguageManager using its
 * identifier, which then returns a string (if found) from
 * the currently loaded language JSON.
 *
 * @author Felix Eckert
 * */
public class LanguageManager {
    private JsonObject languages;
    private JsonObject currentLang;
    private Class<?> CLAZZ;

    /**
     * @param path The path to the Language List file (JSON)
     * @param defaultLang The language to be loaded by default
     * */
    public LanguageManager(String path, String defaultLang, Class<?> CLAZZ) {
        this.CLAZZ = CLAZZ;
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new InputStreamReader(CLAZZ.getResourceAsStream(path)));
        this.languages = gson.fromJson(reader, JsonObject.class);
        loadLanguage(defaultLang);
    }

    /**
     * Loads a different language from the language list file
     *
     * @param lang The name of the language
     * */
    public void loadLanguage(String lang) {
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new InputStreamReader(CLAZZ.getResourceAsStream(languages.get(lang).getAsString())));
        this.currentLang = gson.fromJson(reader, JsonObject.class);
    }

    /**
     * @param identifier The identifier the requested string is stored with in the language files
     * @return The requested string, if not found, it will return the identifier
     * */
    public String getString(String identifier) {
        return currentLang.has(identifier) ? currentLang.get(identifier).getAsString() : identifier;
    }
}
