package org.example;

public class Category {
    private final int id;
    private final String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "id: " + id +
                "\nname: " + name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
