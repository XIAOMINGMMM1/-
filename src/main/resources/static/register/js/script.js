const form = document.getElementById('form');
const username = document.getElementById('username');
const nickname = document.getElementById('nickname');
const password = document.getElementById('password');
const password2 = document.getElementById('password2');
const realname = document.getElementById('realname');

form.addEventListener('submit', e => {
	e.preventDefault();

	var b  = checkInputs();
	if (b){
        var userName = $("#username").val();
        var password = $("#password").val();
        var nickName = $("#nickname").val();
        var realName = $("#realname").val();
        $.ajax({
            url: "/public/user/save",
            type: "POST",
            dataType:"json",
            data: {'userName':userName,'password':password,'nickName':nickName,'realName':realName},
            success: function (data) {
                console.log(data);
                if (data){
                    window.open("/public/login.html");
                }else {
                    alert("注册失败");
                }
            },
            error: function (e) {
                alert("发布失败；请联系管理员");
            }
        });
    }

});

function checkInputs() {
    var checks={};
	// trim to remove the whitespaces
	const usernameValue = username.value.trim();
	const nicknameValue = nickname.value.trim();
	const passwordValue = password.value.trim();
	const password2Value = password2.value.trim();
	const pattusername=/^[a-zA-z0-9]{3,14}$/;
	const pattpassword=/^[a-zA-z0-9]{6,18}$/;
	if(usernameValue === '') {
		setErrorFor(username, '用户名不能为空');
	}else if (!pattusername.test(usernameValue)){
		setErrorFor(username, '用户名格式错误');
	}else {
		setSuccessFor(username);
        checks["username"]="true";
	}

	if (nicknameValue===''){
		setErrorFor(nickname, '昵称不能为空');
	}else if (nicknameValue.length<2|| nicknameValue>14){
		setErrorFor(nickname, '昵称长度在2-14之间');
	}else {
		setSuccessFor(nickname);
		checks["nickname"]="true";
	}

	
	if(passwordValue === '') {
		setErrorFor(password, '密码不能为空');
	}else if (!pattpassword.test(passwordValue)){
		setErrorFor(password, '密码格式错误');
	} else {
		setSuccessFor(password);
        checks["password"]="true";
	}

	
	if(password2Value === '') {
		setErrorFor(password2, '密码不能为空');
	} else if(passwordValue !== password2Value) {
		setErrorFor(password2, '两次密码不正确');
	} else{
		setSuccessFor(password2);
        checks["password2"]="true";
	}

	if ( checks["password2"]=="true" && checks["password"]=="true" && checks["username"]=="true"&& checks["nickname"]=="true"){
	    return true;
    } else {
	    return false;
    }


}

function setErrorFor(input, message) {
	const formControl = input.parentElement;
	const small = formControl.querySelector('small');
	formControl.className = 'form-control error';
	small.innerText = message;
}

function setSuccessFor(input) {
	const formControl = input.parentElement;
	formControl.className = 'form-control success';
}
	
function isEmail(email) {
	return /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(email);
}