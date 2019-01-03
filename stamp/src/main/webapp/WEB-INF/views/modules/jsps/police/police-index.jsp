<%--
  Created by IntelliJ IDEA.
  User: 77426
  Date: 2017/5/20
  Time: 15:28
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
    <title>公安部门-待审核列表</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxHtml}/css/paging.css?id=${fns:getConfig('css.version')}">
    <script language="JavaScript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script src="${ctxHtml}/js/bootstrap.min.js"></script>
    <style>
        table input{
            width: 100%;
            height: 20px;
            border: none;
            background: transparent;
        }
    </style>
    <script type="text/javascript">
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body style="width: 80%;margin: 10px auto">
<h3 >公安部门-待审核列表</h3>
<form:form id="searchForm" action="${ctx}/policeMakeComPage/showCheckingCompany" method="post" class="breadcrumb form-search" modelAttribute="licence">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

    <label>许可证类型：</label>
    <form:select cssClass="form-control" path="workType" style="width:120px;display:inline">
     
       <c:forEach items="${workTypes}" var="f">
           <option value="${f}" label="${f.value}"></option>
       </c:forEach>

    </form:select>
    <label>公司名称:</label>
    <form:input path="compName"></form:input>
    <input id="btnSubmit" class="btn  btn-default btn-sm" type="submit" value="搜索" style="margin-bottom: 2px"/>
</form:form>


<table class="table table-striped table-bordered" width="100%">
    <tr>
        <th width="13%">单位名称</th>
        <th width="13%">单位地址</th>
        <th width="13%">法人</th>
        <th width="13%">法人联系电话</th>
        <!-- <th>项目名称</th> -->
        <th width="13%">类型</th>
        <th width="13%">状态</th>
        <th width="9%">操作</th>
    </tr>
    <c:forEach items="${page.list}" var="license">

        <tr>
            <td><input type="text" value="${license.makeComp.companyName}" readonly></td>
            <td><input type="text" value="${license.makeComp.compAddress}" readonly></td>
            <td>${license.makeComp.legalName}</td>
            <td>${license.makeComp.legalPhone}</td>
            <td>${license.workType.getValue()}</td>
            <td>${license.checkState.value}</td>
            <td><a class="btn btn-block btn-xs btn-default" href="${ctx}/policeLicensePage/checkLicense?id=${license.id}&workType=${license.workType}">查看</a></td>
        </tr>

    </c:forEach>

</table>
<div class="pagination">${page}</div>
</body>
</html>
