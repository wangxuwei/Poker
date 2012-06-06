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
		var table = c.table = $e.bComponent("Table");
		
		app.util.alignCenter($e);
		
		table.$element.on("Table_PRIZE_POOL_REFRESH",function(e,communityCards){
			refresh.call(c,communityCards);
		});
		
	}
	// --------- /Component Interface Implementation ---------- //
	
	// --------- Component Public API --------- //	
	
	// --------- /Component Public API --------- //
	
	// --------- Component Private API --------- //	
	function refresh(communityCards){
		var c = this;
		var $e = this.$element;
		
		$e.empty();
		brite.display("PokerChip",{value:8},{parent:$e});
	}
	
	// --------- /Component Private API --------- //
	
	// --------- Component Registration --------- //
	brite.registerComponent("PrizePool",{
        parent: ".table-content",
        loadTmpl:true
    },function(){
        return new PrizePool();
    });
	// --------- /Component Registration --------- //
})(jQuery);
