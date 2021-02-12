/*
 * Copyright (c) 2021, Hexagon Software
 * Licensed under BSD-3 Clause.
 * <https://hexagon-software.github.io/Polygon/license.html>
 */

package de.hexagonsoftware.pg.util;

import java.awt.*;
import java.util.Properties;

/**
 * Convenience class to avert NPEs when trying to
 * get a Property.
 * Also contains some methods to get Properties as
 * different Objects/Data Types.
 * */
public class PGProperties extends Properties {
	private static final long serialVersionUID = 5031063331601447224L;

	@Override
    public String getProperty(String name) {
        String out = super.getProperty(name);

        return out != null ? out : "NotFound";
    }

    /**
     * @param name Property name
     * @return The requested property as an Integer
     * */
    public int getPropertyAsInt(String name) {
        String out = super.getProperty(name);
        int num = 0;

        if (out == null) return num;

        try {
            num = Integer.parseInt(out);
        } catch (NumberFormatException e) {
            return 0;
        }

        return num;
    }
    
    /**
     * @param name Property name
     * @return The requested property as a Double
     * */
    public double getPropertyAsDouble(String name) {
        String out = super.getProperty(name);

        double num = 0;

        if (out == null) return num;

        try {
            num = Double.parseDouble(out);
        } catch (NumberFormatException e) {
            return 0;
        }

        return num;
    }

    /**
     * @param name Property name
     * @return The requested property as a Boolean
     * */
    public boolean getPropertyAsBool(String name) {
        String out = super.getProperty(name);
        return out != null ? Boolean.valueOf(out) : false;
    }

    /**
     * @param name Property name
     * @return The requested property as a Color
     * */
    public Color getPorpertyAsColor(String name) {
        String out = super.getProperty(name);
        return out != null ? Color.decode(out) : Color.BLACK;
    }
}
