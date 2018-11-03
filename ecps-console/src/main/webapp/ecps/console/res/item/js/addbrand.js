function submitUpload() {
    var option={
        url:path+"/upload/uploadPic.do",//上传的url
        dataType:"text",//回调的数据类型
        success:function (responseText) {
            var jsonObj = $.parseJSON(responseText);
            $("#imgsImgSrc").attr("src",jsonObj.realPath);
            $("#imgs").val(jsonObj.relativePath);
            $("#lastRealPath").val(jsonObj.realPath);
        },
        error:function () {
            alert("上传失败");
        }
    }
    //使用ajax方式提交表单 上传文件
    $("#form111").ajaxSubmit(option);
}