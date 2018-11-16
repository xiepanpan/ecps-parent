$(function () {
    $("#province").change(function () {
        //获得选中的省
        var areaId = $(this).val();
        loadOption(areaId,"mycity");
    })
    $("#mycity").change(function () {
        var areaId = $(this).val();
        loadOption(areaId,"district");
    })

    /**
     * 新增收货地址前校验 不能超过5个
     */
    $("#jvForm").submit(function () {
        var isSubmit = true;
        var shipAddrId = $("#shipAddrId").val();
        if (shipAddrId==null || shipAddrId=="") {
            var addrLength = parseInt($("#addrLength").val());
            if (addrLength>=5) {
                isSubmit=false;
                alert("收货地址不不能超过5个")
            }
        }
        return isSubmit;
    })
})

/**
 * 加载下一级的下拉框
 * @param areaId 父节点id
 * @param selectId 下一级下拉框的id
 */
function loadOption(areaId,selectId) {
    $.ajax({
        url:path+"/user/login/getChildArea.do",
        type:"post",
        dataType:"text",
        data:{
            areaId:areaId
        },
        async:false,
        success:function (data) {
            // alert(data);
            $("#"+selectId).empty();
            var jsonObj = $.parseJSON(data);
            var ebAreaList = jsonObj.ebAreaList;
            
            if (selectId == "mycity") {
                $("#mycity").empty();
                $("#district").empty();
                $("#mycity").append("<option value='-1'>城市</option> ");
                $("#district").append("<option value='-1'>区县</option>")
            }else {
                $("#district").empty();
                $("#district").append("<option value='-1'>区县</option>")
            }

            if (ebAreaList!=null && ebAreaList.length>0) {
                var option ="";
                for (var i= 0;i<ebAreaList.length;i++){
                 option=option+"<option value='"+ebAreaList[i].areaId+"'>"+ebAreaList[i].areaName+"</option>"
                }
                $("#"+selectId).append(option);
            }

        }
    })
}
function modify(shipAddrId) {
    $.ajax({
        url:path+"/user/login/getAddrById.do",
        type:"post",
        data:{
            shipAddrId:shipAddrId
        },
        success:function (data) {
            var jsonObj = $.parseJSON(data);
            $("#shipAddrId").val(jsonObj.ebShipAddr.shipAddrId);
            $("#shipName").val(jsonObj.ebShipAddr.shipName);
            $("#province").val(jsonObj.ebShipAddr.province);
            loadOption(jsonObj.ebShipAddr.province,"mycity");
            $("#mycity").val(jsonObj.ebShipAddr.city);
            loadOption(jsonObj.ebShipAddr.city,"district");
            $("#district").val(jsonObj.ebShipAddr.district);
            $("#addr").val(jsonObj.ebShipAddr.addr);
            $("#zipCode").val(jsonObj.ebShipAddr.zipCode);
            $("#phone").val(jsonObj.ebShipAddr.phone);
            if (jsonObj.ebShipAddr.defaultAddr==1){
                $("#defaultAddr").attr("checked","checked");
            }else {
                $("#defaultAddr").removeAttr("checked");
            }
        }
    })

}