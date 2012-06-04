package com.poker.web;

import java.util.HashMap;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.poker.User;

public class PokerUsersListener implements HttpSessionListener {

	private static HashMap<Long,User> usersMap= new HashMap<Long,User>();

	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		User user = (User) httpSessionEvent.getSession().getAttribute("user");
		usersMap.remove(user.getId());
	}


	public static User getUser(Long userId){
		return usersMap.get(userId);
	}
	public static void addUser(User user){
		usersMap.put(user.getId(),user);
	}
}
