package org.example;

import org.example.utils.SaleBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SalesmanTest {
    Salesman salesman;
    Product tv = new Product(0, "TV", 10, new Category(0, "Entertainment"));
    Product fridge = new Product(0, "Fridge", 17.5, new Category(0, "Appliance"));
    SaleBuilder saleBuilder = new SaleBuilder();

    @BeforeEach
    public void setUp() {
        salesman = new Salesman(0, "Juan", 100);
    }

    @AfterEach
    public void tearDown() {
        saleBuilder.clear();
    }

    @Test
    public void salesmanWithNoSalesShouldGetNoCommission() {
        assertEquals(0, salesman.getCommission());
    }

    @Test
    public void salesmanWithTwoSalesShouldGetSmallCommission() {
        saleBuilder
                .add(tv, 1)
                .add(fridge, 1)
                .getSaleObject(salesman);
        assertEquals(1.375, salesman.getCommission());
    }

    @Test
    public void salesmanWithFourSalesShouldGetGreaterCommission() {
        saleBuilder
                .add(tv, 4)
                .add(fridge, 6)
                .getSaleObject(salesman);
        assertEquals(14.5, salesman.getCommission());
    }
}
