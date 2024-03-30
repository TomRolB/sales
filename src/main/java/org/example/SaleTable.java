package org.example;

import org.example.utils.SaleBuilder;

import java.util.HashMap;

public class SaleTable implements Table<Sale> {
    private HashMap<Integer, Sale> sales = new HashMap<>();
    int currentId = 0;
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
