package org.example;

import org.example.searchers.CategoryProductSearcher;
import org.example.searchers.PriceProductSearcher;
import org.example.searchers.SalesmanProductSearcher;

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
                    handleCreate(args, db, scanner);
                    break;
                case "get":
                    handleGet(args, db);
                    break;
                case "set":
                    handleSet(args, db);
                    break;
                case "delete":
                    handleDelete(args, db);
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
            }
        }
    }

    private static void handleDelete(String[] args, Database db) {
        if (args.length != 3) {
            System.out.println("Invalid number of arguments. Must pass an element type and the id of the element to be deleted.");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid format for id. A valid example would be 0");
            return;
        }

        switch (args[1]) {
            case "salesman":
                db.deleteSalesman(id);
                break;
            case "sale":
                db.deleteSale(id);
                break;
            case "product":
                db.deleteProduct(id);
                break;
            case "category":
                db.deleteCategory(id);
                break;
            default:
                System.out.println("Invalid command. Run 'help' to get more details");
        }

    }

    private static void handleSet(String[] args, Database db) {
        if (args.length != 4) {
            System.out.println("Invalid number of arguments. Must pass an element type and the field to be set.");
            return;
        }

        String toModify = args[1];
        switch (toModify) {
            case "salesman-salary":
                String salesmanName = args[2];
                String salary = args[3];

                db.setSalesmanSalary(salesmanName, salary);
                break;

            case "product-category":
                String productName = args[2];
                String categoryName = args[3];

                db.setProductCategory(productName, categoryName);
                break;

            default:
                System.out.println("Invalid command. Run 'help' to get more details");
        }
    }

    private static void handleSearch(String[] args, Database db) {
        if (args.length != 3) {
            System.out.println("Must pass a searching option and a parameter");
            return;
        }

        String option = args[1];
        switch (option) {
            case "-c":
                CategoryProductSearcher cps = new CategoryProductSearcher(db.product);

                String categoryName = args[2];
                Category searchParam = db.category.getByName(categoryName);

                System.out.println(cps.getProductsAsTable(searchParam));
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
                System.out.println("Invalid option: '" + option + "'");
        }
    }

    private static void handleView(String[] args, Database db) {
        if (args.length != 2) {
            System.out.println("You must pass the element table you would like to view");
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
                System.out.println("Table '" + args[1] + "' does not exist");
        }
    }

    private static void handleGet(String[] args, Database db) {
        if (args.length > 4) {
            System.out.println("You must pass at least the type of element and an id");
            return;
        }

        String type = args[1];
        String id = args[2];

        int parsedId;
        try {
            parsedId = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            System.out.println("Invalid format for id. A valid example would be 0");
            return;
        }

        switch (type) {
            case "salesman":
                if (!printElementIfNotNull(db.salesman, parsedId)) return;
                printCommissionIfRequested(args, db, parsedId);
                break;
            case "sale":
                printElementIfNotNull(db.sale, parsedId);
                break;
            case "product":
                printElementIfNotNull(db.product, parsedId);
                break;
            case "category":
                printElementIfNotNull(db.category, parsedId);
                break;
            default:
                System.out.println(type + " is not a valid element");
        }
    }

    private static void printCommissionIfRequested(String[] args, Database db, int parsedId) {
        if (args.length != 4) return;

        if (!args[3].equals("-c")) {
            System.out.println("Invalid option '" + args[3] + "'");
            return;
        }

        Salesman salesman = db.salesman.getById(parsedId);
        System.out.println("commission: " + salesman.getCommission());
    }

    private static <T> boolean printElementIfNotNull(Table<T> table, int id) {
        T element = table.getById(id);
        if (element == null) {
            System.out.println("There is no element with an id of " + id);
            return false;
        }
        System.out.println(element);
        return true;
    }

    private static void handleCreate(String[] args, Database db, Scanner scanner) {
        if (args.length != 2) {
            System.out.println("You must pass the name of the element to be created. Run Store help to get more details.");
            return;
        }

        switch (args[1]) {
            case "salesman":
                db.createSalesman(scanner);
                break;
            case "sale":
                db.createSale(scanner);
                break;
            case "product":
                db.createProduct(scanner);
                break;
            case "category":
                db.createCategory(scanner);
                break;
            default:
                System.out.println(args[1] + " is not a valid element");
        }
    }

    private static void help() {
        System.out.println("Available commands:");
        System.out.println();
        System.out.println("create <type>\t| Create a new element of a given type");
        System.out.println("\t* <type> must be 'salesman', 'sales', 'product' or 'category'");
        System.out.println();
        System.out.println("get <type> <id>\t| Get an element by its id");
        System.out.println("\t* <type> must be 'salesman', 'sales', 'product' or 'category'");
        System.out.println("\t* <id> must be an integer value and belong to an existing element");
        System.out.println("\t* If type=salesman, a final option '-c' can be included to compute a salesman's commission");
        System.out.println();
        System.out.println("set <type-attribute> <name> <newValue>\t| Change the attribute of an element");
        System.out.println("\t* <type-attribute> must be 'salesman-salary' or 'product-category'");
        System.out.println("\t* <name> must be the name of an existing element of the type in question");
        System.out.println("\t* <newValue> must follow the format of the attribute to be changed");
        System.out.println();
        System.out.println("delete <type> <id>\t| Delete an element by id");
        System.out.println("\t* <type> must be 'salesman', 'sales', 'product' or 'category'");
        System.out.println("\t* <id> must be an integer value and belong to an existing element");
        System.out.println("\t* If an element relies on the element to be deleted, the operation will fail");
        System.out.println();
        System.out.println("view <table>\t| View all created elements in a table.");
        System.out.println("\t* <table> must be 'salesman', 'sales', 'product' or 'category'");
        System.out.println();
        System.out.println("product-search <option> <searchParam>\t| Search products based on a searching option.");
        System.out.println("\t* option '-c': Search by category. <searchParam> must be the name of an existing category.");
        System.out.println("\t* option '-p': Search for products whose price has ever been <searchParam>.");
        System.out.println("\t* option '-s': Search by salesman who sold the product. <searchParam> must be the name of an existing salesman.");
    }
}