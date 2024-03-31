package org.example.searchers;

import org.example.Product;
import org.example.Sale;
import org.example.Salesman;
import org.example.SalesmanTable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

public class SalesmanProductSearcher implements ProductSearcher<Salesman> {
    SalesmanTable table;

    public SalesmanProductSearcher(SalesmanTable table) {
        this.table = table;
    }

    @Override
    public Collection<Product> getProducts(Salesman searchParam) {
        HashSet<Product> result = new HashSet<>();

        for (Sale sale: searchParam.getSales()) {
            for (Map.Entry<Product, Sale.ProductInfo> entry: sale.getUnits()) {
                result.add(entry.getKey());
            }
        }

        return result;
    }
}
