<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>补刻印章列表</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css"/>
    <%--<link rel="stylesheet" href="${ctxHtml}/css/zui.min.css">--%>
    <%--<link rel="stylesheet" href="${ctxHtml}/css/seal.css">--%>
    <%--<link rel="stylesheet" href="${ctxHtml}/css/font-awesome.min.css">--%>
    <%--<link rel="stylesheet" href="${ctxHtml}/css/myCss.css">--%>
    <link rel="stylesheet" href="${ctxHtml}/css/paging.css"/>
    <script src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <%--<script language="JavaScript" src="${ctxHtml}/js/seal.js"></script>--%>
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
    <style>
        table input{
            height: 20px;
            width: 100%;
            border: none;
            background: transparent;
        }
    </style>
</head>
<body style="width: 80%;margin: 25px auto">
<h4>补刻印章</h4>
<form id="searchForm" action="${ctx}/stampMakePage/toRemakeList" method="post">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <label for="companyName" >印章公司名称:</label>
    <input id="companyName" name="useCompanyName" class="form-control" style="width: 200px;height: 30px;display: inline" type="text" value="${useCompanyName}">
    <label for="stampShape">印章章型:</label>
    <select name="stampShape" class="form-control" style="width: 120px;display: inline">
        <option value="1">物理印章</option>
        <option value="2">电子印章</option>
    </select>
    <input id="btnSubmit" class="btn btn-primary"  type="submit" value="查询" style="margin-bottom: 5px"/>
</form>
<br>
<table class="table-bordered table" >
    <tbody>
    <tr class="table-tr-title">

        <th>印章名称</th>
        <th>印章类型</th>
        <th>印章所属单位名</th>
        <th>使用状态</th>
        <th>操作</th>

    </tr>
    <c:forEach items="${page.list}" var="stamp">
        <tr>

            <td><input type="text" value="${stamp.stampName}" style="width: 100%;border: none" readonly></td>
            <td>
                ${fns:getDictLabel(stamp.stampShape,"stampShape" ,null )}
            </td>
            <td><input type="text" value="${stamp.useComp.companyName}" style="width: 100%;border: none" readonly></td>
            <td>${stamp.useState.getValue()}</td>
            <td>
                <a href="${ctx}/stampMakePage/toRemake?id=${stamp.id}&stampShape=${stamp.stampShape}">补刻印章</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination" style="display: block">${page}</div>
</div>
</body>
</html>