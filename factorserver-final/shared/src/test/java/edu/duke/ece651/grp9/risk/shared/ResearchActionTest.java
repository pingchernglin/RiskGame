package edu.duke.ece651.grp9.risk.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResearchActionTest {

    @Test
    void canPerformAction() {
        Player p = new Player("red");
        ResearchAction researchAction = new ResearchAction(p);
        assertEquals("This action is invalid: you cannot do research at the tech level lower than 3.", researchAction.canPerformAction());
        assertEquals(1, p.getTechLevel());
        assertEquals(false, p.getResearched());

        p.upgradeTechLevel();
        p.upgradeTechLevel();
        assertEquals(null, researchAction.canPerformAction());
        researchAction.performAction();
        assertEquals(true, p.getResearched());
    }

    @Test
    public void test_getters() {
        Player p1 = new Player("red");

        Action action = new ResearchAction(p1);

        assertEquals(action.getSource(), null);
        assertEquals(action.getDestination(), null);
        assertEquals(action.getUnitLevel(), -1);
        assertEquals(action.getEndLevel(), -1);
        assertEquals(action.getNumUnits(), -1);
    }
}