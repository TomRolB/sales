package org.example;

import org.example.utils.SaleBuilder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SaleTable implements Table<Sale> {
    private final HashMap<Integer, Sale> sales = new HashMap<>();
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

            for (Map.Entry<Product, Sale.ProductInfo> entry: sale.getUnits()) {
                String productName = entry.getKey().getName();
                int quantity = entry.getValue().quantity;
                double price = entry.getValue().price;

                result
                        .append(productName)
                        .append(" (").append(quantity).append(", ")
                        .append("$").append(price).append("), ");
            }
            result.delete(result.length()-2, result.length()); // Delete last comma
        }

        return result.toString();
    }

    @Override
    public Sale getById(int id) {
        return sales.get(id);
    }
    public void insert(HashMap<Product, Sale.ProductInfo> units, Salesman salesman) {
        sales.put(currentId, new Sale(currentId, salesman, units));
        currentId++;
    }

    public void insert(SaleBuilder builder, Salesman salesman) {
        sales.put(currentId, builder.getSaleObject(currentId, salesman));
        currentId++;
    }

    public Collection<Sale> getSales() {
        return sales.values();
    }

    public void delete(int id) {
        sales.remove(id);
    }
}
