package org.example;

import java.util.HashMap;

public class CategoryTable implements Table<Category>{
    private HashMap<Integer, Category> categories = new HashMap<>();
    int currentId = 0;
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
        return null;
    }
}
