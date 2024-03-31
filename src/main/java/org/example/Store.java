package org.example;

import jdk.jshell.spi.ExecutionControl;
import org.example.searchers.CategoryProductSearcher;
import org.example.searchers.PriceProductSearcher;
import org.example.searchers.SalesmanProductSearcher;
import org.example.utils.SaleBuilder;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Store {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Database db = new Database();

        while (true) {
            System.out.print("Store> ");
            args = scanner.nextLine().split(" ");

            if (args.length == 0) {
                help();
                return;
            }

            switch (args[0]) {
                case "create":
                    handleCreation(args, db, scanner);
                    break;
                case "get":
                    handleGet(args, db);
                    break;
                case "set":
                    handleSet(args, db);
                    break;
                case "view":
                    handleView(args, db);
                    break;
                case "product-search":
                    handleSearch(args, db);
                    break;
                case "help":
                    help();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("'" + args[0] + "' is not a valid command. Run 'help' to get more details.");
                    continue;
            }
        }
    }

    private static void handleSet(String[] args, Database db) {
        if (args.length != 4) {
            System.out.println("Invalid number of arguments. Must pass an element type and the field to be set.");
            return;
        }

        switch (args[1]) {
            case "salesman-salary":
                String salesmanName = args[2];
                Salesman salesman = db.salesman.getByName(salesmanName);
                if (salesman == null) {
                    System.out.println("Salesman '" + salesmanName + "' does not exist");
                    return;
                }

                double salary;
                try {
                    salary = Double.parseDouble(args[3]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid format for salary. A valid example would be 100.5");
                    return;
                }

                salesman.setSalary(salary);
                System.out.println("Changed salary");
                break;
            case "product-category":
                String productName = args[2];
                Product product = db.product.getByName(productName);
                if (product == null) {
                    System.out.println("Product '" + productName + "' does not exist");
                    return;
                }

                String categoryName = args[3];
                Category category = db.category.getByName(categoryName);
                if (category == null) {
                    System.out.println("Category '" + categoryName + "' does not exist");
                    return;
                }

                product.setCategory(category);
                System.out.println("Changed category");
            default:
                System.out.println("Invalid command. Run 'help' to get more details/");
        }
    }

    private static void handleSearch(String[] args, Database db) {
        if (args.length != 3) {
            System.out.println("Must pass a searching option and a parameter");
            return;
        }

        switch (args[1]) {
            case "-c":
                CategoryProductSearcher cps = new CategoryProductSearcher(db.product);
                System.out.println(cps.getProductsAsTable(db.category.getByName(args[2])));
                break;
            case "-p":
                int price;
                try {
                    price = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid format for price. A valid example would be 100.5");
                    return;
                }

                PriceProductSearcher pps = new PriceProductSearcher(db.sale);
                System.out.println(pps.getProductsAsTable(price));
                break;
            case "-s":
                SalesmanProductSearcher sps = new SalesmanProductSearcher(db.salesman);
                System.out.println(sps.getProductsAsTable(db.salesman.getByName(args[2])));
                break;
            default:
                System.out.println("Invalid option: '" + args[1] + "'");
        }
    }

    private static void handleView(String[] args, Database db) {
        if (args.length > 2) {
            System.out.println("You must only pass the element table you would like to view");
            return;
        }

        switch (args[1]) {
            case "salesman":
                System.out.println(db.salesman.toString());
                break;
            case "sale":
                System.out.println(db.sale.toString());
                break;
            case "product":
                System.out.println(db.product.toString());
                break;
            case "category":
                System.out.println(db.category.toString());
                break;
            default:
                System.out.println("Table '" + args[1] + "' does not exit");
                return;
        }
    }

    private static void handleGet(String[] args, Database db) {
        if (args.length > 4) {
            System.out.println("You must pass at least the type of entity and an id");
            return;
        }

        String type = args[1];
        int id;
        try {
            id = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid format for id. A valid example would be 0");
            return;
        }

        switch (type) {
            case "salesman":
                Salesman salesman = db.salesman.getById(id);
                if (salesman == null) {
                    System.out.println("There is no salesman with an id of " + id);
                    return;
                }
                System.out.println(salesman);
                if (args[3].equals("-c")) {
                    System.out.println("commission: " + salesman.getCommission());
                }
                break;
        }
    }

    private static void handleCreation(String[] args, Database db, Scanner scanner) {
        if (args.length == 1) {
            System.out.println("You must pass the name of the entity to be created. Run Store help to get more details.");
            return;
        }

        switch (args[1]) {
            case "salesman":
                createSalesman(args, db, scanner);
                break;
            case "sale":
                createSale(args, db, scanner);
                break;
            case "product":
                createProduct(args, db, scanner);
                break;
            case "category":
                createCategory(args, db, scanner);
                break;
            default:
                System.out.println(args[1] + "is not a valid entity");
                return;
        }
    }

    private static void createSale(String[] args, Database db, Scanner scanner) {
        System.out.print("salesman: ");
        String salesmanName = scanner.nextLine();

        Salesman salesman = db.salesman.getByName(salesmanName);
        if (salesman == null) {
            System.out.println("Salesman '" + salesmanName+ "' does not exist");
            return;
        }

        System.out.println("Start adding products. Leave the field blank and press enter to finish.");

        SaleBuilder saleBuilder = new SaleBuilder();

        while (true) {
            System.out.print("product: ");
            String productName = scanner.nextLine();
            System.out.print("quantity: ");
            String quantity = scanner.nextLine();
            System.out.print("price: ");
            String price = scanner.nextLine();

            if (productName.isEmpty() || quantity.isEmpty() || price.isEmpty()) break;

            Product product = db.product.getByName(productName);
            if (product == null) {
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

            saleBuilder.add(product, parsedQuantity, parsedPrice);
        }

        db.sale.insert(saleBuilder, salesman);
        System.out.println("Created sale");
    }

    private static void createCategory(String[] args, Database db, Scanner scanner) {
        System.out.print("name: ");
        String name = scanner.nextLine();

        db.category.insert(name);
        System.out.println("Created category");
    }

    private static void createProduct(String[] args, Database db, Scanner scanner) {
        System.out.print("name: ");
        String name = scanner.nextLine();
        System.out.print("category: ");
        String categoryName = scanner.nextLine();

        Category category = db.category.getByName(categoryName);
        if (category == null) {
            System.out.println("Category '" + categoryName + "' does not exist");
            return;
        }

        db.product.insert(name, category);
        System.out.println("Created product");
    }

    private static void createSalesman(String[] args, Database db, Scanner scanner) {
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

        db.salesman.insert(name, parsedSalary);
        System.out.println("Created salesman");
    }

    private static void help() {
        System.out.println("Available commands:");
        System.out.println();
        System.out.println("create <type>\t| Create a new element of a given type");
        System.out.println("\t* <type> must be 'salesman', 'sales', 'product' or 'category'");
        System.out.println();
        System.out.println("set <type-attribute> <name> <newValue> \t| Change the attribute of an element");
        System.out.println("\t* <type-attribute> must be 'salesman-salary' or 'product-category'");
        System.out.println("\t* <name> must be the name of an existing element of the type in question");
        System.out.println("\t* <newValue> must follow the format of the attribute to be changed");
        System.out.println();
        System.out.println("view <table>\t| View all created elements in a table.");
        System.out.println("\t* <table> must be 'salesman', 'sales', 'product' or 'category'");
        System.out.println();
        System.out.println("product-search <option> <searchParam>\t| Search products based on a searching option.");
        System.out.println("\t* option '-c': Search by category. <searchParam> must be the name of an existing category.");
        System.out.println("\t* option '-p': Search for products whose price has ever been <searchParam>.");
        System.out.println("\t* option '-p': Search by salesman who sold the product. <searchParam> must be the name of an existing salesman.");
    }
}