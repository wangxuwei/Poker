var app = app || {};
(function($) {
	
	app.getJsonData = function(url,params){
		var dfd = $.Deferred();
		jQuery.ajax({
			type: "Post",
            url: url,
            async: true,
            data: params,
            dataType: "json"
        }).success(function(data){
        	dfd.resolve(data);
        }).fail(function(jxhr,arg2){
        	try {
	        	if (jxhr.responseText){
	        		console.log("POKER WARNING: json not well formatted, falling back to JS eval");
	        		var data = eval("(" + jxhr.responseText + ")");
	        		dfd.resolve(data);	
	        	}else{
		        	throw "POKER EXCEPTION: Cannot get content for "+url;
	        	}
        	}catch (ex){
        		console.log("POKER ERROR: " + ex + " Fail parsing JSON for url: " + url + "\nContent received:\n" + jxhr.responseText);
        	}
        });
        
        return dfd.promise();		
	}

})(jQuery);




