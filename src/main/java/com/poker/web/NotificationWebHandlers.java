package com.poker.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.britesnow.snow.web.handler.annotation.WebModelHandler;
import com.britesnow.snow.web.param.annotation.WebModel;
import com.britesnow.snow.web.param.annotation.WebParam;
import com.poker.game.Player;
import com.poker.game.Table;

public class NotificationWebHandlers {
    @WebModelHandler(startsWith = "/notification")
    public void notification(@WebModel Map m, @WebParam("room") String room) {
        Table table = ActionWebHandlers.getTable(room);
        List playerList = table.getPlayers();
        for (int i = 0; i < playerList.size(); i++) {
            Player player = (Player) playerList.get(i);
            Map playerMap = new HashMap();
            playerMap.put("pokerChip", player.getCash());
            playerMap.put("player", player.getName());
            playerMap.put("status", player.getAction().getName());
            playerList.set(i, playerMap);
        }
        m.put("playersStatus", playerList);
        m.put("poolPokerChip", table.getPot());
        List cardList = table.getBoard();
        for (int i = 0; i < cardList.size(); i++) {
            cardList.set(i, cardList.get(i).toString());
        }
        m.put("communityCards", cardList);
    }
}