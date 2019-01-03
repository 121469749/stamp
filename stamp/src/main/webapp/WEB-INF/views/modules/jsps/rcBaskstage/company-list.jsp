<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/29
  Time: 13:32
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
    <li class="active"><a href="#">用户列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="check" action="#" method="post" class="breadcrumb form-search ">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
    <ul class="ul-form">

        <li><label>公司名称：</label>
            <form:input path="companyName" htmlEscape="false" maxlength="50" class="input-medium"/>
        </li>
        <li><label>统一码：</label>
            <form:input path="soleCode" htmlEscape="false" maxlength="50" class="input-medium"/>
        </li>
        <li><label>所属区域:</label>
            <sys:treeselect id="area" name="area.id" value="${check.area.id}" labelName="area.name"
                            labelValue="${check.area.name}"
                            title="区域" url="/sys/area/treeData" cssClass="required"/>
        </li>
        <li>
            <label>公司系统类型</label>
            <form:select path="compType">
                <form:option value="MAKE">刻章单位</form:option>
                <form:option value="USE">用章公司</form:option>
                <form:option value="AGENCY">经销商</form:option>
            </form:select>
        </li>

            <%--<li><label>姓&nbsp;&nbsp;&nbsp;名：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/></li>--%>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"
                                onclick="return page();"/>

        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>公司名字</th>
        <th>公司统一码</th>
        <th>所属区域</th>
        <th>公司电话</th>
        <th>公司系统类型</th>
        <th>创建时间</th>
        <th>公司法人</th>
        <th>法人电话</th>
        <c:choose>
            <c:when test="${company.compType.key == 1}">
                <th>公司状态</th>
                <th>系统管控状态</th>
                <th>操作</th>
            </c:when>
            <c:when test="${company.compType.key == 2}">
                <th>系统管控状态</th>
                <th>操作</th>
            </c:when>
            <c:when test="${company.compType.key == 3}">

            </c:when>
        </c:choose>
    </tr>
    <tbody>
    <c:forEach items="${page.list}" var="company">
        <tr>
            <td>${company.companyName}</td>
            <td>${company.soleCode}</td>
            <td>${company.area.name}</td>
            <td>${company.compPhone}</td>
            <td>${company.compType.value}</td>
            <td><fmt:formatDate value="${company.createDate}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
            <td>${company.legalName}</td>
            <td>${company.legalPhone}</td>
            <c:choose>
                <c:when test="${company.compType.key == 1}">
                    <td>${company.compState.value}</td>
                    <td>${company.sysOprState.value}</td>
                    <td>
                        <c:choose>
                            <c:when test="${company.sysOprState.key ==1 }">
                                <a href="${ctx}/sys/company/systemOpration/stop?companyId=${company.id}&companyType=${company.compType}">停用</a>
                            </c:when>
                            <c:when test="${company.sysOprState.key ==2 }">
                                <a href="${ctx}/sys/company/systemOpration/start?companyId=${company.id}&companyType=${company.compType}">启用</a>
                            </c:when>
                        </c:choose>
                    </td>
                </c:when>
                <c:when test="${company.compType.key == 2}">
                    <td>${company.sysOprState.value}</td>
                    <td>
                        <c:choose>
                            <c:when test="${company.sysOprState.key ==1 }">
                                <a href="${ctx}/sys/company/systemOpration/stop?companyId=${company.id}&companyType=${company.compType}">停用</a>
                            </c:when>
                            <c:when test="${company.sysOprState.key ==2 }">
                                <a href="${ctx}/sys/company/systemOpration/start?companyId=${company.id}&companyType=${company.compType}">启用</a>
                            </c:when>
                        </c:choose>
                    </td>
                </c:when>
                <c:when test="${company.compType.key == 3}">

                </c:when>
            </c:choose>

        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
