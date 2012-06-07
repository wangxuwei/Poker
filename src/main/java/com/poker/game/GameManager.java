package com.poker.game;


import com.google.inject.Singleton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Singleton
public class GameManager implements Runnable {

    private final Object lock = new Object();
    private List<Table> tables = new ArrayList<Table>();
    private Map<Serializable, Table> tableMap = new HashMap<Serializable, Table>();

    public static boolean mock = true;
    public static int mock_index = 0;


    private boolean gameOver = false;


    public Table getTable(String tableId) {
        Table table = tableMap.get(tableId);
        if (table == null) {
            throw new IllegalArgumentException(String.format("invalid table id %s", tableId));
        }
        return table;
    }

    public Player getPlayer(String tableId, String userId) {
        Table table = getTable(tableId);

        Player player = table.getPlayer(userId);
        if (player == null) {
            throw new IllegalArgumentException(String.format("invalid user id %s", userId));
        }
        return player;
    }

    public void action(String tableId, String userId, Action action) {
        Table table = getTable(tableId);

        Player player = table.getPlayer(userId);
        if (player == null) {
            throw new IllegalArgumentException(String.format("invalid user id %s", userId));
        }
        if (!table.getAllowedActions(player).contains(action)) {
            throw new IllegalArgumentException(String.format("invalid action %s", action.toString()));
        }
        player.act(table, action, table.getMinBet(), table.getBet());
    }

    public Table createTable(int bigBlind) {
        Table table = new Table(Table.DEFAULT_BIG_BLIND);
        tableMap.put(table.getId(), table);
        synchronized (lock) {
            tables.add(table);
        }
        return table;
    }

    public TableState getTableState(String tableId) {
        return getTable(tableId).getState();
    }

    public Set<Action> getAllowedActions(String tableId, String playerId) {
        Table table = getTable(tableId);

        Player player = table.getPlayer(playerId);
        if (player == null) {
            throw new IllegalArgumentException(String.format("invalid user id %s", playerId));
        }
        return table.getAllowedActions(player);
    }

    public void joinTable(String room, Player player) {
         getTable(room).addPlayer(player,-1);
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


    int getPoolPokerChip(String room){
       return getTable(room).getPot();
    }

    List<Card> getCommunityCards(String room){
        return getTable(room).getBoard();
    }

    List<Player> getPlayers(String room){
       return getTable(room).getPlayers();
    }

    public void bet(String room, String player){
        action(room, player, Action.BET);
    }

    public void call(String room, String player) {
        action(room, player, Action.CALL);
    }

    public void fold(String room, String player){
        action(room, player, Action.FOLD);
    }

    public void check(String room, String player){
        action(room, player, Action.CHECK);
    }

    public void raise(String room, String player){
        action(room, player, Action.RAISE);
    }



    List<Message> getMessage(String room) {
        return getTable(room).getMessages();
    }
}
