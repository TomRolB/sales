package org.example.searchers;

import org.example.Product;
import org.example.Sale;
import org.example.SaleTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

// Searches for products whose price has ever been 'searchParam'
public class PriceProductSearcher implements ProductSearcher<Integer> {
    SaleTable table;

    public PriceProductSearcher(SaleTable table) {
        this.table = table;
    }

    @Override
    public Collection<Product> getProducts(Integer searchParam) {
        HashSet<Product> result = new HashSet<>();

        for (Sale sale: table.getSales()) {
            for (Map.Entry<Product, Sale.ProductInfo> entry: sale.getUnits()) {
                if (entry.getValue().price == searchParam) {
                    result.add(entry.getKey());
                }
            }
        }

        return result;
    }

}
