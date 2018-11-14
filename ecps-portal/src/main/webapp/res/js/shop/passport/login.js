$(function () {
    $(".bg_text input").blur(function () {
        //获得离开焦点的值
        var val = $(this).val();
        val = $.trim(val);
        //取name属性的值
        var inputName = $(this).attr("name");
        if (val==null||val=="") {
            if(inputName=="username"){
                $("#errorName").html("请输入用户名");
            }else if (inputName=="password") {
                $("#errorName").html("请输入密码");
            }else if (inputName=="captcha") {
                $("#errorName").html("请输入验证码");
            }
            //显示500毫秒
            $("#errorName").show(500);
        }else {
            $("#errorName").hide(500);
        }
    })
    //验证码错误提示信息
    var tip=$("#tip").val();
    if(tip=="captcha_error") {
        $("#errorName").html("验证码错误");
        $("#errorName").show(500);
    }else if(tip=="userpwd_error"){
        $("#errorName").html("用户名或密码错误");
        $("#errorName").show(500);
    }
})
function changeImage() {
    //有的浏览器在请求url相同的时候会不调用后台 从缓存中取 因此加上时间戳参数
    var cPath = path+"/user/getImage.do?date="+new Date();
    $("#captchaImage").attr("src",cPath);
}