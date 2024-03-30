package org.example;

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
                case "view":
                    handleView(args, db);
                    break;
                case "exit":
                    return;
                default:
                    throw new IllegalArgumentException("'" + args[0] + "' is not a valid command. Run Store help to get more details.");
            }
        }
    }

    private static void handleView(String[] args, Database db) {
        if (args.length > 2) {
            throw new IllegalArgumentException("You must only pass the element table you would like to view");
        }

        switch (args[1]) {
            case "salesman":
                System.out.println(db.salesman.toString());
                break;
        }
    }

    private static void handleGet(String[] args, Database db) {
        if (args.length > 4) {
            throw new IllegalArgumentException("You must pass the type of entity and an id");
        }

        String type = args[1];
        int id;
        try {
            id = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid format for id. A valid example would be 0");
        }

        switch (type) {
            case "salesman":
                Salesman salesman = db.salesman.getById(id);
                if (salesman == null) {
                    throw new NoSuchElementException("There is no salesman with an id of " + id);
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
            throw new IllegalArgumentException("You must pass the name of the entity to be created. Run Store help to get more details.");
        }

        switch (args[1]) {
            case "salesman":
                createSalesman(args, db);
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
                throw new IllegalArgumentException(args[1] + "is not a valid entity");
        }
    }

    private static void createSale(String[] args, Database db, Scanner scanner) {
        System.out.println("salesman: ");
        String salesman = scanner.nextLine();
        System.out.println("Start adding products. Leave the field blank and press enter to finish.");

        SaleBuilder saleBuilder = new SaleBuilder();

        while (true) {
            System.out.print("product: ");
            String product = scanner.nextLine();
            System.out.print("quantity: ");
            String quantity = scanner.nextLine();

            if (product.isEmpty() || quantity.isEmpty()) break;

            int parsedQuantity;
            try {
                parsedQuantity = Integer.parseInt(quantity);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid format for quantity. A valid example would be 4");
            }

            saleBuilder.add(db.product.getByName(product), parsedQuantity);
        }

        db.sale.insert(saleBuilder, db.salesman.getByName(salesman));
        System.out.println("Created product");
    }

    private static void createCategory(String[] args, Database db, Scanner scanner) {
        System.out.println("name: ");
        String name = scanner.nextLine();

        db.category.insert(name);
        System.out.println("Created category");
    }

    private static void createProduct(String[] args, Database db, Scanner scanner) {
        System.out.print("name: ");
        String name = scanner.nextLine();
        System.out.print("price: ");
        String price = scanner.nextLine();
        System.out.print("category: ");
        String category = scanner.nextLine();

        double parsedPrice;
        try {
            parsedPrice = Double.parseDouble(price);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid format for price. A valid example would be 15.5");
        }

        db.product.insert(name, parsedPrice, db.category.getByName(category));
        System.out.println("Created product");
    }

    private static void createSalesman(String[] args, Database db) {
        if (args.length > 4) {
            throw new IllegalArgumentException("Passed too many parameters");
        }

        String name = args[2];
        double salary;
        try {
            salary = Double.parseDouble(args[3]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid format for salary. A valid example would be 100.5");
        }

        db.salesman.insert(name, salary);
        System.out.println("Created salesman");
    }

    private static void help() {
        System.out.println("Available commands:");
        System.out.println("add sm\t\tAdds a new Salesman to the system");
        System.out.println("create s\t\tAdds a new Salesman to the system");
    }
}