package com.poker.web;

import java.util.List;

import com.britesnow.snow.web.handler.annotation.WebModelHandler;
import com.britesnow.snow.web.param.annotation.WebModel;
import com.britesnow.snow.web.param.annotation.WebParam;
import com.poker.game.Table;

public class MessageWebHandlers {

    @WebModelHandler(startsWith = "/message/getMessage")
    public void getMessage(@WebModel List<String> ls, @WebParam("room") String room) {
        Table table = ActionWebHandlers.getTable(room);
        ls = table.getMessages();
    }
}
