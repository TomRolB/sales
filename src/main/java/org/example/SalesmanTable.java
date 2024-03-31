package org.example;

import java.util.Collection;
import java.util.HashMap;

public class SalesmanTable implements Table<Salesman>{
    private HashMap<Integer, Salesman> salesmen = new HashMap<>();
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
        salesmen.put(currentId, new Salesman(currentId, name, salary));
        currentId++;
    }

    public Salesman getByName(String salesman) {
        for (Salesman sm: salesmen.values()) {
            if (sm.getName().equals(salesman)) return sm;
        }

        return null;
    }

    public Collection<Salesman> getSalesmen() {
        return salesmen.values();
    }
}
