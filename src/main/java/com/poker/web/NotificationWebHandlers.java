package com.poker.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.britesnow.snow.web.handler.annotation.WebModelHandler;
import com.britesnow.snow.web.param.annotation.WebModel;
import com.britesnow.snow.web.param.annotation.WebParam;
import com.google.inject.Inject;
import com.poker.game.Card;
import com.poker.game.GameRunner;
import com.poker.game.Player;
import com.poker.game.Table;

public class NotificationWebHandlers {

    @Inject
    private GameRunner gameRunner;

    @WebModelHandler(startsWith = "/notification")
    public void notification(@WebModel Map m, @WebParam("room") String room) {
        Table table = gameRunner.getTable(room);
        List playerList = table.getPlayers();
        for (int i = 0; i < playerList.size(); i++) {
            Player player = (Player) playerList.get(i);
            Map playerMap = new HashMap();
            playerMap.put("pokerChip", player.getCash());
            playerMap.put("player", player.getName());
            Card[] handArr = player.getHand().getCards();
            List handList = new ArrayList();
            for (int j = 0; j < handArr.length; j++) {
                handList.add(handArr[j].toString());
            }
            playerMap.put("handCards", handList);
            playerMap.put("playerId", player.getId());
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