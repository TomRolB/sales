package org.example;

import java.util.HashMap;

public class CategoryTable implements Table<Category>{
    private HashMap<Integer, Category> categories = new HashMap<>();
    int currentId = 0;
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(
                "id\tname\n"
        );

        for (Category category: categories.values()) {
            result
                    .append("\n")
                    .append(category.getId()).append("\t")
                    .append(category.getName()).append("\t");
        }

        return result.toString();
    }
    @Override
    public Category getById(int id) {
        return categories.get(id);
    }
    public void insert(String name) {
        categories.put(currentId, new Category(currentId, name));
        currentId++;
    }

    public Category getByName(String category) {
        for (Category cat: categories.values()) {
            if (cat.getName().equals(category)) return cat;
        }
        throw new IllegalArgumentException("Category " + category + " does not exist");
    }
}
