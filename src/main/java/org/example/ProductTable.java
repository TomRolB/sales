package org.example;

import java.util.Collection;
import java.util.HashMap;

public class ProductTable implements Table<Product>{
    private final HashMap<Integer, Product> products = new HashMap<>();
    private final HashMap<String, Product> stringIndex = new HashMap<>();
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
        if (stringIndex.containsKey(name)) {
            System.out.println(("Product " + name + " already exists"));
            return;
        }

        Product product = new Product(currentId, name, category);
        products.put(currentId, product);
        stringIndex.put(name, product);
        currentId++;
    }

    public Product getByName(String product) {
        return stringIndex.get(product);
    }

    public Collection<Product> getProducts() {
        return products.values();
    }

    public void delete(int id) {
        String productName = products.get(id).getName();
        stringIndex.remove(productName);
        products.remove(id);
    }
}
