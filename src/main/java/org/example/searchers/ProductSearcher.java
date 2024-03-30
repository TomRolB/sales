package org.example.searchers;

import org.example.Product;

import java.util.Collection;

public interface ProductSearcher<T> {
    Collection<Product> getProducts(T searchParam);
    default String getProductsAsTable(T searchParam) {
        Collection<Product> products = getProducts(searchParam);

        StringBuilder result = new StringBuilder(
                "id\tname\tcategory\n"
        );

        for (Product product: products) {
            result
                    .append("\n")
                    .append(product.getId()).append("\t")
                    .append(product.getName()).append("\t")
                    .append(product.getCategory().getName());
        }

        return result.toString();
    }
}
