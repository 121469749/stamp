<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="decorator" content="default"/>
    <title>已交付印章</title>
    <%--<link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css"/>--%>
    <%--<link rel="stylesheet" href="${ctxHtml}/css/paging.css"/>--%>
    <%--<script src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>--%>
    <script type="text/javascript">
        $(document).ready(function () {

        });

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }

        /**
         * 格式化日期
         * @param date 日期
         * @param format 格式化样式,例如yyyy-MM-dd HH:mm:ss E
         * @return 格式化后的金额
         */
        function formatDate(date, format) {
            var v = "";
            if (typeof date == "string" || typeof date != "object") {
                return;
            }
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var day = date.getDate();
            var hour = date.getHours();
            var minute = date.getMinutes();
            var second = date.getSeconds();
            var weekDay = date.getDay();
            var ms = date.getMilliseconds();
            var weekDayString = "";

            if (weekDay == 1) {
                weekDayString = "星期一";
            } else if (weekDay == 2) {
                weekDayString = "星期二";
            } else if (weekDay == 3) {
                weekDayString = "星期三";
            } else if (weekDay == 4) {
                weekDayString = "星期四";
            } else if (weekDay == 5) {
                weekDayString = "星期五";
            } else if (weekDay == 6) {
                weekDayString = "星期六";
            } else if (weekDay == 7) {
                weekDayString = "星期日";
            }

            v = format;
            //Year
            v = v.replace(/yyyy/g, year);
            v = v.replace(/YYYY/g, year);
            v = v.replace(/yy/g, (year + "").substring(2, 4));
            v = v.replace(/YY/g, (year + "").substring(2, 4));

            //Month
            var monthStr = ("0" + month);
            v = v.replace(/MM/g, monthStr.substring(monthStr.length - 2));

            //Day
            var dayStr = ("0" + day);
            v = v.replace(/dd/g, dayStr.substring(dayStr.length - 2));

            //hour
            var hourStr = ("0" + hour);
            v = v.replace(/HH/g, hourStr.substring(hourStr.length - 2));
            v = v.replace(/hh/g, hourStr.substring(hourStr.length - 2));

            //minute
            var minuteStr = ("0" + minute);
            v = v.replace(/mm/g, minuteStr.substring(minuteStr.length - 2));

            //Millisecond
            v = v.replace(/sss/g, ms);
            v = v.replace(/SSS/g, ms);

            //second
            var secondStr = ("0" + second);
            v = v.replace(/ss/g, secondStr.substring(secondStr.length - 2));
            v = v.replace(/SS/g, secondStr.substring(secondStr.length - 2));

            //weekDay
            v = v.replace(/E/g, weekDayString);
            return v;
        }
    </script>
    <style>
        table input {
            height: 20px;
            width: 100%;
            border: none;
            background: transparent;
        }
        .accordion-heading, .table th {
            white-space: nowrap;
            background-color: #E5E5E5;
            background-image: -moz-linear-gradient(top, #EAEAEA, #E5E5E5);
            background-image: -ms-linear-gradient(top,#EAEAEA,#E5E5E5);
            background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#EAEAEA), to(#E5E5E5));
            background-image: -webkit-linear-gradient(top,#EAEAEA,#E5E5E5);
            background-image: -o-linear-gradient(top,#EAEAEA,#E5E5E5);
            background-image: linear-gradient(top,#EAEAEA,#E5E5E5);
            background-repeat: repeat-x;
        }
    </style>
</head>
<body style="width: 80%;margin: 25px auto">
<h4 style="margin-bottom: 5px;">当前路径：首页 \ 已交付印章</h4>
<form:form id="searchForm" modelAttribute="stamp" action="${ctx}/stampMakePage/toDeliveryList" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <label for="stampShape">印章章型:</label>
    <form:select path="stampShape" class="input-medium">
        <form:option value="">全部</form:option>
        <form:option value="1">物理印章</form:option>
        <form:option value="2">电子印章</form:option>
    </form:select>
    <label>印章编码:</label>
    <form:input path="stampCode"></form:input>

    <label>企业名称:</label>
    <form:input path="lastRecord.companyName"></form:input>

    <label>交付日期:</label>
    <input name="deliveryDate" id="deliveryDate" type="text" readonly="readonly" maxlength="20"
           class="input-medium Wdate "
           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"
    />
    <input name="fakemakeDate" id="fakemakeDate" type="hidden" readonly="readonly" maxlength="20"
           class="input-medium Wdate " value="1990-01-01 00:00:00"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onmouseover="dateAdd();"/>
</form:form>
<script>
    function dateAdd() {
        var startD = document.getElementById("deliveryDate").value;
//        alert(startDate);
        var startDate = new Date(startD);
//        startDate = +startDate + 1000 * 60 * 60;
//        startDate = new Date(startDate);
//        var nextStartDate = startDate.getFullYear() + "-" + (startDate.getMonth() + 1) + "-" + startDate.getDate() + " " + startDate.getHours() + ":" + startDate.getMinutes() + ":" + startDate.getSeconds();
//        alert(nextStartDate);
        var df = formatDate(startDate, "yyyy-MM-dd 23:59:59");
//        alert(startD);
        if (startD.length !== 0) {
            document.getElementById("fakemakeDate").value = formatDate(startDate, "yyyy-MM-dd 23:59:59");
        }
    }
</script>
<br>
<h5 style="color: red;float:right;margin-right: 20px;">如企业信息发生过变更, 新详情显示变更后的企业信息, 详情显示申请备案时的企业信息</h5>
<center>
<table class="table table-bordered" style="width: 98%;">
    <tbody>
    <tr style="font-size: large;">
        <th>印章编码</th>
        <th>企业名称</th>
        <%--<th>印章名称</th>--%>
        <th>印章类型</th>
        <th>物理/电子印章</th>

        <th>交付日期</th>
        <th>状态</th>

        <th>操作</th>
    </tr>
    <c:forEach items="${page.list}" var="stamp">
        <tr style="font-size: medium;">
            <td>
                    ${stamp.stampCode}
            </td>
            <td>${stamp.lastRecord.companyName}</td>
            <td>
                    ${fns:getDictLabel(stamp.stampType,"stampType" ,null )}
            </td>
            <td>
                    ${fns:getDictLabel(stamp.stampShape,"stampShape" ,null )}
            </td>

            <td><fmt:formatDate value="${stamp.deliveryDate}" pattern="yyyy-MM-dd"/></td>
            <td>${stamp.useState.value}</td>
            <td>
                <c:if test="${stamp.modifyInfoLog == null}">
                    <a href="${ctx}/stampMakePage/toStampInfo?id=${stamp.id}&stampShape=${stamp.stampShape}">详情</a>
                </c:if>
                <c:if test="${stamp.modifyInfoLog != null}">
                    <a href="${ctx}/stampMakePage/toStampInfo?id=${stamp.id}&stampShape=${stamp.stampShape}" >详情</a>
                    <a href="${ctx}/stampMakePage/toNewStampInfo?id=${stamp.id}&stampShape=${stamp.stampShape}&useComp.id=${stamp.useComp.id}" style="margin-left: 10px;">新详情</a>
                </c:if>

            </td>
        </tr>
    </c:forEach>

    </tbody>
</table>
</center>
<div class="pagination" style="display: block;margin-left: 27%">${page}</div>

</body>
</html>