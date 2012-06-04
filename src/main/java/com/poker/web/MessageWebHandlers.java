package com.poker.web;

import java.util.List;

import com.britesnow.snow.web.handler.annotation.WebModelHandler;
import com.britesnow.snow.web.param.annotation.WebModel;
import com.britesnow.snow.web.param.annotation.WebParam;

public class MessageWebHandlers {
    @WebModelHandler(startsWith = "/message/getMessage")
    public void getPlayers(@WebModel List<String> ls, @WebParam("room") Integer room) {
        ls.add(null);
    }
}
