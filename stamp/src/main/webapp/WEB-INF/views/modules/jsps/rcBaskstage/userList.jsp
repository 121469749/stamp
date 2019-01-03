<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/27
  Time: 16:14
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
            if (n) $("#pageNo").val(n);
            if (s) $("#pageSize").val(s);
            <%--$("#searchForm").attr("action","${ctx}/rc/baskstage/user/list");--%>
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>

<ul class="nav nav-tabs">
    <li class="active"><a href="#">用户列表</a></li>
    <li><a href="${ctx}/rc/baskstage/user/police/form">公安机关管理员添加</a></li>
</ul>
<form:form id="searchForm" modelAttribute="checkUser" action="${ctx}/rc/baskstage/user/list" method="post"
           class="breadcrumb form-search ">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>用户类型:</label>
            <form:select path="userType">
                <form:option value="ADMIN">系统管理员</form:option>
                <form:option value="USE">用章公司</form:option>
                <form:option value="MAKE">制章点</form:option>
                <form:option value="AGENY">经销商</form:option>
                <form:option value="POLICE">公安部门</form:option>
            </form:select>
            <c:choose>
                <c:when test="${checkUser.userType.key == '1'}">

                </c:when>
                <c:when test="${checkUser.userType.key == '5'}">
                    <li><label>所属区域：</label>
                    <sys:treeselect id="area" name="policeInfo.area.id" value="${checkUser.policeInfo.area.id}" labelName="policeInfo.area.name" labelValue="${checkUser.policeInfo.area.name}"
                                    title="区域" url="/sys/area/treeData" cssClass="required"  allowClear="true"/>
                    </li>
                </c:when>
                <c:otherwise >
                    <li><label>所属区域：</label>
                    <sys:treeselect id="area" name="companyInfo.area.id" value="${checkUser.companyInfo.area.id}" labelName="companyInfo.area.name" labelValue="${checkUser.companyInfo.area.name}"
                                    title="区域" url="/sys/area/treeData" cssClass="required"  allowClear="true"/>
                    </li>
                    <li><label>公司名称:</label>
                        <form:input path="companyInfo.companyName" htmlEscape="false" maxlength="50" class="input-medium"></form:input>
                    </li>
                </c:otherwise>
            </c:choose>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"
                                onclick="return page();"/>
    </ul>
</form:form>

<div class="header">
    <div id="messageBox" class="alert ${flag==1?'alert-success':'alert-error'} ${empty message ? 'hide' : ''}"><button data-dismiss="alert" class="close">×</button>
        <label id="loginError" class="${flag==1?'success':'error'}">${message}</label>
    </div>
