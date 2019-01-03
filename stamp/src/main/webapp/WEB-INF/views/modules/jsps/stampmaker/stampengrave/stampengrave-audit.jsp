<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>安全审计管理-制章</title>
    <meta name="decorator" content="default"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/audit/index_make/">审计</a></li>
</ul>
<form:form id="searchForm" modelAttribute="audit" action="${ctx}/audit/index_make/" method="post" class="breadcrumb form-search" cssStyle="width: 91%;margin: 10px auto;">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>审计日期：</label>
            <input name="auditDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
                   value="<fmt:formatDate value="${tAudit.auditDate}" pattern="yyyy-MM-dd"/>"
                   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed" style="width: 93%;margin: 10px auto">
    <thead>
    <tr>
        <th>操作者</th>
        <th>审计类型</th>
        <th>审计日期</th>
        <th>操作结果</th>
        <th>原因</th>
        <th>IP地址</th>
        <th>MAC地址</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="tAudit">
        <c:choose>
            <c:when test="${tAudit.auditType eq '1'}">
                <tr>
                    <td>
                        <c:if test="${tAudit.user.name ne null}">
                            ${tAudit.user.name}
                        </c:if>
                        <c:if test="${tAudit.user.name eq null}">
                            ${user.name}
                        </c:if>
                    </td>
                    <td>
                        制章
                    </td>
                    <td>
                        <fmt:formatDate value="${tAudit.auditDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td>
                        <c:if test="${tAudit.auditResult eq '0'}">
                            成功
                        </c:if>
                        <c:if test="${tAudit.auditResult eq '1'}">
                            失败
                        </c:if>
                    </td>
                    <td>
                            ${tAudit.reason}
                    </td>
                    <td>
                            ${tAudit.ip}
                    </td>
                    <td>
                            ${tAudit.mac}
                    </td>
                </tr>
            </c:when>
        </c:choose>
    </c:forEach>
    </tbody>
</table>
<div class="pagination" style="width: 93%;margin: 10px auto">${page}</div>

</body>
</html>
