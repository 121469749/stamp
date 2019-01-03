<%--
  Created by IntelliJ IDEA.
  User: 77426
  Date: 2017/5/20
  Time: 15:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="renderer" content="webkit">
    <title>刻章点刻制详情统计-印章详情</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxHtml}/css/paging.css?id=${fns:getConfig('css.version')}">
    <script language="JavaScript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script src="${ctxHtml}/js/bootstrap.min.js"></script>
    <style>
        table input {
            width: 100%;
            border: none;
            background: transparent;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function () {

        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<%--输入页面名称--%>
<h3 style="width: 90%;margin: 10px auto">印章刻制详情统计</h3>

<table class="table table-striped table-bordered" style="width: 93%;margin: 10px auto">
    <tr>
        <th colspan="4">${makeCompany.companyName}</th>
    </tr>
    <tr>
        <th>印章类型</th>
        <th>印章备案总数</th>
        <th>物理印章数量(个)
            <table><tr><th>总数</th>&nbsp;<th>启用</th>&nbsp;<th>停用</th>&nbsp;<th>挂失</th>&nbsp;<th>缴销</th></tr></table>
        </th>
        <th>电子印章数量(个)
            <table><tr><th>总数</th><th>启用</th><th>停用</th><th>挂失</th><th>缴销</th></tr></table>
        </th>
    </tr>
    <c:forEach items="${list}" var="stampTypeCount">
        <tr>
            <td>${fns:getDictLabel(stampTypeCount.stampType,'stampType' ,null )}</td>
            <td>${stampTypeCount.count}</td>
            <td><table><tr>
            <td width="28px">${stampTypeCount.phyStampCount.count}</td>
            <td width="28px">${stampTypeCount.phyStampCount.useCount}</td>
            <td width="28px">${stampTypeCount.phyStampCount.stopCount}</td>
            <td width="28px">${stampTypeCount.phyStampCount.logoutCount}</td>
            <td width="28px">${stampTypeCount.phyStampCount.reportCount}</td>
            </tr></table></td>
            <td><table><tr>
            <td width="28px">${stampTypeCount.eleStampCount.count}</td>
            <td width="28px">${stampTypeCount.eleStampCount.useCount}</td>
            <td width="28px">${stampTypeCount.eleStampCount.stopCount}</td>
            <td width="28px">${stampTypeCount.eleStampCount.logoutCount}</td>
            <td width="28px">${stampTypeCount.eleStampCount.reportCount}</td>
            </tr></table></td>
        </tr>
    </c:forEach>


</table>
<%--<div class="pagination" style="margin-left: 20%">--%>
<%--${page}--%>
<%--</div>--%>
<div class="form-actions" style="text-align: center;padding-left:0;">
    <input id="btnCancel" class="btn btn-success showcod" type="button" value="返 回" onclick="history.go(-1)"/>
</div>
</body>
</html>
