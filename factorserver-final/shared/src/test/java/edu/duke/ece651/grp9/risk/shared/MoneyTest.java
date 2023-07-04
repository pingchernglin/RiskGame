package edu.duke.ece651.grp9.risk.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void testGetAndAdd() {
        Money money = new Money(0);
        assertEquals(0, money.getQuantity());
        money.addResource(100);
        assertEquals(100, money.getQuantity());
    }
}
