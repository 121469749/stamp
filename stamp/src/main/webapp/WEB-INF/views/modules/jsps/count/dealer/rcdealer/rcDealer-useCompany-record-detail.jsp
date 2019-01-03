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
    <title>用章企业备案统计</title>
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
</head>
<body>
<%--输入页面名称--%>
<h3 style="width: 80%;margin: 10px auto">用章企业备案统计</h3>

<table class="table table-striped table-bordered" style="width: 93%;margin: 10px auto">
    <tr>
        <th colspan="4">${useCompany.companyName}</th>
    </tr>
    <tr>
        <th>备案日期</th>
        <th>备案类型</th>
        <th>备案印章</th>
        <th>数量</th>
    </tr>
    <c:forEach items="${list}" var="stampRecord">
        <tr>
            <td><fmt:formatDate value="${stampRecord.createDate}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></td>
            <td>${stampRecord.workType.value}</td>
            <td>
                <c:forEach items="${stampRecord.stamps}" var="stamp">
                    ${stamp.stampName},
                    ${fns:getDictLabel(stamp.stampShape,'stampShape' ,null )}
                    <br/>
                </c:forEach>
            </td>
            <td>${fn:length(stampRecord.stamps)}</td>
        </tr>
    </c:forEach>


</table>
<div class="form-actions" style="text-align: center;padding-left:0;">
    <input id="btnCancel" class="btn btn-success showcod" type="button" value="返 回" onclick="history.go(-1)"/>
</div>
</body>
</html>
