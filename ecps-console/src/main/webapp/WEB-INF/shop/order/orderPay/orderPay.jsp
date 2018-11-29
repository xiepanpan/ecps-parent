<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" %>
<%@ include file="/ecps/console/common/taglibs.jsp" %>

<head>
    <title>待付款单_订单管理</title>
    <meta name="heading" content="待付款单_订单管理"/>
    <meta name="menu" content="OrderMgmtMenu"/>
    <script type="text/javascript" src="<c:url value='/ecps/console/res/js/jquery.form.js'/>"></script>

    <script type="text/javascript"
            src="<c:url value='/ecps/console/res/plugins/My97DatePicker/WdatePicker.js'/>"></script>
    <script language="javascript" type="text/javascript">
        $(function () {
            var isCall =$("#isCall").val();
            if (isCall=='0') {
                //未付款待外呼
                $("#label2").attr("class","here");
            }else if(isCall=='1') {
                //已外呼
                $("#label3").attr("class","here");
            }else{
                $("#label1").attr("class","here");
            }


        });

    </script>

</head>
<body id="main">

<div class="frameL">
    <div class="menu icon">
        <jsp:include page="/${system}/common/ordermenu.jsp"/>
    </div>
</div>
<input type="hidden" value="${isCall}" id="isCall"/>
<div class="frameR">
    <div class="content">
        <div class="loc icon"><samp class="t12"></samp>当前位置：订单管理&nbsp;&raquo;&nbsp;<span class="gray"
                                                                                         title="待付款单">待付款单</span></div>

        <h2 class="h2_ch">
      <span id="tabs" class="l">
        <a id="label1" href="${path}/order/getOrdersByOrderState.do?orderState=1,0" title="全部" class="nor">全部</a>
        <a id="label2" href="${path}/order/getOrdersByOrderState.do?orderState=1" title="未付款待外呼" class="nor">未付款待外呼</a>
        <a id="label3" href="${path}/order/getOrdersByOrderState.do?orderState=0" title="已外呼" class="nor">已外呼</a>
<%--         <a id="label4" href="${path}/order/orderPay.do?orderState=17"  title="超时作废" class="nor">超时作废</a> --%>
      </span>

        </h2>
        <form action="${path}/order/orderPay.do" id="form1" name="form1" method="post">


            <table cellspacing="0" summary="" class="tab" id="myTable">
                <tr>
                    <th>订单号</th>
                    <th>收货人</th>
                    <th>联系电话</th>
                    <th>支付金额</th>
                    <th>支付方式</th>
                    <th>支付状态</th>
                    <th>下单时间</th>
                    <th>操作时间</th>
                    <th>订单状态</th>
                    <th>操作</th>
                </tr>
                <c:forEach items="${taskBeanList}" var="taskBean">
                    <tr>
                        <td>
                            <a href="${path }/shop/order/orderPay/detail.jsp?orderId=3123">${taskBean.ebOrder.orderNum}</a>
                        </td>
                        <td>${taskBean.ebOrder.shipName}</td>
                        <td>${taskBean.ebOrder.phone}</td>
                        <td>${taskBean.ebOrder.orderSum}</td>
                        <td>在线支付</td>
                        <td>
                            <c:if test="${taskBean.ebOrder.isPaid==1}">已付</c:if>
                            <c:if test="${taskBean.ebOrder.isPaid==0}">未付</c:if>
                        </td>
                        <td class="nwp"><fmt:formatDate value="${taskBean.ebOrder.orderTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td class="nwp"><fmt:formatDate value="${taskBean.ebOrder.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td class="nwp">
                            未付款单
                        </td>
                        <td><a href="${path }/shop/order/orderPay/detail.jsp?orderId=3123">查看</a></td>
                    </tr>
                </c:forEach>

            </table>
        </form>

    </div>
</div>
</body>

