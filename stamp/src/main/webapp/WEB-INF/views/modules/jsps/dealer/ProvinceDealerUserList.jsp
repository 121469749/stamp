<%--
  Created by IntelliJ IDEA.
  User: hjw-pc
  Date: 2017/6/16
  Time: 15:04
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
    <li class="active"><a href="${ctx}/dealer/province/user/list">用户列表</a></li>

    <li><a href="${ctx}/dealer/province/user/form">经销商${(empty user.id)?'添加':'修改' }</a></li>

</ul>
<form:form id="searchForm" modelAttribute="company" action="${ctx}/dealer/province/user/list" method="post"
           class="breadcrumb form-search ">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
    <ul class="ul-form">
        <li>
            <label for="area">区域搜索:</label>
            <sys:treeselect id="area" name="area.id" value="${company.area.id}" labelName="area.name"
                            labelValue="${company.area.name}"
                            title="区域" url="/sys/area/custom/currentCompanyArea" cssClass="required"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"
                                onclick="return page();"/>

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
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="company">
        <tr>
            <td>${company.companyName}</td>
            <td>${company.compPhone}</td>
            <td>${company.legalName}</td>
            <td>${company.legalPhone}</td>
            <td>${company.area.name}</td>
                <%--<a href="${ctx}/dealer/user/changeLoginFlag?id=${user.id}">${user.loginFlag eq 1 ?'禁止登录':'允许登录'}</a>--%>
            <td>
                <a href="${ctx}/dealer/province/user/form?userTypeId=${company.id}">修改</a>
                <a href="${ctx}/dealer/province/user/delete?id=${company.id}"
                   onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
