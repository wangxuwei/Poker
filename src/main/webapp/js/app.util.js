var app = app || {};
(function($) {
	
	app.util = {};
	
	//align center
	app.util.alignCenter = function($e){
		var pWidth = $e.parent().width();
		var pHeight = $e.parent().height();
		$e.css("left",(pWidth - $e.width())/2 + "px").css("top",(pHeight - $e.height())/2 + "px");
	}
	

})(jQuery);




