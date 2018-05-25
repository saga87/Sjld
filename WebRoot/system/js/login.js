/*
*	JQuery Document
*/
$(function () {
	$(".username").focus(function(){
		var oVal=$(this).val();
		if(oVal=="用户名"){
			$(this).val("");
		}
	}).blur(function(){
		var oVal=$(this).val();	
		if(oVal==""){
			$(this).val("用户名");
		}
	});
	/*$(".pass1").focus(function(){
		var oVal=$(this).val();
		if(oVal=="密码"){
			$("#strPass1").hide();
			$("#strPass2").show().focus().val();
		}
	}).blur(function(){
		var oVal=$(this).val();	
		if(oVal==""){
			$("#strPass1").show().val("密码");
			$("#strPass2").hide();
		}
	});*/
	$(".pass1").focus(function(){
		$(this).hide();
		$(".pass2").show().focus();
	})
	$(".pass2").blur(function(){
		if($(".pass2").val()==''){ 
			$(".pass2").hide();
			$(".pass1").show();
		}
	});
	$(".verif input").focus(function(){
		var oVal=$(this).val();
		if(oVal=="验证码"){
			$(this).val("");
		}
	}).blur(function(){
		var oVal=$(this).val();	
		if(oVal==""){
			$(this).val("验证码");
		}
	});
});

$(document).keyup(function(event) {
	if (event.keyCode == 13) {
		if(event.srcElement.id=="strName"){
			$("#strPass1").focus();
		}else if(event.srcElement.id=="strPass2"){
			$("#verif").focus();
		}else{
			$("#longinbutton").click();
		}
	}
});

// 用户登录
function login() {
	var username = $("input[name='username']").val();
	var password =  $("input[name='password']").val();
	var yzm = $("input[name='yzmin']").val();
	if (username == "" || username == "用户名") {
		alert('用户名不能为空');
		return;
	}
	if (password == "") {
		alert('密码不能为空');
		return;
	}
	if (yzm == "" || yzm == "验证码") {
		alert('验证码不能为空');
		return;
	}
	// 用户提交
	$.post('wkrjsystem/wkrjlogin/checkLogin', {
		username : username,
		password : password,
		yzm:yzm
	}, function(result) {
		try{
			var result = eval('(' + result + ')');
		}catch(e){
		}
		if(true==result.success){
			window.location.href =base+"wkrjsystem/wkrjlogin/login";
		}else{
			alert(result.msg);
			//$("input[name='username']").val('');
			//$("input[name='password']").val('');
			$("input[name='yzm']").val('');
		}
	});
}

//自动更新验证码
	function reloadImg(obj){
		obj.src="validateCodeServlet?param="+Math.random();
	}

