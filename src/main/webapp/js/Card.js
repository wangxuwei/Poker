(function($){

	function Card(){};
  
	// --------- Component Interface Implementation ---------- //
	Card.prototype.create = function(data,config){
		var card = null;
		var data = data || {};
		var params = {show:false,cardNo:"",cardSuite:""};
		if(data.show){
			params.show = true;
			params.cardNo = data.cardNo;
			params.cardSuite = data.cardSuite;
		}
		var html = $("#tmpl-Card").render(params);
		return $(html);
	}
	
	Card.prototype.init = function(data,config){
		var c = this;
		var $e = this.$element;
	}
		
	Card.prototype.postDisplay = function(data,config){
		var c = this;
		var $e = this.$element;
		
		
	}
	// --------- /Component Interface Implementation ---------- //
	
	// --------- Component Public API --------- //	
	
	// --------- /Component Public API --------- //
	// --------- Component Private API --------- //	
	Card.prototype.show = function(){
		var c = this;
		var $e = this.$element;
		$e.addClass("show");
	}
	Card.prototype.hide = function(){
		var c = this;
		var $e = this.$element;
		$e.removeClass("show");
	}
	// --------- /Component Private API --------- //
	
	// --------- Component Registration --------- //
	brite.registerComponent("Card",{
        parent: ".player1 .cardsArea",
        loadTmpl:true
    },function(){
        return new Card();
    });
	// --------- /Component Registration --------- //
})(jQuery);
