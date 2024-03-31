package org.example;

import java.util.Collection;
import java.util.HashMap;

public class ProductTable implements Table<Product>{
    private HashMap<Integer, Product> products = new HashMap<>();
    int currentId = 0;
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(
                "id\tname\tcategory\n"
        );

        for (Product product: products.values()) {
            result
                    .append("\n")
                    .append(product.getId()).append("\t")
                    .append(product.getName()).append("\t")
                    .append(product.getCategory().getName()).append("\t");
        }

        return result.toString();
    }
    @Override
    public Product getById(int id) {
        return products.get(id);
    }
    public void insert(String name, Category category) {
        products.put(currentId, new Product(currentId, name, category));
        currentId++;
    }

    public Product getByName(String product) {
        for (Product prod: products.values()) {
            if (prod.getName().equals(product)) return prod;
        }

        return null;
    }

    public Collection<Product> getAll() {
        return products.values();
    }
}
