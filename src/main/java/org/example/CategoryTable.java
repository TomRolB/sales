package org.example;

import java.util.HashMap;

public class CategoryTable implements Table<Category>{
    private final HashMap<Integer, Category> categories = new HashMap<>();
    private final HashMap<String, Category> stringIndex = new HashMap<>();
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
        if (stringIndex.containsKey(name)) {
            System.out.println("Category " + name + " already exists");
        }

        Category category = new Category(currentId, name);
        categories.put(currentId, category);
        stringIndex.put(name, category);
        currentId++;
    }

    public Category getByName(String category) {
        return stringIndex.get(category);
    }

    public void delete(int id) {
        String categoryName = categories.get(id).getName();
        stringIndex.remove(categoryName);
        categories.remove(id);
    }
}
