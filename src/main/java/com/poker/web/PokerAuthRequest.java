package com.poker.web;
import java.util.List;
import java.util.Map;

import com.britesnow.snow.util.ObjectUtil;
import com.britesnow.snow.web.RequestContext;
import com.britesnow.snow.web.auth.AuthRequest;
import com.britesnow.snow.web.auth.AuthToken;
import com.britesnow.snow.web.handler.annotation.WebActionHandler;
import com.britesnow.snow.web.handler.annotation.WebModelHandler;
import com.britesnow.snow.web.param.annotation.WebModel;
import com.britesnow.snow.web.param.annotation.WebParam;
import com.google.common.base.Objects;
import com.google.common.hash.Hashing;
import com.google.inject.Inject;
import com.poker.User;
import com.poker.game.GameRunner;
import com.poker.game.Player;


public class PokerAuthRequest implements AuthRequest {
    @Inject
    private GameRunner gameRunner;
    
    @Override
    public AuthToken authRequest(RequestContext rc) {
        // Note: this is not the login logic, the login logic would be 
        //        @WebActionHandler that would generate the appropriate 
        
        // Note: this is a simple stateless authentication scheme. 
        //       Security is medium-low, however, with little bit more logic
        //       it can be as secure as statefull login while keeping it's scalability attributes
        
        // First, we get userId and userToken from cookie
        String userIdStr = rc.getCookie("userId");
        String userToken = rc.getCookie("userToken");
        
        if (userIdStr != null && userToken != null){
            // get the User from the DAO
            Long userId = ObjectUtil.getValue(userIdStr, Long.class, null);
            User user = PokerUsersListener.getUser(userId);
            
            // Build the expectedUserToken from the user info 
            // For this example, simplistic userToken (sha1(username,password))
            String expectedUserToken = Hashing.sha1().hashString(user.getUsername() + user.getId()).toString();
            
            if (Objects.equal(expectedUserToken, userToken)){
                // if valid, then, we create the AuthTocken with our User object
                AuthToken<User> authToken = new AuthToken<User>();
                authToken.setUser(user);
                return authToken;
                
            }else{
                // otherwise, we could throw an exception, or just return null
                // In this example (and snowStarter, we just return null)
                return null;
            }
        }else{
            return null;
        }
    }
    
    @WebModelHandler(startsWith = "/")
    public void pageIndex(@WebModel Map m,RequestContext rc) {
    	User user = getUserFromSession(rc);
    	m.put("user", user);
    }
    
    @WebActionHandler
    public Object login(@WebParam("userId") Long userId,@WebParam("username") String username,RequestContext rc) {
        User user = PokerUsersListener.getUser(userId);
        if (user == null) {
            if(username != null){
            	user = new User();
            	user.setId(System.currentTimeMillis());
            	user.setUsername(username);
            	setUserToSession(rc, user);
            }
        } else {
        	setUserToSession(rc, user);
        }
		return user;
    }
    
	  // --------- Private Helpers --------- //
    // store the user in the session. If user == null, then, remove it.
    private void setUserToSession(RequestContext rc, User user) {
        // TODO: need to implement session less login (to easy loadbalancing)
        if (user != null) {
            rc.getReq().getSession().setAttribute("user", user);
            String userToken = Hashing.sha1().hashString(user.getUsername() + user.getId()).toString();
            rc.setCookie("userToken",userToken);
            rc.setCookie("userId",user.getId());
            PokerUsersListener.addUser(user);
            

            //FIXME
            List playerList = gameRunner.getTable("1").getPlayers();
            int index = gameRunner.mock_index++;
            if (index>7) {
                index=0;
            }
            Player player = (Player) playerList.get(index);
            rc.getReq().getSession().setAttribute("playerId", player.getId());
            //
        } else {
            rc.getReq().getSession().removeAttribute("user");
        }
    }

    // get the user from the session
    private User getUserFromSession(RequestContext rc) {
    	User user = (User) rc.getReq().getSession().getAttribute("user");
    	return user;
    }
    // --------- /Private Helpers --------- //
} 