</div>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th class="sort-column login_name">登录名</th>
        <th class="sort-column name">姓名</th>
        <th>用户类型</th>
        <%--<th>电话</th>--%>
        <th>手机</th>
        <c:choose>
            <c:when test="${checkUser.userType.key == 1}">
            </c:when>
            <c:when test="${checkUser.userType.key == 2}">
                <th>公司名称</th>
                <th>企业统一码</th>
                <th>管辖区域</th>
                <th>公司法人</th>
                <th>法人电话</th>
                <th>公司管理员</th>
            </c:when>
            <c:when test="${checkUser.userType.key == 3}">
                <th>公司名称</th>
                <th>企业统一码</th>
                <th>所属区域</th>
                <th>公司法人</th>
                <th>法人电话</th>
                <th>公司管理员</th>
            </c:when>
            <c:when test="${checkUser.userType.key == 4}">
                <th>公司名称</th>
                <th>企业统一码</th>
                <th>所属区域</th>
                <th>公司法人</th>
                <th>法人电话</th>
                <th>公司管理员</th>
            </c:when>
            <c:when test="${checkUser.userType.key == 5}">
                <th>管辖区域</th>
                <th>负责人</th>
                <th>电话</th>
                <th>单位地址</th>
                <th>单位管理员</th>
            </c:when>
        </c:choose>
        <th>创建日期</th>
        <th>上次更新时间</th>
        <th>操作人</th>
        <th>帐号状态</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="user">
        <tr>
            <td>
                <c:if test="${user.userType.key==5}">
                    <a href="${ctx}/rc/baskstage/user/police/form?id=${user.id}&userType=${user.userType}">${user.loginName}</a>
                </c:if>
                <c:if test="${user.userType.key!=5}">
                    ${user.loginName}
                </c:if>
            </td>
            <td>${user.name}</td>
            <td>${user.userType.value}</td>
            <td>${user.phone}</td>
            <%--<td>${user.mobile}</td>--%>


            <c:choose>

                <c:when test="${checkUser.userType.key == 1}">

                </c:when>

                <c:when test="${checkUser.userType.key == 2}">
                    <td>${user.companyInfo.companyName}</td>
                    <td>${user.companyInfo.soleCode}</td>
                    <td>${user.companyInfo.area.name}</td>
                    <td>${user.companyInfo.legalName}</td>
                    <td>${user.companyInfo.legalPhone}</td>
                    <td>
                        <c:if test="${user.isSysrole== 1}">
                            是
                        </c:if>
                        <c:if test="${user.isSysrole == 0}">
                            否
                        </c:if>
                    </td>
                </c:when>

                <c:when test="${checkUser.userType.key == 3}">
                    <td>${user.companyInfo.companyName}</td>
                    <td>${user.companyInfo.soleCode}</td>
                    <td>${user.companyInfo.area.name}</td>
                    <td>${user.companyInfo.legalName}</td>
                    <td>${user.companyInfo.legalPhone}</td>
                    <td>
                        <c:if test="${user.isSysrole== 1}">
                            是
                        </c:if>
                        <c:if test="${user.isSysrole == 0}">
                            否
                        </c:if>
                    </td>
                </c:when>

                <c:when test="${checkUser.userType.key == 4}">
                    <td>${user.companyInfo.companyName}</td>
                    <td>${user.companyInfo.soleCode}</td>
                    <td>${user.companyInfo.area.name}</td>
                    <td>${user.companyInfo.legalName}</td>
                    <td>${user.companyInfo.legalPhone}</td>
                    <td>
                        <c:if test="${user.isSysrole== 1}">
                            是
                        </c:if>
                        <c:if test="${user.isSysrole == 0}">
                            否
                        </c:if>
                    </td>
                </c:when>

                <c:when test="${checkUser.userType.key == 5}">
                    <td>${user.policeInfo.area.name}</td>
                    <td>${user.policeInfo.principal}</td>
                    <td>${user.policeInfo.phone}</td>
                    <td>${user.policeInfo.address}</td>
                    <td>
                        <c:if test="${user.isSysrole== 1}">
                            是
                        </c:if>
                        <c:if test="${user.isSysrole == 0}">
                            否
                        </c:if>
                    </td>
                </c:when>
            </c:choose>
            <td><fmt:formatDate value="${user.createDate}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
            <td><fmt:formatDate value="${user.updateDate}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
            <td>${user.updateBy.name}</td>
            <td>
                <c:if test="${user.loginFlag== 1}">
                    <c:if test="${loginUser.loginName != user.loginName}">
                        <a href="${ctx}/rc/baskstage/user/changeLoginFlag?id=${user.id}&userType=${user.userType}&loginFlag=0">帐号停用</a>
                    </c:if>
                </c:if>
                <c:if test="${user.loginFlag == 0}">
                    <a href="${ctx}/rc/baskstage/user/changeLoginFlag?id=${user.id}&userType=${user.userType}&loginFlag=1">帐号启用</a>
                </c:if>
            </td>
            <td>
                <c:if test="${user.isSysrole=='1' }">
                    <c:if test="${loginUser.loginName != user.loginName}">
                        <a href="${ctx}/rc/baskstage/user/reset/page?id=${user.id}&userType=${user.userType}&">重置密码</a>
                    </c:if>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>