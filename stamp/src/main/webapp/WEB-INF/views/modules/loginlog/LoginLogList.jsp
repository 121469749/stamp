<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/28
  Time: 17:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>用户管理</title>
    <meta name="decorator" content="default"/>
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
<ul class="nav nav-tabs">
    <li class="active"><a href="#">登陆日志</a></li>
</ul>
<form:form id="searchForm" modelAttribute="loginlog" action="#" method="post" class="breadcrumb form-search ">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
    <ul class="ul-form">
        <li><label>登录名：</label><form:input path="loginName" htmlEscape="false" maxlength="50" class="input-medium"/>
        </li>
        <li><label>用户类型:</label>
            <form:select path="userType" cssStyle="width: 100px;">
                <form:option value=""></form:option>
                <form:option value="ADMIN">系统管理员</form:option>
                <form:option value="USE">用章单位</form:option>
                <form:option value="MAKE">制章点</form:option>
                <form:option value="AGENY">经销商</form:option>
                <form:option value="POLICE">公安部门</form:option>
            </form:select>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"
                                onclick="return page();"/>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>登录名</th>
        <th>用户类型</th>
        <th>登陆ip</th>
        <th>操作时间</th>
        <th>操作类型</th>
        <th>操作结果</th>
    <tbody>
    <c:forEach items="${page.list}" var="loginlog">
        <tr>
            <td>${loginlog.loginName}</td>
            <td>${loginlog.userType.value}</td>
            <td>${loginlog.ip}</td>
            <td>
            <fmt:formatDate value="${loginlog.loginDate}"  type="both" ></fmt:formatDate>
            </td>
            <td>${loginlog.loginType.value}</td>
            <td>${loginlog.loginStatus.value}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>