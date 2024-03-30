package org.example.searchers;

import org.example.Category;
import org.example.Product;

import java.util.Collection;

public class CategoryProductSearcher implements ProductSearcher<Category>{
    @Override
    public Collection<Product> getProducts(Category searchParam) {
        return null;
    }
}
