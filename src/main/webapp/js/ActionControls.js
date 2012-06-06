(function($){

	function ActionControls(){};
  
	// --------- Component Interface Implementation ---------- //
	ActionControls.prototype.create = function(data,config){
		var data = data || {};
		var params = {value:1,imgSrc:1};
		if(data.value){
			params.value = data.value;
		}
		var html = $("#tmpl-ActionControls").render(params);
		return $(html);
	}
		
	ActionControls.prototype.postDisplay = function(data,config){
		var c = this;
		var $e = this.$element;
		var room = c.room = $e.bComponent("Room");
		
		room.$element.on("Room_ACTIONCONTROL_REFRESH",function(e,extra){
			refresh.call(c,extra);
		});
		
		$e.on("click",".btn",function(){
			var $btn = $(this);
			var name = $btn.attr("data-name");
			var roomId = room.roomId;
			if(name == "check"){
				app.actions.check(roomId,app.playerId);
			}else if(name == "call"){
				app.actions.call(roomId,app.playerId);
			}else if(name == "fold"){
				app.actions.fold(roomId,app.playerId);
			}else if(name == "raise"){
				app.actions.raise(roomId,app.playerId);
			}
		});
		
	}
	// --------- /Component Interface Implementation ---------- //
	
	// --------- Component Public API --------- //	
	
	// --------- /Component Public API --------- //
	// --------- Component Private API --------- //	
	function refresh(extraData){
		var c = this;
		var $e = this.$element;
		var actions = extraData.requestPlayerStatus.actions;
		$e.find(".btn").hide();
		for(var i = 0; i < actions.length; i++){
			$e.find(".btn[data-name='"+actions[i]+"']").show();
		}
	}
	// --------- /Component Private API --------- //
	
	// --------- Component Registration --------- //
	brite.registerComponent("ActionControls",{
        parent: ".actionControlsArea",
        loadTmpl:true
    },function(){
        return new ActionControls();
    });
	// --------- /Component Registration --------- //
})(jQuery);
