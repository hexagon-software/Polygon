package de.hexagonsoftware.pg.resources;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

public class FontResource {
    private Font font;

    public FontResource(String path, Class<?> CLASS, int type, float size) {
        try {
            font = Font.createFont(type, CLASS.getResourceAsStream(path)).deriveFont(size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public Font get() {return font;}
}
