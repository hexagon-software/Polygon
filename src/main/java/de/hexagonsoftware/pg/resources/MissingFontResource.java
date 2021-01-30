package de.hexagonsoftware.pg.resources;

public class MissingFontResource extends FontResource {
    public MissingFontResource() {
        super("/lastResort.ttf", MissingFontResource.class, 0, 25f);
    }
}
