const form = document.getElementById('form');
const nickname = document.getElementById('username');
const email = document.getElementById('email');
const phone = document.getElementById('phone');
const intro = document.getElementById('intro');

form.addEventListener('submit', e => {
	e.preventDefault();

	var b  = checkInputs();
	if (b){
        var email = $("#email").val();
        var phone = $("#phone").val();
        var nickname = $("#username").val();
        var intro = $("#intro").val();
        $.ajax({
            url: "/ordinary/user/update-user-info",
            type: "POST",
            dataType:"json",
            data: {'email':email,'phone':phone,'nickname':nickname,'intro':intro},
            success: function (data) {
                console.log(data);
                if (data){
                    var search = window.location.search;
                    search = search.replace("?redirectUrl=","");
                    console.log(search);
                    debugger
                    window.location.href=search;
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
	const nicknameValue = nickname.value.trim();
	const emailValue = email.value.trim();
	const phoneValue = phone.value.trim();
	const introValue = intro.value.trim();
	
	if(nicknameValue === '') {
		setErrorFor(nickname, '用户名不能为空');
	} else {
		setSuccessFor(nickname);
        checks["nickname"]="true";
	}


	if (checks["nickname"]=="true"){
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