package org.example;

import java.util.HashMap;
import java.util.Map;

public class Sale {
    private final Salesman salesman;
    private final HashMap<Product, Integer> units;

    public Sale(Salesman salesman, HashMap<Product, Integer> products) {
        this.salesman = salesman;
        this.units = products;
        salesman.addSale(this);
    }


    public Salesman getSalesman() {
        return salesman;
    }

    public double getRevenue() {
        double totalRevenue = 0;
        for (Map.Entry<Product, Integer> entry: units.entrySet()) {
            double price = entry.getKey().getPrice();
            int quantity = entry.getValue();

            totalRevenue += price * quantity;
        }

        return totalRevenue;
    }

    public int getTotalQuantity() {
        int totalQuantity = 0;
        for (int quantity: units.values()) {
            totalQuantity += quantity;
        }

        return totalQuantity;
    }
}
