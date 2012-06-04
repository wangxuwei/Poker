package com.poker.game;


import java.util.ArrayList;
import java.util.List;

public class GameRunner implements Runnable{

    private final Object lock = new Object();
    private List<Table> tables =  new ArrayList<Table>();
    private final List<Table> toAdds = new ArrayList<Table>();

    private boolean gameOver = false;

    public void addTable(Table table) {
        synchronized (lock) {
            toAdds.add(table);
        }
    }

    public Table getTable(int id) {
        return null;
    }

    @Override
    public void run() {
        while (!gameOver) {
            for (Table table : tables) {
                //make new table to play tables
                synchronized (lock) {
                    tables.addAll(toAdds);
                    toAdds.clear();
                }
                //do something
            }
        }

    }
}
