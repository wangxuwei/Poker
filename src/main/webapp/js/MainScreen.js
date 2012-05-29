(function($){

	function MainScreen(){};
  
	// --------- Component Interface Implementation ---------- //
	MainScreen.prototype.create = function(data,config){
		var html = $("#tmpl-MainScreen").html();
		return $(html);
	}
		
	MainScreen.prototype.postDisplay = function(data,config){
		var c = this;
		var $e = this.$element;
	}
	// --------- /Component Interface Implementation ---------- //
	
	// --------- Component Public API --------- //	
	
	// --------- /Component Public API --------- //
	
	// --------- /Component Private API --------- //
	// --------- Component Private API --------- //	
	
	
	// --------- Component Registration --------- //
	brite.registerComponent("MainScreen",{
        parent: "#page",
        emptyParent: true,
        loadTmpl:true
    },function(){
        return new MainScreen();
    });
	// --------- /Component Registration --------- //
})(jQuery);
