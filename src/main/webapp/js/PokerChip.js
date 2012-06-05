(function($){

	function PokerChip(){};
  
	// --------- Component Interface Implementation ---------- //
	PokerChip.prototype.create = function(data,config){
		var data = data || {};
		var params = {value:1,imgSrc:1};
		if(data.value){
			params.value = data.value;
		}
		var html = $("#tmpl-PokerChip").render(params);
		return $(html);
	}
		
	PokerChip.prototype.postDisplay = function(data,config){
		var c = this;
		var $e = this.$element;
		
	}
	// --------- /Component Interface Implementation ---------- //
	
	// --------- Component Public API --------- //	
	
	// --------- /Component Private API --------- //
	// --------- Component Private API --------- //	
	
	// --------- /Component Public API --------- //
	
	// --------- Component Registration --------- //
	brite.registerComponent("PokerChip",{
        parent: ".player1 .betArea",
        loadTmpl:true
    },function(){
        return new PokerChip();
    });
	// --------- /Component Registration --------- //
})(jQuery);
