package com.poker.web;

import java.util.List;
import java.util.Map;

import com.britesnow.snow.web.handler.annotation.WebActionHandler;
import com.britesnow.snow.web.handler.annotation.WebModelHandler;
import com.britesnow.snow.web.param.annotation.WebModel;
import com.britesnow.snow.web.param.annotation.WebParam;
import com.poker.game.Card;
import com.poker.game.Hand;
import com.poker.game.Player;
public class ActionWebHandlers {
    @WebModelHandler(startsWith = "/action/getPlayers")
    public void getPlayers(@WebModel List<Player> ls, @WebParam("room") Integer room) {
        Player player = null;
        ls.add(player);
    }

    @WebModelHandler(startsWith = "/action/hand")
    public void hand(@WebModel Map m, @WebParam("room") Integer room, @WebParam("player") Integer player) {
        Hand hand = null;
        m.put("hand", hand);
    }

    @WebModelHandler(startsWith = "/action/flop")
    public void flop(@WebModel List<Card> ls, @WebParam("room") Integer room, @WebParam("player") Integer player) {
        Card card = null;
        ls.add(card);
    }

    @WebActionHandler
    public void bet(@WebParam("room") Integer room, @WebParam("player") Integer player) {
    }

    @WebActionHandler
    public void call(@WebParam("room") Integer room, @WebParam("player") Integer player) {
    }

    @WebActionHandler
    public void fold(@WebParam("room") Integer room, @WebParam("player") Integer player) {
    }

    @WebActionHandler
    public void check(@WebParam("room") Integer room, @WebParam("player") Integer player) {
    }

    @WebActionHandler
    public void raise(@WebParam("room") Integer room, @WebParam("player") Integer player) {
    }
}
