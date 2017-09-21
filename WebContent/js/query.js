$(function(){
	$("#query").on('click',function(){
		var name = $("input[name='queryName']").val();
		var gender = $("input[name='gender']:checked").val();
		var url = "student?act=query&name="+name+"&gender="+gender;
		window.location.href = url;
	});
});