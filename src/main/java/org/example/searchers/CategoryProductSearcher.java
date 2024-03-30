package org.example.searchers;

import org.example.Category;
import org.example.Product;
import org.example.ProductTable;
import org.example.Salesman;

import java.util.Collection;
import java.util.LinkedList;

public class CategoryProductSearcher implements ProductSearcher<Category>{
    ProductTable table;
    public CategoryProductSearcher(ProductTable table) {
        this.table = table;
    }

    @Override
    public Collection<Product> getProducts(Category searchParam) {
        Collection<Product> result = new LinkedList<>();

        for (Product product: table.getAll()) {
            if (product.getCategory().equals(searchParam)) result.add(product);
        }

        return result;
    }


}
