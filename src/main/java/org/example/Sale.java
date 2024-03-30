package org.example;

import java.util.HashMap;
import java.util.Map;

public class Sale {
    private final int id;
    private final Salesman salesman;
    private final HashMap<Product, Integer> units;

    public Sale(int id, Salesman salesman, HashMap<Product, Integer> units) {
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

        for (Map.Entry<Product, Integer> entry: units.entrySet()) {
            result.append("\n").append(entry.getKey().getName()).append(":").append(entry.getValue());
        }

        return result.toString();
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
