package de.hexagonsoftware.pg.resources;

import de.hexagonsoftware.pg.util.ErrorHandler;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

public class FontResource {
    private Font font;

    public FontResource(String path, Class<?> CLASS, int type, float size) {
        try {
            font = Font.createFont(type, CLASS.getResourceAsStream(path)).deriveFont(size);
        } catch (FontFormatException | IOException e) {
            ErrorHandler.reportException(e);
        }
    }

    public Font get() {return font;}
}
