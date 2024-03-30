package org.example;

import java.util.HashMap;

public class ProductTable implements Table<Product>{
    private HashMap<Integer, Product> products = new HashMap<>();
    int currentId = 0;
    @Override
    public Product getById(int id) {
        return products.get(id);
    }
    public void insert(String name, double price, Category category) {
        products.put(currentId, new Product(currentId, name, price, category));
        currentId++;
    }

    public Product getByName(String product) {
        for (Product prod: products.values()) {
            if (prod.getName().equals(product)) return prod;
        }

        return null;
    }
}
