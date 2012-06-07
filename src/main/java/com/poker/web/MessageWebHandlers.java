package com.poker.web;

import java.util.List;

import com.britesnow.snow.web.handler.annotation.WebModelHandler;
import com.britesnow.snow.web.param.annotation.WebModel;
import com.britesnow.snow.web.param.annotation.WebParam;
import com.google.inject.Inject;
import com.poker.game.GameManager;
import com.poker.game.Message;
import com.poker.game.Table;

public class MessageWebHandlers {
    @Inject
        private GameManager gameManager;
    @WebModelHandler(startsWith = "/message/getMessage")
    public void getMessage(@WebModel List<Message> ls, @WebParam("room") String room) {
        Table table = gameManager.getTable(room);
        ls.addAll(table.getMessages());
    }
}
