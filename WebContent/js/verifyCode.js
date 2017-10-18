$(function(){
	var $img = $("img.verifyCodeImg");
	$img.on("click",function(){
		var url = "verifyCode" + "?random=" + Math.random();
		console.info(url);
		$(this).attr("src",url);
	});
	console.info("23e");
});