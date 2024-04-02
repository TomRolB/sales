package org.example;

import org.example.utils.SaleBuilder;

import java.util.Scanner;

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
        System.out.println("Deleted salesman");
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
        System.out.println("Deleted sale");
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
                return;
            }
        }

        product.delete(id);
        System.out.println("Deleted product");
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
        System.out.println("Deleted category");
    }

    public void createSalesman(Scanner scanner) {
        System.out.print("name: ");
        String name = scanner.nextLine();
        System.out.print("salary: ");
        String salary = scanner.nextLine();

        double parsedSalary;
        try {
            parsedSalary = Double.parseDouble(salary);
        } catch (NumberFormatException e) {
            System.out.println("Invalid format for salary. A valid example would be 100.5");
            return;
        }

        salesman.insert(name, parsedSalary);
        System.out.println("Created salesman");
    }

    public void createSale(Scanner scanner) {
        System.out.print("salesman: ");
        String salesmanName = scanner.nextLine();

        Salesman salesmanObj = salesman.getByName(salesmanName);
        if (salesmanObj == null) {
            System.out.println("Salesman '" + salesmanName+ "' does not exist");
            return;
        }

        System.out.println("Start adding products. Leave any field blank and press enter to finish.");

        SaleBuilder saleBuilder = new SaleBuilder();

        while (true) {
            System.out.print("product: ");
            String productName = scanner.nextLine();
            System.out.print("quantity: ");
            String quantity = scanner.nextLine();
            System.out.print("price: ");
            String price = scanner.nextLine();

            if (productName.isEmpty() || quantity.isEmpty() || price.isEmpty()) break;

            Product productObj = product.getByName(productName);
            if (productObj == null) {
                System.out.println("Product '" + productName + "' does not exist");
                return;
            }

            int parsedQuantity;
            try {
                parsedQuantity = Integer.parseInt(quantity);
            } catch (NumberFormatException e) {
                System.out.println("Invalid format for quantity. A valid example would be 4");
                return;
            }

            double parsedPrice;
            try {
                parsedPrice = Double.parseDouble(price);
            } catch (NumberFormatException e) {
                System.out.println("Invalid format for price. A valid example would be 4");
                return;
            }

            saleBuilder.add(productObj, parsedQuantity, parsedPrice);
            System.out.println("Added a product\n");
        }

        sale.insert(saleBuilder, salesmanObj);
        System.out.println("Created sale");
    }

    public void createProduct(Scanner scanner) {
        System.out.print("name: ");
        String name = scanner.nextLine();
        System.out.print("category: ");
        String categoryName = scanner.nextLine();

        Category categoryObj = category.getByName(categoryName);
        if (categoryObj == null) {
            System.out.println("Category '" + categoryName + "' does not exist");
            return;
        }

        product.insert(name, categoryObj);
        System.out.println("Created product");
    }

    public void createCategory(Scanner scanner) {
        System.out.print("name: ");
        String name = scanner.nextLine();

        category.insert(name);
        System.out.println("Created category");
    }

    public void setSalesmanSalary(String salesmanName, String salary) {
        Salesman salesmanObj = salesman.getByName(salesmanName);
        if (salesmanObj == null) {
            System.out.println("Salesman '" + salesmanName + "' does not exist");
            return;
        }

        double parsedSalary;
        try {
            parsedSalary = Double.parseDouble(salary);
        } catch (NumberFormatException e) {
            System.out.println("Invalid format for salary. A valid example would be 100.5");
            return;
        }

        salesmanObj.setSalary(parsedSalary);
        System.out.println("Changed salary");
    }

    public void setProductCategory(String productName, String categoryName) {
        Product productObj = product.getByName(productName);
        if (productObj == null) {
            System.out.println("Product '" + productName + "' does not exist");
            return;
        }

        Category categoryObj = category.getByName(categoryName);
        if (categoryObj == null) {
            System.out.println("Category '" + categoryName + "' does not exist");
            return;
        }

        productObj.setCategory(categoryObj);
        System.out.println("Changed category");
    }
}
