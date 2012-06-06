(function($){

	function Room(){};
  
	// --------- Component Interface Implementation ---------- //
	Room.prototype.create = function(data,config){
		var html = $("#tmpl-Room").html();
		return $(html);
	}
		
	Room.prototype.postDisplay = function(data,config){
		var c = this;
		var $e = this.$element;
		
		brite.display("Table");
		brite.display("ActionControls");
		
		startNotification.call(c);
	}
	// --------- /Component Interface Implementation ---------- //
	
	// --------- Component Public API --------- //	
	
	// --------- /Component Public API --------- //
	
	// --------- Component Private API --------- //
	function refresh(extraData){
		var c = this;
		var $e = this.$element;
		
		$e.trigger("Room_TABLE_REFRESH",extraData);
		$e.trigger("Room_ACTIONCONTROL_REFRESH",extraData);
	}
	
	function startNotification(){
		var c = this;
		var $e = this.$element;
		setTimeout(function(){
			app.actions.notification().done(function(extraData){
				refresh.call(c,extraData);
				startNotification.call(c);
			});
		},2000);
	}
	// --------- /Component Private API --------- //	
	
	
	// --------- Component Registration --------- //
	brite.registerComponent("Room",{
        parent: ".MainScreen",
        emptyParent: true,
        loadTmpl:true
    },function(){
        return new Room();
    });
	// --------- /Component Registration --------- //
})(jQuery);
