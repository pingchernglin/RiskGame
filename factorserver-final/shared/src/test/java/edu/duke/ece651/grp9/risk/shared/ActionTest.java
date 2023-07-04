package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import org.junit.jupiter.api.Test;

public class ActionTest {

    @Test
    public void test_performAction() {
        Player p1 = new Player("red");
        Territory t1 = new Territory("Two Rivers");
        p1.addTerritory(t1);
        t1.setOwner(p1);
        t1.setUnit(5);
        t1.setUnits(5);
        assertEquals(t1.getUnits(0), 5);

        Territory t2 = new Territory("Tar Valon");
        p1.addTerritory(t2);
        t2.setOwner(p1);
        t2.setUnit(0);
        t2.addNeighbors(t1);
        assertEquals(t2.getUnits(0), 0);

        Territory t3 = new Territory("Falme");
        p1.addTerritory(t3);
        t3.setOwner(p1);
        t3.setUnit(10);
        t3.setUnits(10);
        t3.addNeighbors(t1);
        assertEquals(t3.getUnits(0), 10);

        Player p2 = new Player("green");
        Territory t4 = new Territory("Andor");
        p2.addTerritory(t4);
        t4.setOwner(p2);
        t4.setUnit(3);
        t4.setUnits(3);
        t2.addNeighbors(t4);
        assertEquals(t4.getUnits(0), 3);

        Territory t5 = new Territory("Tear");
        p2.addTerritory(t5);
        t5.setOwner(p2);
        t5.setUnit(5);
        t5.setUnits(5);
        t5.addNeighbors(t4);

        HashSet<Action> actions = new HashSet<>();
        //t1 = 5
        //t2 = 0
        //t3 = 10
        //t4 = 3
        //t5 = 5
        Action m1 = new MoveAction(p1, t1, t2, 5);
        Action m2 = new MoveAction(p1, t1, t2, 3);
        Action m3 = new MoveAction(p1, t3, t1, 7);

        Action a1 = new AttackAction(p1, t2, t4, 7);

        actions.add(m1);
        actions.add(m2);
        actions.add(m3);

        for (Action action : actions) {
            assertEquals(action.canPerformAction(), null);
        }
        assertEquals(a1.canPerformAction(), null);
        //t1 = 4
        //t2 = 1
        //t3 = 3
        //t4 = 3
        //t5 = 5

        for (Territory territory : p1.getTerritoryList()) {
            assertTrue(territory.mockIsValid());
        }

        for (Action action : actions) {
            action.performAction();
        }

        //assertEquals(t1.getUnit(), 4);
        assertEquals(t1.getUnits(0), 4);

        assertEquals(t2.getUnits(0), 8);

        //assertEquals(t3.getUnit(), 3);
        assertEquals(t3.getUnits(0), 3);


        Action m4 = new MoveAction(p2, t4, t5, 10, 0);
        assertEquals(t4.getUnits(0), 3);

        assertEquals(m4.canPerformAction(), null);
        assertFalse(t4.mockIsValid());
        assertEquals(t4.getUnits(0), 3);

        Map map = new Map();
        map.addTerritory(t1);
        map.addTerritory(t2);
        map.addTerritory(t3);
        map.addTerritory(t4);
        map.addTerritory(t5);
        map.addPlayer(p1);
        map.addPlayer(p2);

        Battle battle = new Battle(map);
        battle.addAttackAction((AttackAction) a1);

        battle.playBattlePhase();

        assertEquals(t2.getUnits(0), 1);
    }
}

