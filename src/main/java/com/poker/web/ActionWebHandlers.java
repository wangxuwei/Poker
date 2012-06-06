package com.poker.web;

import java.util.List;
import java.util.Map;

import com.britesnow.snow.web.handler.annotation.WebActionHandler;
import com.britesnow.snow.web.handler.annotation.WebModelHandler;
import com.britesnow.snow.web.param.annotation.WebModel;
import com.britesnow.snow.web.param.annotation.WebParam;
import com.google.inject.Inject;
import com.poker.game.Action;
import com.poker.game.Card;
import com.poker.game.GameRunner;
import com.poker.game.Player;
import com.poker.game.Table;

public class ActionWebHandlers {
    @Inject
    private GameRunner gameRunner;

    @WebModelHandler(startsWith = "/action/getPlayers")
    public void getPlayers(@WebModel List<Player> ls, @WebParam("room") String room) {
        Player player = null;
        ls.add(player);
        Table table = gameRunner.getTable(room);
        if (table != null) {
            ls.addAll(table.getState().getPlayers());
        }
    }

    @WebModelHandler(startsWith = "/action/hand")
    public void hand(@WebModel Map m, @WebParam("room") String room, @WebParam("player") String player) {
        Table table = gameRunner.getTable(room);
        if (table != null) {
            Player actionPlayer = table.getPlayer(player);
            if (actionPlayer != null) {
                m.put("hand", actionPlayer.getHand());
            }
        }

    }

    @WebModelHandler(startsWith = "/action/flop")
    public void flop(@WebModel List<Card> ls, @WebParam("room") String room, @WebParam("player") String player) {
        Table table = gameRunner.getTable(room);
        if (table != null) {
            Player actionPlayer = table.getPlayer(player);
            if (actionPlayer != null) {
                actionPlayer.act(table, Action.FOLD, -1, -1);
            }
        }
    }

    @WebActionHandler
    public void bet(@WebParam("room") String room, @WebParam("player") String player) {
        //todo need bet value
        Table table = gameRunner.getTable(room);
        if (table != null) {
            Player actionPlayer = table.getPlayer(player);
            if (actionPlayer != null) {
                actionPlayer.act(table, Action.BET, -1, -1);
            }
        }
    }

    @WebActionHandler
    public void call(@WebParam("room") String room, @WebParam("player") String player) {
        Table table = gameRunner.getTable(room);
        if (table != null) {
            Player actionPlayer = table.getPlayer(player);
            if (actionPlayer != null) {
                actionPlayer.act(table, Action.CALL, -1, -1);
            }
        }
    }

    @WebActionHandler
    public void fold(@WebParam("room") String room, @WebParam("player") String player) {
        Table table = gameRunner.getTable(room);
        if (table != null) {
            Player actionPlayer = table.getPlayer(player);
            if (actionPlayer != null) {
                actionPlayer.act(table, Action.FOLD, -1, -1);
            }
        }
    }

    @WebActionHandler
    public void check(@WebParam("room") String room, @WebParam("player") String player) {
        Table table = gameRunner.getTable(room);
        if (table != null) {
            Player actionPlayer = table.getPlayer(player);
            if (actionPlayer != null) {
                actionPlayer.act(table, Action.CHECK, -1, -1);
            }
        }
    }

    @WebActionHandler
    public void raise(@WebParam("room") String room, @WebParam("player") String player) {
        Table table = gameRunner.getTable(room);
        if (table != null) {
            Player actionPlayer = table.getPlayer(player);
            if (actionPlayer != null) {
                actionPlayer.act(table, Action.RAISE, -1, -1);
            }
        }

    }
}
