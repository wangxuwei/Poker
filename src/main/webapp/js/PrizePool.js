(function($){

	function PrizePool(){};
  
	// --------- Component Interface Implementation ---------- //
	PrizePool.prototype.create = function(data,config){
		var html = $("#tmpl-PrizePool").html();
		return $(html);
	}
		
	PrizePool.prototype.postDisplay = function(data,config){
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
	brite.registerComponent("PrizePool",{
        parent: ".table-content",
        loadTmpl:true
    },function(){
        return new PrizePool();
    });
	// --------- /Component Registration --------- //
})(jQuery);
