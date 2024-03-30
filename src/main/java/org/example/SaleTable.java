package org.example;

import org.example.utils.SaleBuilder;

import java.util.HashMap;
import java.util.Map;

public class SaleTable implements Table<Sale> {
    private HashMap<Integer, Sale> sales = new HashMap<>();
    int currentId = 0;
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(
                "id\tsalesman\tunits\n"
        );

        for (Sale sale: sales.values()) {
            result
                    .append("\n")
                    .append(sale.getId()).append("\t")
                    .append(sale.getSalesman().getName()).append("\t");

            for (Map.Entry<Product, Integer> entry: sale.getUnits()) {
                Product product = entry.getKey();
                int quantity = entry.getValue();

                result.append(product.getName()).append(": ").append(quantity).append(", ");
            }
            result.delete(result.length()-2, result.length()); // Delete last comma
        }

        return result.toString();
    }

    @Override
    public Sale getById(int id) {
        return sales.get(id);
    }
    public void insert(Salesman salesman, HashMap<Product, Integer> units) {
        sales.put(currentId, new Sale(currentId, salesman, units));
        currentId++;
    }

    public void insert(SaleBuilder builder, Salesman salesman) {
        sales.put(currentId, builder.getSaleObject(currentId, salesman));
        currentId++;
    }
}
