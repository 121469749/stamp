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
<form:form id="searchForm" modelAttribute="company"  method="post" class="breadcrumb form-search ">
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
       <%-- <li><label>所属区域:</label>
            <sys:treeselect id="area" name="area.id" value="${company.area.id}" labelName="area.name"
                            labelValue="${company.area.name}"
                            title="区域" url="/sys/area/treeData" cssClass="required"/>
        </li>--%>
        <div style="display: none;">
            <form:select path="compType">
                <form:option value="USE">用章公司</form:option>
            </form:select>
        </div>

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
        <th>创建时间</th>
        <th>公司法人</th>
        <th>法人电话</th>
        <th>操作</th>
    </tr>
    <tbody>
    <c:forEach items="${page.list}" var="company">
        <tr>
            <td>${company.companyName}</td>
            <td>${company.soleCode}</td>
            <td>${company.area.name}</td>
            <td>${company.compPhone}</td>
            <td><fmt:formatDate value="${company.createDate}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
            <td>${company.legalName}</td>
            <td>${company.legalPhone}</td>
            <td>
                <c:if test="${company.stamp.stampState.key ==4}">
                    <a href="${ctx}/policeStampPage/forwardModifyPage?company.id=${company.id}" class="btn btn-default">变更</a>
                </c:if>
                <c:if test="${company.stamp.stampState.key !=4}">
                    此公司名下没有可用印章
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>
