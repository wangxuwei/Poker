package com.poker.game;


import com.google.inject.Singleton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class GameRunner implements Runnable {

    private final Object lock = new Object();
    private List<Table> tables = new ArrayList<Table>();
    private Map<Serializable, Table> tableMap = new HashMap<Serializable, Table>();


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
