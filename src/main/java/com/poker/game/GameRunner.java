package com.poker.game;


import com.google.inject.Singleton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class GameRunner implements Runnable {

    public static boolean mock = true;
    public static int mock_index = 0;
    
    private static Map<Serializable, Table> tableMap = new HashMap<Serializable, Table>();
    
    static {
        if (mock) {
            Table table = new Table(10);
            Player player1 = new Player("player1", 1000);
            Player player2 = new Player("player2", 2000);
            Player player3 = new Player("player3", 3000);
            Player player4 = new Player("player4", 4000);
            Player player5 = new Player("player5", 5000);
            Player player6 = new Player("player6", 6000);
            Player player7 = new Player("player7", 7000);
            Player player8 = new Player("player8", 8000);
            //
            table.addPlayer(player1, 1);
            table.addPlayer(player2, 2);
            table.addPlayer(player3, 3);
            table.addPlayer(player4, 4);
            table.addPlayer(player5, 5);
            table.addPlayer(player6, 6);
            table.addPlayer(player7, 7);
            table.addPlayer(player8, 8);
            //
            table.addMessage("player1:test1");
            table.addMessage("player2:test2");
            table.addMessage("player3:test3");
            tableMap.put("1", table);
        }
    }
    
    private final Object lock = new Object();
    private List<Table> tables = new ArrayList<Table>();
    


    private boolean gameOver = false;


    public Table getTable(String id) {
        return  tableMap.get(id);
    }

    public Table createTable(int bigBlind) {
        Table table = new Table(Table.DEFAULT_BIG_BLIND);
        tableMap.put(table.getId(), table);
        synchronized (lock) {
            tables.add(table);
        }
        return table;
    }

    @Override
    public void run() {
        while (!gameOver) {
            for (Table table : tables) {
                //make new table to play tables
                synchronized (lock) {
                    //
                }
                //do something
            }
        }

    }
}
