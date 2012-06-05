package com.poker.game;


import org.junit.Test;

public class TableTest {
    @Test
    public void testGame() {
        Table table = new Table(id, 1);
        //add some player
        Player joe = new Player("joe", 200);
        Player mike = new Player("mike", 300);
        Player me = new Player("me", 500);
        Player jason = new Player("jason", 400);

        table.addPlayer(joe, 1);
        table.addPlayer(mike, 2);
        table.addPlayer(me, 3);
        table.addPlayer(jason, 4);

        table.start();
    }
}
