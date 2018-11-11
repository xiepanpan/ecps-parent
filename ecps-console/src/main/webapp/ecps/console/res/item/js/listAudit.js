$(function () {
    //获得上下架状态 只要在页面中获取值都是String类型
    var auditStatus = parseInt($("#auditStatus").val());
    //0表示待审核
    if (auditStatus==0) {
         $("#label1").attr("class","here");
    }else if (auditStatus==1) {
        //审核通过
        $("#label3").attr("class","here");
    }else if (auditStatus==2){
        //审核不通过
        $("#label2").attr("class","here");
    }else {
        //全部
        $("#label4").attr("class","here");
    }

    //获得隐藏域的值
    var pageNo = parseInt($("#currentPageNo").val());
    var totalPage = parseInt($("#totalPage").val());

    //只有一页数据 都隐藏
    if(pageNo==1 && pageNo == totalPage) {
        $("#firstPage").hide();
        $("#lastPage").hide();
        $("#previous").hide();
        $("#next").hide();
    }else if (pageNo==1 && totalPage>pageNo) {
        //不止一页 当前页为第一页
        $("#firstPage").hide();
        $("#lastPage").show();
        $("#previous").hide();
        $("#next").show();
    }else if (pageNo>1 && totalPage>pageNo) {
        //当前页不是第一页
        $("#firstPage").show();
        $("#lastPage").show();
        $("#previous").show();
        $("#next").show();
    }else if (pageNo==totalPage&&totalPage>1){
        //最后一页
        $("#firstPage").show();
        $("#lastPage").hide();
        $("#previous").show();
        $("#next").hide();
    }
    $("#next").click(function () {
        pageNo++;
        $("#pageNo").val(pageNo);
        $("#form1").submit();
    });
    $("#previous").click(function () {
        pageNo--;
        $("#pageNo").val(pageNo);
        $("#form1").submit();
    });
    $("#firstPage").click(function () {
        $("#pageNo").val(1);
        $("#form1").submit();
    });
    $("#lastPage").click(function () {
        $("#pageNo").val(totalPage);
        $("#form1").submit();
    });

    $("#selectPage").change(function () {
        var pageNo = $(this).val();
        $("#pageNo").val(pageNo);
        $("#form1").submit();
    })

})