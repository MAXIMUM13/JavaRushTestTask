package ru.maximum13.jrtest.model;

import java.util.Objects;

/**
 *
 * @author MAXIMUM13
 */
public class Todo {

    public enum Property {

        ID("id"),
        DESCRIPTION("description"),
        DONE("done");
        
        public final String name;

        private Property(String name) {
            this.name = name;
        }

        public static Property getByName(String name) {
            Property[] properties = values();
            for (Property p : properties) {
                if (p.name().equalsIgnoreCase(name)) {
                    return p;
                }
            }
            return ID;
        }
        
        public String getName() {
            return this.name().toLowerCase();
        }
    }
    
    public enum Category {

        ALL("all"),
        DONE("done"),
        NOT_DONE("not done");

        public final String name;

        private Category(String name) {
            this.name = name;
        }

        public static Category getByName(String name) {
            Category[] categories = values();
            for (Category c : categories) {
                if (c.name.equalsIgnoreCase(name)) {
                    return c;
                }
            }
            return ALL;
        }
    }

    private int id;
    private String description;
    private boolean done;

    public Todo() {
    }

    public Todo(String description) {
        this.description = description;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return this.done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.id);
        hash = 43 * hash + Objects.hashCode(this.description);
        hash = 43 * hash + Objects.hashCode(this.done);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final Todo other = (Todo) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }

        return Objects.equals(this.done, other.done);
    }

    @Override
    public String toString() {
        return "Todo{"
                + "id=" + this.id
                + ", description=" + this.description
                + ", done=" + this.done
                + '}';
    }
}
