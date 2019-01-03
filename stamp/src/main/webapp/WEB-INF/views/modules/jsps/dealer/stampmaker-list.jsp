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
    <meta name="decorator" content="default"/>
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
        input{
            height: 30px!important;
            margin: 0!important;
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
<%--输入页面名称--%>
<h3 style="width: 90%;margin: 10px auto">刻章单位列表</h3>

<form:form id="searchForm" modelAttribute="company" action="${ctx}/dealer/makeCompany/list" method="post" cssStyle="width: 90%;margin: 0 auto">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <p style="color: #666">当前公司管辖区域:<c:forEach var="area" items="${areas}">${area.name}</c:forEach></p>
        <label class="control-label">区域查询:</label>
            <sys:treeselect id="area" name="area.id" value="${company.area.id}" labelName="area.name" labelValue="${company.area.name}"
                            title="区域" url="/sys/area/treeDataWithAreaId" cssClass="required"/>
    <label for="companyName" >刻章公司名称:</label>
    <form:input path="companyName"  type="text" cssClass="form-control" cssStyle="width: 200px;display: inline"/>

    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
<sys:message content="${message}"/>
<table class="table table-striped table-bordered" style="width: 93%;margin: 10px auto">
    <tr>
        <%--以下th标签为列表的头--%>
        <th>刻章公司名称</th>
        <th>公司类型</th>
        <th>归属区域</th>
        <th>公司法人</th>
        <th>社会统一码</th>
        <th width="200px">操作</th>
    </tr>
    <c:forEach items="${page.list}" var="makeCompany">
        <tr>
            <td>${makeCompany.companyName}</td>
            <td>${makeCompany.compType.value}</td>
            <td>${makeCompany.area.parent.name}${makeCompany.area.name}</td>
            <td>${makeCompany.legalName}</td>
            <td>${makeCompany.soleCode}</td>
            <%--按钮--%>
            <td width="200px">
                <div style="width: 200px!important;">
                    <a id="check" class="btn btn-block btn-default" href="${ctx}/dealer/company/detail?companyId=${makeCompany.id}&companyType=${makeCompany.compType}" style="display: inline">查看</a>
                    <shiro:hasPermission name="dealer:company:edit">
                        <c:if test="${makeCompany.sysOprState.key == 1}">
                            <a id="check" class="btn btn-block btn-default" href="${ctx}/dealer/systemOpration/stop?companyId=${makeCompany.id}&companyType=${makeCompany.compType}" style="display: inline">管控停用</a>
                        </c:if>
                        <c:if test="${makeCompany.sysOprState.key == 2}" >
                            <a id="check" class="btn btn-block btn-default" href="${ctx}/dealer/systemOpration/start?companyId=${makeCompany.id}&companyType=${makeCompany.compType}" style="display: inline">管控启用</a>
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
</body>
</html>
