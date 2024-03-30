package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Sale {
    private final int id;
    private final Salesman salesman;
    private final HashMap<Product, ProductInfo> units;

    public Sale(int id, Salesman salesman, HashMap<Product, ProductInfo> units) {
        this.id = id;
        this.salesman = salesman;
        this.units = units;
        salesman.addSale(this);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(
                "id: " + id +
                "\nsalesman: " + salesman +
                "\n units: "
        );

        for (Map.Entry<Product, ProductInfo> entry: units.entrySet()) {
            int quantity = entry.getValue().quantity;
            double price = entry.getValue().price;

            result
                    .append("\n")
                    .append(entry.getKey().getName())
                    .append(" (").append(quantity).append(", ")
                    .append("$").append(price).append(")");
        }

        return result.toString();
    }

    public static class ProductInfo {
        public int quantity;
        public double price;

        public ProductInfo(int quantity, double price) {
            this.quantity = quantity;
            this.price = price;
        }
    }

    public int getId() {
        return id;
    }
    public Salesman getSalesman() {
        return salesman;
    }

    public double getRevenue() {
        double totalRevenue = 0;
        for (Map.Entry<Product, ProductInfo> entry: units.entrySet()) {
            int quantity = entry.getValue().quantity;
            double price = entry.getValue().price;

            totalRevenue += price * quantity;
        }

        return totalRevenue;
    }

    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (ProductInfo info: units.values()) {
            totalQuantity += info.quantity;
        }

        return totalQuantity;
    }

    public Set<Map.Entry<Product, ProductInfo>> getUnits() {
        return units.entrySet();
    }
}
