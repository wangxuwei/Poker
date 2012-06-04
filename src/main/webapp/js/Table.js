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
