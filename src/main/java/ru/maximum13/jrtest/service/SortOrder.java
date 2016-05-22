package ru.maximum13.jrtest.service;

/**
 *
 * @author MAXIMUM13
 */
public enum SortOrder {

    ASC("asc", "ascending"),
    DESC("desc", "descending");

    public final String shortName;
    public final String name;

    private SortOrder(String shortName, String name) {
        this.shortName = shortName;
        this.name = name;
    }

    public static SortOrder getByName(String name) {
        SortOrder[] values = values();
        for (SortOrder value : values) {
            if (value.name.equalsIgnoreCase(name) || value.shortName.equalsIgnoreCase(name)) {
                return value;
            }
        }

        return ASC;
    }
}
