var rk = function() {
    function CapRefresh(){
        capImg.attr('src', xd.getCaptchaUrl());
    }
    return {
        login: function(username,password,auto,captcha) {
        	
        	return true;
        },
        bindLoginForm:function()
        {
            var capBox = $('.captcha_box');
            var capImg = $('.captcha_box img');
            var capInput = $('.captcha_box input');
            var infoBox = $('.info_box');
            var submitButton = $('.login_button');

            capImg.click(function(){
                CapRefresh();
            });
    		$('.verify_click').click(function(){
    			CapRefresh();
    		});
            capInput.css('color','#a87765').val('');
            //输入提示，确保文字暗色

            //登录
            $('#loginForm').submit(function(){
                var username = $('#username').val();
                var password = $('#password').val();
                var captcha = $('#captcha').val();
                var auto = $('#auto').is(':checked');
                //点击登录按钮变登录中
                submitButton.addClass('loading');
                //登录
                rk.login(username, password, auto, function(user){
                    if(user && user.id > 0){
                        window.location.reload();
                    }
                },logInfo, null, 'xd', captcha);
                return false;
            });
            function logInfo(data){
                //清除载入中
                submitButton.removeClass('loading');
                //错误信息
                $('.login_error').html(data.error).removeClass('not_error');
                //提示框展现
                infoBox.show();
                //出现验证码
                if(data.error == '验证码错误'){
    				if(!capBox.is(':visible')){
    					$('.login_error').addClass('not_error').html('请输入验证码再登录：');
    					$('#loginerror').hide();
    				}
                    capBox.show();
                    infoBox.addClass('info_box_with_cap');//调整验证码框位置
                    $('.info_box_bt').addClass('info_box_bt_cap');
    				capInput.focus();
                }else{
                	$('#loginerror').show();
    				infoBox.removeClass('info_box_with_cap');
    				$('.info_box_bt').removeClass('info_box_bt_cap');
    				capBox.hide();
    			}
                if(capBox.is(':visible')){
                    	//刷新验证码
                    	CapRefresh();
                }
            }
            $('#username').focus();
        }
    };
} ();