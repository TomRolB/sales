package org.example.utils;

import org.example.Product;
import org.example.Sale;
import org.example.Salesman;

import java.util.HashMap;

public class SaleBuilder {
    HashMap<Product, Sale.ProductInfo> unitMap = new HashMap<>();

    public SaleBuilder add(Product product, int quantity, double price) {
        unitMap.put(product, new Sale.ProductInfo(quantity, price));
        return this;
    }

    public Sale getSaleObject(int id, Salesman salesman) {
        return new Sale(id, salesman, unitMap);
    }

    public void clear() {
        unitMap.clear();
    }
}
