/**
 * 页面用户名显示
 */
$(function () {
    $.ajax({
        url:path+"/user/getUser.do",
        type:"post",
        dataType:"json",
        success:function (data) {
            if (data.user!=null){
                $("#loginAlertIs").html(data.user.username);
            }
        }
    })
})