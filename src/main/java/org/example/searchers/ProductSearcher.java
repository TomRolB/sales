package org.example.searchers;

import org.example.Product;

import java.util.Collection;

public interface ProductSearcher<T> {
    Collection<Product> getProducts(T searchParam);
}
