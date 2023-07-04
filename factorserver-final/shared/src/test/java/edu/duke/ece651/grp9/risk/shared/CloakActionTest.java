package edu.duke.ece651.grp9.risk.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CloakActionTest {
    @Test
    public void test_canPerform() {
        Player player = new Player("red");
        player.upgradeTechLevel();
        player.upgradeTechLevel();
        player.doResearch();
        Territory ter = new Territory("A");
        player.addTerritory(ter);
        ter.setOwner(player);

        CloakAction action = new CloakAction(player, ter);
        assertEquals(null, action.canPerformAction());
    }

    @Test
    public void test_getters() {
        Player p1 = new Player("red");
        Territory ter = new Territory("A");
        Action action = new CloakAction(p1, ter);

        assertEquals(action.getSource(), ter);
        assertEquals(action.getDestination(), null);
        assertEquals(action.getUnitLevel(), -1);
        assertEquals(action.getEndLevel(), -1);
        assertEquals(action.getNumUnits(), -1);
    }

}