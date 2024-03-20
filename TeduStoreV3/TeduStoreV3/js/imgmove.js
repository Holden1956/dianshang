$(function() {
	/*图片变大的样式*/
	$(".move-img").hover(function() {
		$(this).animate({
			"background-size": "110%"
		}, "fast");
	}, function() {
		$(this).animate({
			"background-size": "100%"
		}, "fast");
	})
})
$(function (){
	let authorization = localStorage.getItem("token");
	let username=localStorage.getItem("username")
	if (authorization){
		$(".fa_fa-user").html(username);
	}
})