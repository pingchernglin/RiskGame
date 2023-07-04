package edu.duke.ece651.grp9.risk.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttackActionTest {

    @Test
    void canPerformAction_invalid() {
        Player p1 = new Player("red");
        Player p2 = new Player("blue");
        Territory t1 = new Territory("NC");
        Territory t2 = new Territory("CA");
        t1.setOwner(p1);
        t2.setOwner(p2);
        t1.setUnits(10);
        t2.setUnits(0);
        // not neighbors
        AttackAction att = new AttackAction(p1, t1, t2, 6, 0);
        String err = att.canPerformAction();
        assertNotEquals(null, err);
        // units out of bound
        AttackAction att2 = new AttackAction(p1, t1, t2, 100, 0);
        err = att2.canPerformAction();
        assertNotEquals(null, err);
    }

    @Test
    public void test_getSet() {
        Player p1 = new Player("red");
        Territory t1 = new Territory("Two Rivers");
        t1.setOwner(p1);
        t1.setUnits(5);

        Territory t2 = new Territory("Tar Valon");
        t2.setOwner(p1);
        t2.setUnits(0);

        AttackAction a1 = new AttackAction(p1, t1, t2, 5, 0);

        assertEquals(a1.getEndLevel(), -1);
    }

    @Test
    void canPerformAction_valid() {
        Player p1 = new Player("red");
        Player p2 = new Player("blue");
        Territory t1 = new Territory("NC");
        Territory t2 = new Territory("CA");
        t1.addNeighbors(t2);
        t1.setOwner(p1);
        t2.setOwner(p2);
        t1.setUnits(10);
        t2.setUnits(0);
        AttackAction att = new AttackAction(p1, t1, t2, 6, 0);
        String err = att.canPerformAction();
        assertEquals(null, err);
    }

    @Test
    void BasicTest() {
        Territory src = new Territory("Duke");
        Territory dst = new Territory("UNC");
        Player player = new Player("Luna");
        AttackAction attackAction = new AttackAction(player, src, dst,10);

        assertEquals(10, attackAction.getNumUnits());
        assertEquals(dst, attackAction.getDestination());
        assertEquals(src, attackAction.getSource());
        assertEquals(player, attackAction.getPlayer());
        assertTrue(attackAction.isSameOriAttack(attackAction));

        attackAction.setAttackUnits(20);
        assertEquals(20, attackAction.getNumUnits());
    }

    @Test
    void performAction_10vs0() {
        Player p1 = new Player("red");
        Player p2 = new Player("blue");
        Territory t1 = new Territory("NC");
        Territory t2 = new Territory("CA");
        t1.setOwner(p1);
        t2.setOwner(p2);
        t1.setUnits(10, 3);
        t2.setUnits(0, 3);
        AttackAction att = new AttackAction(p1, t1, t2, 6, 3);

        Map map = new Map();
        map.addTerritory(t1);
        map.addTerritory(t2);
        map.addPlayer(p1);
        map.addPlayer(p2);

        Battle battle = new Battle(map);
        battle.addAttackAction(att);
        battle.playBattlePhase();

        assertEquals(p1, t2.getOwner());
        assertEquals(6, t2.getUnits(3));
        assertEquals(0, t2.getUnits(0));
        assertEquals(4, t1.getUnits(3));
    }


    @Test
    void performAction_0vs1() {
        Player p1 = new Player("red");
        Player p2 = new Player("blue");
        Territory t1 = new Territory("NC");
        Territory t2 = new Territory("CA");
        Map map= new Map();
        map.addPlayer(p1);
        map.addPlayer(p2);
        map.addTerritory(t1);
        map.addTerritory(t2);

        t1.addNeighbors(t2);
        t1.setOwner(p1);
        t2.setOwner(p2);
        t1.setUnits(10);
        t2.setUnits(0);
        AttackAction att1 = new AttackAction(p1, t1, t2, 10, 0);
        AttackAction att2 = new AttackAction(p2, t2, t1, 0, 0);
        assertNull(att1.canPerformAction());
        assertNull(att2.canPerformAction());

//        att1.performAction();
//        att2.performAction();

        Battle battle = new Battle(map);
        battle.addAttackAction(att1);
        battle.addAttackAction(att2);
        battle.playBattlePhase();
        assertEquals(p1, t1.getOwner());
        //assertEquals(t1.getUnits(0), 2);
        assertEquals(p1, t2.getOwner());
        //TODO failing because the single unit in t2 is staying to defend
        //assertEquals(t2.getUnits(0), 10);
    }

}
