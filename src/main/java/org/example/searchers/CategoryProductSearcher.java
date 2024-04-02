package org.example.searchers;

import org.example.Category;
import org.example.Product;
import org.example.ProductTable;

import java.util.Collection;
import java.util.HashSet;

public class CategoryProductSearcher implements ProductSearcher<Category>{
    ProductTable table;
    public CategoryProductSearcher(ProductTable table) {
        this.table = table;
    }

    @Override
    public Collection<Product> getProducts(Category searchParam) {
        HashSet<Product> result = new HashSet<>();

        for (Product product: table.getProducts()) {
            if (product.getCategory().equals(searchParam)) result.add(product);
        }

        return result;
    }


}
