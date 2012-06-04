package com.poker.web;

import java.util.Map;

import com.britesnow.snow.web.handler.annotation.WebModelHandler;
import com.britesnow.snow.web.param.annotation.WebModel;
import com.britesnow.snow.web.param.annotation.WebParam;

public class NotificationWebHandlers {
    @WebModelHandler(startsWith = "/notification")
    public void notification(@WebModel Map m, @WebParam("room") Integer room) {
        m.put("playersStatus", null);
        m.put("poolPokerChip", null);
        m.put("communityCards", null);
    }
}