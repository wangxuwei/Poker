(function($){

	function Table(){};
  
	// --------- Component Interface Implementation ---------- //
	Table.prototype.create = function(data,config){
		var html = $("#tmpl-Table").html();
		return $(html);
	}
		
	Table.prototype.postDisplay = function(data,config){
		var c = this;
		var $e = this.$element;
		app.util.alignCenter($e);
		
		//show community cards
		brite.display("CommunityCards").done(function(communityCards){
			brite.display("PrizePool").done(function(prizePool){
				app.util.alignCenter($e);
				var top = communityCards.$element.position().top - prizePool.$element.height() - 30;
				prizePool.$element.css("top",top+"px");
				

				brite.display("PokerChip",{value:8},{parent:".PrizePool"});
			});
			
			//FIXME keep it for now
			brite.display("Card",{show:true,cardNo:"3",cardSuite:"d"},{parent:".CommunityCards"});
			brite.display("Card",{show:true,cardNo:"J",cardSuite:"h"},{parent:".CommunityCards"});
			brite.display("Card",{show:true,cardNo:"2",cardSuite:"c"},{parent:".CommunityCards"});
			brite.display("Card",{show:true,cardNo:"K",cardSuite:"c"},{parent:".CommunityCards"});
			brite.display("Card",{show:true,cardNo:"8",cardSuite:"s"},{parent:".CommunityCards"});
		});
		
		//FIXME keep it for now
		brite.display("Card",{show:true,cardNo:"4",cardSuite:"c"},{parent:".player1 .cardsArea"});
		brite.display("Card",{show:true,cardNo:"Q",cardSuite:"c"},{parent:".player1 .cardsArea"});
		
		brite.display("Card",{show:true,cardNo:"4",cardSuite:"h"},{parent:".player2 .cardsArea"});
		brite.display("Card",{show:true,cardNo:"K",cardSuite:"d"},{parent:".player2 .cardsArea"});
		
		brite.display("Card",{show:true,cardNo:"8",cardSuite:"h"},{parent:".player3 .cardsArea"});
		brite.display("Card",{show:true,cardNo:"7",cardSuite:"c"},{parent:".player3 .cardsArea"});
		
		brite.display("Card",{show:true,cardNo:"9",cardSuite:"s"},{parent:".player4 .cardsArea"});
		brite.display("Card",{show:true,cardNo:"3",cardSuite:"s"},{parent:".player4 .cardsArea"});
		
		brite.display("Card",{show:true,cardNo:"5",cardSuite:"s"},{parent:".player5 .cardsArea"});
		brite.display("Card",{show:true,cardNo:"5",cardSuite:"c"},{parent:".player5 .cardsArea"});
		
		brite.display("Card",{show:true,cardNo:"T",cardSuite:"h"},{parent:".player6 .cardsArea"});
		brite.display("Card",{show:true,cardNo:"J",cardSuite:"c"},{parent:".player6 .cardsArea"});
		
		brite.display("Card",{show:true,cardNo:"6",cardSuite:"s"},{parent:".player7 .cardsArea"});
		brite.display("Card",{show:true,cardNo:"9",cardSuite:"c"},{parent:".player7 .cardsArea"});
		
		brite.display("Card",{show:true,cardNo:"3",cardSuite:"d"},{parent:".player8 .cardsArea"});
		brite.display("Card",{show:true,cardNo:"8",cardSuite:"s"},{parent:".player8 .cardsArea"});
		
		brite.display("Card",{show:false},{parent:".player9 .cardsArea"});
		brite.display("Card",{show:false},{parent:".player9 .cardsArea"});
		
		
		//FIXME keep it for now
		brite.display("PokerChip",{value:1},{parent:".player1 .betArea"});
		brite.display("PokerChip",{value:2},{parent:".player2 .betArea"});
		brite.display("PokerChip",{value:4},{parent:".player3 .betArea"});
		brite.display("PokerChip",{value:4},{parent:".player4 .betArea"});
		brite.display("PokerChip",{value:4},{parent:".player5 .betArea"});
		brite.display("PokerChip",{value:4},{parent:".player6 .betArea"});
		brite.display("PokerChip",{value:4},{parent:".player7 .betArea"});
		brite.display("PokerChip",{value:4},{parent:".player8 .betArea"});
		
	}
	// --------- /Component Interface Implementation ---------- //
	
	// --------- Component Public API --------- //	
	
	// --------- /Component Private API --------- //
	// --------- Component Private API --------- //	
	
	// --------- /Component Public API --------- //
	
	// --------- Component Registration --------- //
	brite.registerComponent("Table",{
        parent: ".mainScreen-content",
        loadTmpl:true
    },function(){
        return new Table();
    });
	// --------- /Component Registration --------- //
})(jQuery);
