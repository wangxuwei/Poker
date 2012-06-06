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
		var table = c.table = $e.bComponent("Table");
		
		app.util.alignCenter($e);
		
		table.$element.on("Table_COMMUNITY_CARDS_REFRESH",function(e,communityCards){
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
		for(var i = 0;i < communityCards; i++){
			brite.display("Card",{show:true,cardNo:"8",cardSuite:"s"},{parent:$e});
		}	
	}
	// --------- /Component Private API --------- //
	
	// --------- Component Registration --------- //
	brite.registerComponent("CommunityCards",{
        parent: ".table-content",
        loadTmpl:true
    },function(){
        return new CommunityCards();
    });
	// --------- /Component Registration --------- //
})(jQuery);
