(function($) {
	
	// --------- Component Interface Implementation --------- //
	function Login(){}
	
	
	Login.prototype.create = function(data,config){
		var html = $("#tmpl-Login").html();
		var $e = $(html);
		return $e;
	}
	
	Login.prototype.postDisplay = function(data,config){
		var c = this;
		var $e = c.$element;
		var pWidth = $e.parent().width();
		var pHeight = $e.parent().height();
		$e.css("left",(pWidth - $e.width())/2 + "px").css("top",(pHeight - $e.height())/2 + "px");
		
		$e.find("input[name='username']").focus();
		$e.find(".login-button").click(function(){
			var username = $e.find("input[name='username']").val();
			
			if(username === "") {
				$e.find(".msg").html("Please enter valid user name");
			} else {
				login(username).done(function(user){
					if (typeof user  == "object"){
						window.location = window.location.href;
					}
					
				});
			}
		});
	}
	// --------- /Component Interface Implementation --------- //
	
	
	
	// --------- Helper Functions --------- //
	function login(username){
		var reqData = {
			action: "login",
			username: username
			
		}
		return $.ajax({
			type: "POST",
			url: jsonUrl,
			data: reqData,
			dataType: "json"
		}).pipe( function(val) {
			return val;
		});
	}
	// --------- /Helper Functions --------- //
	
	
	// --------- Component Registration --------- //
	brite.registerComponent("Login", {
		parent : "#page",
		loadTmpl : true
	}, function() {
		return new Login();
	});
	// --------- /Component Registration --------- //
	
	
})(jQuery);




