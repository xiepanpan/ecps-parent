$(function () {
    //获得上下架状态 只要在页面中获取值都是String类型
    var showStatus = parseInt($("#showStatus").val());
    if (showStatus == 1) {
        $("#label4").attr("class", "here");
    } else if (showStatus == 0) {
        $("#label5").attr("class", "here");
    } else {
        $("#label6").attr("class", "here");
    }

    //获得隐藏域的值
    var pageNo = parseInt($("#currentPageNo").val());
    var totalPage = parseInt($("#totalPage").val());

    //只有一页数据 都隐藏
    if (pageNo == 1 && pageNo == totalPage) {
        $("#firstPage").hide();
        $("#lastPage").hide();
        $("#previous").hide();
        $("#next").hide();
    } else if (pageNo == 1 && totalPage > pageNo) {
        //不止一页 当前页为第一页
        $("#firstPage").hide();
        $("#lastPage").show();
        $("#previous").hide();
        $("#next").show();
    } else if (pageNo > 1 && totalPage > pageNo) {
        //当前页不是第一页
        $("#firstPage").show();
        $("#lastPage").show();
        $("#previous").show();
        $("#next").show();
    } else if (pageNo == totalPage && totalPage > 1) {
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

    $("#addItemNoteConfirm").click(function () {
        var notes = $("#itemNote").val();
        $("#notes").val(notes);
        //提交表单
        $("#showForm").submit();
    });

})

function isShow(itemId, showStatus) {
    $("#itemNote").val("");
    //把itemId 和auditStatus给表单
    $("#itemId").val(itemId);
    $("#showStatus1").val(showStatus);
    tipShow("#addItemNote");
}

function publish(itemId) {
    //遮罩
    tipShow("#refundLoadDiv");
    $.ajax({
        url:path+"/item/publishItem.do",
        type:"post",
        dataType:"text",
        data:{
            itemId:itemId
        },
        success:function (data) {
            if (data=="success") {
                alert("发布成功");
            }else {
                alert("发布失败");
            }
            tipHide("#refundLoadDiv");
        }
    })
}