<%--
  Created by IntelliJ IDEA.
  User: hjw-pc
  Date: 2017/6/16
  Time: 15:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>用户管理</title>
    <meta name="decorator" content="default"/>

</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/dealer/city/sub/user/list">用户列表</a></li>
    <li><a href="${ctx}/dealer/city/sub/user/form">用户添加</a></li>
</ul>
<form:form id="searchForm" modelAttribute="user" action="${ctx}/dealer/city/sub/user/list" method="post" class="breadcrumb form-search ">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
    <ul class="ul-form">
        <li><label>登录名：</label><form:input path="loginName" htmlEscape="false" maxlength="50" class="input-medium"/></li>
        <li class="clearfix"></li>

        <li><label>姓&nbsp;&nbsp;&nbsp;名：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/></li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>

        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead><tr><th class="sort-column login_name">登录名</th><th class="sort-column name">姓名</th><th>电话</th><th>手机</th>
        <%--<th>角色</th> --%><th>操作</th></tr></thead>
    <tbody>
    <c:forEach items="${page.list}" var="user">
        <tr>
            <td><a href="${ctx}id=${user.id}">${user.loginName}</a></td>
            <td>${user.name}</td>
            <td>${user.phone}</td>
            <td>${user.mobile}</td><%--
				<td>${user.roleNames}</td> --%>
            <td>
                <a href="${ctx}/dealer/city/sub/user/changeLoginFlag?id=${user.id}">  ${user.loginFlag eq 1 ?'禁止登录':'允许登录'}</a>
                <a href="${ctx}/dealer/city/sub/user/form?id=${user.id}">修改</a>
                <a href="${ctx}/dealer/city/sub/user/delete?id=${user.id}" onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
