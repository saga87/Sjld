


$(document).keyup(function(event) {
	if (event.keyCode == 13) {
		$("#longinbutton").click();
	}
});

// 用户登录
function login() {
	var username = $("input[name='username']").val();
	var password =  $("input[name='password']").val();
	var yzm = document.getElementById('zxh_yzm').value;
	if (username == "") {
		$.messager.alert('登录提示', '用户名不能为空', 'error');
		return;
	}
	if (password == "") {
		$.messager.alert('登录提示', '密码不能为空', 'error');
		return;
	}
	if (yzm == "") {
		$.messager.alert('登录提示', '验证码不能为空', 'error');
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
			window.location.href = base+"wkrjsystem/wkrjlogin/login";
		}else{
			$.messager.alert('登录提示', result.msg, 'error');
			$("input[name='username']").val('');
			$("input[name='password']").val('');
		}
	});
}

//自动更新验证码
	function reloadImg(obj){
		obj.src="validateCodeServlet?param="+Math.random();
	}

