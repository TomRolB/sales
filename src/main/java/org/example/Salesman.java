package org.example;

import java.util.HashSet;
import java.util.Set;

public class Salesman {
    private final int id;
    private final String name;
    private double salary;
    private final Set<Sale> sales = new HashSet<>();

    public Salesman(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "id: " + id +
                "\nname: " + name +
                "\nsalary: " + salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Set<Sale> getSales() {
        return sales;
    }
    public void addSale(Sale sale) {
        sales.add(sale);
    }

    public double getCommission() {
        double totalRevenue = 0;
        int totalQuantity = 0;

        for (Sale sale: sales) {
            totalRevenue += sale.getRevenue();
            totalQuantity += sale.getTotalQuantity();
        }

        double commissionRate = totalQuantity > 2 ? 0.10 : 0.05;

        return totalRevenue * commissionRate;
    }

    public void deleteSaleIfPresent(Sale saleObj) {
        sales.remove(saleObj);
    }
}
