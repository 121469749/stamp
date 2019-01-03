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
    <li class="active"><a href="${ctx}/dealer/user/list">用户列表</a></li>
    <shiro:hasPermission name="dealer:runcheng:user:edit"><li><a href="${ctx}/dealer/user/form">经销商添加</a></li></shiro:hasPermission>
</ul>
<form:form id="searchForm" modelAttribute="user" action="${ctx}/dealer/user/list" method="post" class="breadcrumb form-search ">
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
    <thead>
    <tr>
        <th>经销商公司名</th>
        <th>经销商电话</th>
        <th>经销商法人</th>
        <th>经销商法人电话</th>
        <th>管辖区域</th>
        <th class="sort-column login_name">登录名</th>
        <th class="sort-column name">姓名</th>
        <th>电话</th>
        <th>手机</th>
        <%--<th>角色</th> --%><shiro:hasPermission name="dealer:runcheng:user:edit"><th>操作</th></shiro:hasPermission></tr></thead>
    <tbody>
    <c:forEach items="${page.list}" var="user">
        <tr>
            <td>${user.companyInfo.companyName}</td>
            <td>${user.companyInfo.compPhone}</td>
            <td>${user.companyInfo.legalName}</td>
            <td>${user.companyInfo.legalPhone}</td>
            <td>${user.companyInfo.area.name}</td>
            <td>${user.loginName}</td>
            <td>${user.name}</td>
            <td>${user.phone}</td>
            <td>${user.mobile}</td><%--
				<td>${user.roleNames}</td> --%>
            <shiro:hasPermission name="dealer:runcheng:user:edit"><td>
                <%--<a href="${ctx}/dealer/user/changeLoginFlag?id=${user.id}">${user.loginFlag eq 1 ?'禁止登录':'允许登录'}</a>--%>
               <c:if test="${currentUser.id != user.id}">
                   <a href="${ctx}/dealer/user/form?id=${user.id}&userType=${user.userType}">修改</a>
                   <a href="${ctx}/dealer/user/delete?id=${user.id}&userType=${user.userType}" onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a>
               </c:if>
            </td></shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
