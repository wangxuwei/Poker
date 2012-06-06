(function($){

	function Table(){};
  
	// --------- Component Interface Implementation ---------- //
	Table.prototype.create = function(data,config){
		var html = $("#tmpl-Table").html();
		return $(html);
	}
	
	Table.prototype.init = function(data,config){
		var c = this;
		var $e = this.$element;
		//show community cards
		brite.display("CommunityCards").done(function(communityCards){
			brite.display("PrizePool").done(function(prizePool){
				fixPoolPosition.call(c);
			});
		});
	}
		
	Table.prototype.postDisplay = function(data,config){
		var c = this;
		var $e = this.$element;
		var room = c.room = $e.bComponent("Room");
		app.util.alignCenter($e);
		
		room.$element.on("Room_TABLE_REFRESH",function(e,extra){
			refresh.call(c,extra);
		});
		
	}
	// --------- /Component Interface Implementation ---------- //
	
	// --------- Component Public API --------- //	
	
	// --------- /Component Public API --------- //
	
	// --------- Component Private API --------- //	
	function refresh(extraData){
		var c = this;
		var $e = this.$element;
		for(var i = 0; i < extraData.playersStatus.length; i++){
			var playerStatus = extraData.playersStatus[i];
			var $player = $e.find(".player"+i);
			$player.find("label").html(playerStatus.player);
			var $cardArea = $player.find(".cardArea").empty();
			var $betArea = $player.find(".betArea").empty();
			for(var j = 0; j < playerStatus.handCards.length; j++){
				brite.display("Card",{show:true,cardNo:"8",cardSuite:"s"},{parent:$cardArea});
			}
			if(playerStatus.pokerChip > 0){
				brite.display("PokerChip",{value:playerStatus.pokerChip},{parent:$betArea});
			}
		}
		
		$e.trigger("Table_COMMUNITY_CARDS_REFRESH",extraData.communityCards);
		$e.trigger("Table_PRIZE_POOL_REFRESH",extraData.poolPokerChip);
		
	}
	
	function fixPoolPosition(){
		var c = this;
		var $e = this.$element;
		var $communityCards = $e.find(".CommunityCards");
		var $prizePool = $e.find(".PrizePool");
		var top = $communityCards.position().top - $prizePool.height() - 30;
		$prizePool.css("top",top+"px");
	}
	// --------- /Component Private API --------- //
	
	// --------- Component Registration --------- //
	brite.registerComponent("Table",{
        parent: ".room-content",
        loadTmpl:true
    },function(){
        return new Table();
    });
	// --------- /Component Registration --------- //
})(jQuery);
