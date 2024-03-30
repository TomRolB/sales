package org.example.utils;

import org.example.Product;
import org.example.Sale;
import org.example.Salesman;

import java.util.HashMap;

public class SaleBuilder {
    HashMap<Product, Integer> unitMap = new HashMap<>();

    public SaleBuilder add(Product product, int quantity) {
        unitMap.put(product, quantity);
        return this;
    }

    public Sale getSaleObject(int id, Salesman salesman) {
        return new Sale(id, salesman, unitMap);
    }

    public void clear() {
        unitMap.clear();
    }
}
