package edu.duke.ece651.grp9.risk.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodTest {

    @Test
    void testGetAndAdd() {
        Food food = new Food(0);
        assertEquals(0, food.getQuantity());
        food.addResource(100);
        assertEquals(100, food.getQuantity());
    }

}
