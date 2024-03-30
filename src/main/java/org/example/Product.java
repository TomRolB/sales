package org.example;

public class Product {
    private final int id;
    private final String name;
    private Category category;

    public Product(int id, String name, Category category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    @Override
    public String toString() {
        return "id: " + id +
                "\nname: " + name +
                "\ncategory: " + category.getName();
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
