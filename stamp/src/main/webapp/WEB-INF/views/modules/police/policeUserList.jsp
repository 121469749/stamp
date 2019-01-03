<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>用户管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">

        function page(n, s) {
            if (n) $("#pageNo").val(n);
            if (s) $("#pageSize").val(s);
            $("#searchForm").attr("action", "${ctx}/police/user/list");
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/police/user/list">用户列表</a></li>
    <c:if test="${subAreaNumbers != 0}">
        <li><a href="${ctx}/police/user/form">用户添加</a></li>
    </c:if>

</ul>
<form:form modelAttribute="area" id="searchForm" action="${ctx}/police/user/list" method="post"
           class="breadcrumb form-search ">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <li>
        <c:if test="${subAreaNumbers != 0}">
            <div class="control-group">
                <label class="control-label">管辖区域:</label>

                <div class="controls">
                    <sys:treeselect id="area" name="id" value="${area.id}" labelName="name" labelValue="${area.name}"
                                    title="区域" url="/sys/area/custom/currentPoliceAreaALayer"/>
                </div>
            </div>
        </c:if>
    </li>
    <li class="btns">
        <input id="btnSubmit" <c:if test="${subAreaNumbers == 0}">style="display: none"</c:if>  class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
    </li>
</form:form>
<div class="header">
    <div id="messageBox" class="alert ${flag==1?'alert-success':'alert-error'} ${empty message ? 'hide' : ''}">
        <button data-dismiss="alert" class="close">×</button>
        <label id="loginError" class="${flag==1?'success':'error'}">${message}</label>
    </div>
</div>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>管理区域</th>
        <th>该区域管理员</th>
        <th>区域负责人</th>
        <th>负责人电话</th>
        <th class="sort-column login_name">登录名</th>
        <th class="sort-column name">姓名</th>
        <th>电话</th>
        <th>手机</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="policeUser">
        <tr>
            <td>${policeUser.policeInfo.area.name}</td>
            <td>
                <c:if test="${policeUser.isSysrole == 0}">
                    否
                </c:if>

                <c:if test="${policeUser.isSysrole == 1}">
                    是
                </c:if>
            </td>
            <td>${policeUser.policeInfo.principal}</td>
            <td>${policeUser.policeInfo.phone}</td>
            <td><a href="#">${policeUser.loginName}</a></td>
            <td>${policeUser.name}</td>
            <td>${policeUser.phone}</td>
            <td>${policeUser.mobile}</td>

            <td>
                <c:choose>

                    <c:when test="${currentPolice.area.id != policeUser.policeInfo.area.id && policeUser.isSysrole == 1}">
                        <a href="${ctx}/police/user/form?id=${policeUser.id}&userType=${policeUser.userType}">修改</a>
                    </c:when>

                </c:choose>

            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>