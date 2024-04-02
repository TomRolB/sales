package org.example;

import java.util.Collection;
import java.util.HashMap;

public class SalesmanTable implements Table<Salesman>{
    private final HashMap<Integer, Salesman> salesmen = new HashMap<>();
    private final HashMap<String, Salesman> stringIndex = new HashMap<>();
    int currentId = 0;
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(
                "id\tname\tsalary\n"
        );

        for (Salesman salesman: salesmen.values()) {
            result
                    .append("\n")
                    .append(salesman.getId()).append("\t")
                    .append(salesman.getName()).append("\t")
                    .append(salesman.getSalary()).append("\t");
        }

        return result.toString();
    }
    @Override
    public Salesman getById(int id) {
        return salesmen.get(id);
    }
    public void insert(String name, double salary) {
        Salesman salesman = new Salesman(currentId, name, salary);
        if (stringIndex.containsKey(name)) {
            System.out.println("Salesman " + name + " already exists");
            return;
        }

        salesmen.put(currentId, salesman);
        stringIndex.put(name, salesman);
        currentId++;
    }

    public Salesman getByName(String salesman) {
        return stringIndex.get(salesman);
    }

    public void delete(int id) {
        String salesmanName = salesmen.get(id).getName();
        stringIndex.remove(salesmanName);
        salesmen.remove(id);
    }

    public Collection<Salesman> getSalesmen() {
        return salesmen.values();
    }
}
