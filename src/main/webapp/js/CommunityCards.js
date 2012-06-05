(function($){

	function CommunityCards(){};
  
	// --------- Component Interface Implementation ---------- //
	CommunityCards.prototype.create = function(data,config){
		var html = $("#tmpl-CommunityCards").html();
		return $(html);
	}
		
	CommunityCards.prototype.postDisplay = function(data,config){
		var c = this;
		var $e = this.$element;
		
		app.util.alignCenter($e);
		
		
	}
	// --------- /Component Interface Implementation ---------- //
	
	// --------- Component Public API --------- //	
	
	// --------- /Component Private API --------- //
	// --------- Component Private API --------- //	
	
	// --------- /Component Public API --------- //
	
	// --------- Component Registration --------- //
	brite.registerComponent("CommunityCards",{
        parent: ".table-content",
        loadTmpl:true
    },function(){
        return new CommunityCards();
    });
	// --------- /Component Registration --------- //
})(jQuery);
