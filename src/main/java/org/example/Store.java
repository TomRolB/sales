package org.example;

public class Store {
    public static void main(String[] args) {
        Database db = new Database();

        if (args.length == 0) {
            help();
            return;
        }

        switch (args[0]) {
            case "create":
                handleCreation(args, db);
                break;
            default:
                throw new IllegalArgumentException("Command not found. Run Store help to get more details.");
        }
    }

    private static void handleCreation(String[] args, Database db) {
        if (args.length == 1) {
            throw new IllegalArgumentException("You must pass the name of the entity to be created. Run Store help to get more details.");
        }

        switch (args[1]) {
            case "salesman":
                createSalesman(args, db);
                break;
            default:
                throw new IllegalArgumentException(args[1] + "is not a valid entity");
        }
    }

    private static void createSalesman(String[] args, Database db) {
        new Salesman(db.salesman.generateId(), args[2], Double.parseDouble(args[3]));
    }

    private static void help() {
        System.out.println("Available commands:");
        System.out.println("add sm\t\tAdds a new Salesman to the system");
        System.out.println("create s\t\tAdds a new Salesman to the system");
    }
}