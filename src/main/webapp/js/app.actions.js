var app = app || {};
(function($) {
	app.actions = {};
	
	app.actions.getPlayers = function(room){
		room = 1;
		var params = {
				action:"getPlayers",
				room:room
		}
		return app.getJsonData(jsonUrl,params);
	}
	
	app.actions.call = function(room,player){
		room = 1;
		var params = {
				action:"call",
				room:room,
				player:player
		}
		return app.getJsonData(jsonUrl,params);
	}
	
	app.actions.fold = function(room,player){
		room = 1;
		var params = {
				action:"fold",
				room:room,
				player:player
		}
		return app.getJsonData(jsonUrl,params);
	}
	
	app.actions.check = function(room,player){
		room = 1;
		var params = {
				action:"check",
				room:room,
				player:player
		}
		return app.getJsonData(jsonUrl,params);
	}
	
	app.actions.raise = function(room,player,pokerChip){
		room = 1;
		var params = {
				action:"raise",
				room:room,
				player:player,
				pokerChip:pokerChip
		}
		return app.getJsonData(jsonUrl,params);
	}
	
	app.actions.notification = function(room){
		room = 1;
		var params = {
				room:room
		}
		return app.getJsonData(contextPath + "/notification.json",params);
	}

})(jQuery);




