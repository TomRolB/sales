package org.example;

public class Database {
    SalesmanTable salesman = new SalesmanTable();
    SaleTable sale = new SaleTable();
    ProductTable product = new ProductTable();
    CategoryTable category = new CategoryTable();
    public void deleteSalesman(int id) {
        Salesman salesmanObj = salesman.getById(id);

        if (salesmanObj == null) {
            System.out.println("There is no salesman with an id of " + id);
            return;
        }

        if (!salesmanObj.getSales().isEmpty()) {
            System.out.println("Can not delete salesman: there are sales depending on this element");
            return;
        }

        salesman.delete(id);
    }

    public void deleteSale(int id) {
        Sale saleObj = sale.getById(id);
        if (saleObj == null) {
            System.out.println("There is no sale with an id of " + id);
            return;
        }

        for (Salesman salesmanObj: salesman.getSalesmen()) {
            salesmanObj.deleteSaleIfPresent(saleObj);
        }

        sale.delete(id);
    }

    public void deleteProduct(int id) {
        Product productObj = product.getById(id);

        if (productObj == null) {
            System.out.println("There is no product with an id of " + id);
            return;
        }

        for (Sale saleObj: sale.getSales()) {
            if (saleObj.containsProduct(productObj)) {
                System.out.println("Can not delete product: there are sales depending on this element");
            }
            return;
        }

        product.delete(id);
    }

    public void deleteCategory(int id) {
        Category categoryObj = category.getById(id);
        if (categoryObj == null) {
            System.out.println("There is no category with an id of " + id);
            return;
        }

        for (Product productObj: product.getProducts()) {
            if (productObj.getCategory() == categoryObj) {
                System.out.println("Can not delete category: there are products depending on this element");
                return;
            }
        }

        category.delete(id);
    }
}
