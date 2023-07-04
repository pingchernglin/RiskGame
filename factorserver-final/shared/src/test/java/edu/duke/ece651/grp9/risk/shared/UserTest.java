package edu.duke.ece651.grp9.risk.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    public void test_active() {
        User user = new User("name", "pwd");
        user.status = "in";
        assertEquals(true, user.activeUser());
        user.status = "out";
        assertEquals(false, user.activeUser());
    }
}