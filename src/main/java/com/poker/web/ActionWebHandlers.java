package com.poker.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.britesnow.snow.web.handler.annotation.WebActionHandler;
import com.britesnow.snow.web.handler.annotation.WebModelHandler;
import com.britesnow.snow.web.param.annotation.WebModel;
import com.britesnow.snow.web.param.annotation.WebParam;
import com.poker.game.Card;
import com.poker.game.Hand;
import com.poker.game.Player;
import com.poker.game.Table;

public class ActionWebHandlers {
    public static boolean             mock    = true;

    private static Map<String, Table> gameMap = new HashMap<String, Table>();

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
            gameMap.put("1", table);
        }
    }

    @WebModelHandler(startsWith = "/action/getPlayers")
    public void getPlayers(@WebModel List<Player> ls, @WebParam("room") String room) {
        Table table = getTable(room);
        ls = table.getPlayers();
    }

    @WebModelHandler(startsWith = "/action/hand")
    public void hand(@WebModel Map m, @WebParam("room") String room, @WebParam("player") String player) {
        Hand hand = null;
        Table table = getTable(room);
        m.put("hand", hand);
    }

    @WebModelHandler(startsWith = "/action/flop")
    public void flop(@WebModel List<Card> ls, @WebParam("room") String room, @WebParam("player") String player) {
        Card card = null;
        ls.add(card);
    }

    @WebActionHandler
    public void bet(@WebParam("room") String room, @WebParam("player") String player) {
    }

    @WebActionHandler
    public void call(@WebParam("room") String room, @WebParam("player") String player) {
    }

    @WebActionHandler
    public void fold(@WebParam("room") String room, @WebParam("player") String player) {
    }

    @WebActionHandler
    public void check(@WebParam("room") String room, @WebParam("player") String player) {
    }

    @WebActionHandler
    public void raise(@WebParam("room") String room, @WebParam("player") String player) {
    }

    public static Table getTable(String room) {
        Table table = gameMap.get(room);
        return table;
    }
}
