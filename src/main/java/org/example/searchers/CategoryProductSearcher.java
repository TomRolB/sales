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

    public String getProductsAsTable(Category searchParam) {
        Collection<Product> products = getProducts(searchParam);

        StringBuilder result = new StringBuilder(
                "id\tname\tprice\tcategory\n"
        );

        for (Product product: products) {
            result
                    .append("\n")
                    .append(product.getId()).append("\t")
                    .append(product.getName()).append("\t")
                    .append(product.getPrice()).append("\t")
                    .append(product.getCategory().getName());
        }

        return result.toString();
    }
}
