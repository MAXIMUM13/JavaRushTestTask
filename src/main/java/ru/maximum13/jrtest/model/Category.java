package ru.maximum13.jrtest.model;

/**
 * @author MAXIMUM13
 * @since 14.07.2019
 */
public enum Category {

    ALL,
    DONE,
    NOT_DONE;

    private Category() {
    }

    public static Category getByName(String name) {
        Category[] categories = values();
        for (Category category : categories) {
            if (category.name().equalsIgnoreCase(name)) {
                return category;
            }
        }
        return ALL;
    }
}
