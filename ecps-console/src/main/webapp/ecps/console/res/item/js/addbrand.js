//图片上传
function submitUpload() {
    var option = {
        url: path + "/upload/uploadPic.do",//上传的url
        dataType: "text",//回调的数据类型
        success: function (responseText) {
            var jsonObj = $.parseJSON(responseText);
            $("#imgsImgSrc").attr("src", jsonObj.realPath);
            $("#imgs").val(jsonObj.relativePath);
            $("#lastRealPath").val(jsonObj.realPath);
        },
        error: function () {
            alert("上传失败");
        }
    }
    //使用ajax方式提交表单 上传文件
    $("#form111").ajaxSubmit(option);
}

//添加品牌数据校验
$(function () {
    $("#form111").submit(function () {
        var isSubmit = true;
        //校验必选字段
        $(this).find("[reg2]").each(function () {
            //获得输入的值
            var val = $(this).val();
            val = $.trim(val);
            //获得正则表达式
            var reg = $(this).attr("reg2");
            //获得提示信息
            var tip = $(this).attr("tip");
            //创建正则表达式对象
            var regExp = new RegExp(reg);
            //前端校验
            if (!regExp.test(val)) {
                $(this).next("span").html("<font color='red'>" + tip + "</font>")
                isSubmit = false;
                //跳出循环
                return false;
            } else {
                var inputName=$(this).attr("name");
                //品牌名符合前端校验再进行后端校验
                if (inputName=="brandName") {
                    //校验品牌名重复性
                    var result = validBrandName(val);
                    if (result=="no"){
                        $(this).next("span").html("<font color='red'>品牌名称已存在</font>")
                        isSubmit=false;
                        return false;
                    }else {
                        //后台检验成功
                        $(this).next("span").html("");
                    }
                }else {
                    //前端校验成功
                    $(this).next("span").html("");
                }
            }
        });

        //校验可选字段
        $(this).find("[reg1]").each(function () {
            //获得输入的值
            var val = $(this).val();
            val = $.trim(val);
            //获得正则表达式
            var reg = $(this).attr("reg1");
            //获得提示信息
            var tip = $(this).attr("tip");
            //创建正则表达式对象
            var regExp = new RegExp(reg);
            if (val != null && val !== "" && !regExp.test(val)) {
                $(this).next("span").html("<font color='red'>" + tip + "</font>")
                isSubmit = false;
                //跳出循环
                return false;
            } else {
                $(this).next("span").html("");
            }
        });
        //使用遮罩 防止二次提交
        if (isSubmit) {
            tipShow("#refundLoadDiv");
        }
        return isSubmit;
    });

    //校验必选字段 离开焦点就进行校验
    $(this).find("[reg2]").blur(function () {
        //获得输入的值
        var val = $(this).val();
        val = $.trim(val);
        //获得正则表达式
        var reg = $(this).attr("reg2");
        //获得提示信息
        var tip = $(this).attr("tip");
        //创建正则表达式对象
        var regExp = new RegExp(reg);
        //前端校验
        if (!regExp.test(val)) {
            $(this).next("span").html("<font color='red'>" + tip + "</font>")
        } else {
            var inputName=$(this).attr("name");
            //品牌名符合前端校验再进行后端校验
            if (inputName=="brandName") {
                //校验品牌名重复性
                var result = validBrandName(val);
                if (result=="no"){
                    $(this).next("span").html("<font color='red'>品牌名称已存在</font>")
                }else {
                    //后台检验成功
                    $(this).next("span").html("");
                }
            }else {
                //前端校验成功
                $(this).next("span").html("");
            }
        }
    });

    //校验可选字段
    $(this).find("[reg1]").blur(function () {
        //获得输入的值
        var val = $(this).val();
        val = $.trim(val);
        //获得正则表达式
        var reg = $(this).attr("reg1");
        //获得提示信息
        var tip = $(this).attr("tip");
        //创建正则表达式对象
        var regExp = new RegExp(reg);
        if (val != null && val !== "" && !regExp.test(val)) {
            $(this).next("span").html("<font color='red'>" + tip + "</font>")
        } else {
            $(this).next("span").html("");
        }
    });

});

/**
 * 品牌名称重复性校验
 * @param brandName
 */
//ajax 默认异步 设置同步 不然可能结果没返回 就已经return result了
function validBrandName(brandName) {
    var result = "yes";
    $.ajax({
        url: path + "/item/validBrandName.do",
        type:"post",
        async:false,
        data:{
            brandName:brandName},
        dataType:"text",
        success:function (data) {
            var dataObj = eval("("+data+")");
            result=dataObj.flag;
        },
        error:function () {
            alert("品牌名称校验错误");
        }
    });
    return result;
}