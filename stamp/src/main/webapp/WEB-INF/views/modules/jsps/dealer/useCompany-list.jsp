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
    <title>刻章单位-列表</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxHtml}/css/paging.css?id=${fns:getConfig('css.version')}">
    <script language="JavaScript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script src="${ctxHtml}/js/bootstrap.min.js"></script>
    <style>
        table input{
            width: 100%;
            border: none;
            background: transparent;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function() {

        });

        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<%--不知道有没有用的东西--%>

<%--输入页面名称--%>
<h3 style="width: 80%;margin: 10px auto">用章单位列表</h3>
<form:form id="searchForm" modelAttribute="company" action="${ctx}/dealer/useCompany/list" method="post" cssStyle="text-align: center">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <%--搜索--%>
    <label for="companyName" >用章公司名称:</label>
    <form:input path="companyName"  type="text" cssClass="form-control" cssStyle="width: 200px;display: inline"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
<sys:message content="${message}"/>
<table class="table table-striped table-bordered" style="width: 93%;margin: 10px auto">
    <tr>
        <%--以下th标签为列表的头--%>
            <th>用章公司名称</th>
            <th>公司类型</th>
            <th>归属区域</th>
            <th>公司法人</th>
            <th>社会统一码</th>
        <th width="200px">操作</th>
    </tr>
    <c:forEach items="${page.list}" var="useCompany">
        <tr>
            <td>${useCompany.companyName}</td>
            <td>${useCompany.compType.value}</td>
            <td>${useCompany.area.name}</td>
            <td>${useCompany.legalName}</td>
            <td>${useCompany.soleCode}</td>
                <%--按钮--%>
            <td width="200px">
                <div style="width: 200px!important;">
                    <a id="check" class="btn btn-block btn-default" href="${ctx}/dealer/company/detail?companyId=${useCompany.id}&companyType=${useCompany.compType}" style="display: inline">查看</a>
                    <shiro:hasPermission name="dealer:company:edit">
                    <c:if test="${useCompany.sysOprState.key == 1}">
                        <a id="check" class="btn btn-block btn-default" href="${ctx}/dealer/systemOpration/stop?companyId=${useCompany.id}&companyType=${useCompany.compType}" style="display: inline">管控停用</a>
                    </c:if>
                    <c:if test="${useCompany.sysOprState.key == 2}" >
                        <a id="check" class="btn btn-block btn-default" href="${ctx}/dealer/systemOpration/start?companyId=${useCompany.id}&companyType=${useCompany.compType}" style="display: inline">管控启用</a>
                    </c:if>
                    </shiro:hasPermission>
                </div>
            </td>
        </tr>
    </c:forEach>
</table>
<div class="pagination" style="margin-left: 20%">
    ${page}
</div>
</html>
