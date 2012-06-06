package com.poker.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.britesnow.snow.web.RequestContext;
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
    public void notification(@WebModel Map m, @WebParam("room") String room, RequestContext rc) {
        Table table = gameRunner.getTable(room);
        List playerList = table.getPlayers();
        List playerList2 = new ArrayList();
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
            playerMap.put("status", player.getAction() == null ? "" : player.getAction().getName());
            playerList2.add(playerMap);
            //
            if (player.getId().equals(rc.getReq().getSession().getAttribute("playerId"))) {
                List actionList = new ArrayList();
                actionList.add("Continue");
                actionList.add("Raise");
                actionList.add("Fold");
                playerMap.put("actions", actionList);
                m.put("requestPlayerStatus", playerMap);
            }
        }
        m.put("playersStatus", playerList2);
        m.put("poolPokerChip", table.getPot());
        List cardList = table.getBoard();
        List cardList2 = new ArrayList();
        for (int i = 0; i < cardList.size(); i++) {
            cardList2.add(cardList.get(i).toString());
        }
        m.put("communityCards", cardList2);
    }
}