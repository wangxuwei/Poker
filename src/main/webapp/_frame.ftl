<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Texas Hold¡¯em poker</title>
    [@webBundle path="/css/" type="css" /]
    [@webBundle path="/js/" type="js" /]
    
    [#-- set jsonUrl as the actionResponse and contextPath variables --] 
	[#assign jsonUrl]${_r.contextPath}/_actionResponse.json[/#assign]
		<script type="text/javascript">
		  var jsonUrl = '${_r.contextPath}/_actionResponse.json';	
		  var contextPath = "${_r.contextPath}";
		</script>
	[#-- /set jsonUrl as the actionResponse and contextPath variables --]	
  </head>

  <body>
  	<div id="page">
  
	</div>
    		$(function(){
				brite.display("MainScreen");
			});
  </body>
</html>