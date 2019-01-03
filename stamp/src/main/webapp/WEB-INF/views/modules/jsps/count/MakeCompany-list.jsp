<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/22
  Time: 10:32
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
    <title>刻章点简单统计</title>
    <meta name="decorator" content="default"/>
    <style>
        table input {
            width: 100%;
            border: none;
            background: transparent;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function () {

        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
        function exportExcel() {

            $("#searchForm").attr("action","${ctx}/makeCompany/count/exportExcel");
            $("#searchForm").submit();
            $("#searchForm").attr("action","${ctx}/makeCompany/count/list");
        }

    </script>
</head>
<body>
<%--输入页面名称--%>
<h3 style="width: 80%;margin: 10px auto">
    当前刻章点-${currentMakeCompany.companyName}-${currentMakeCompany.area.name}-简单统计</h3>
<form:form id="searchForm" modelAttribute="dto" action="${ctx}/makeCompany/count/list"
           method="post"
           cssStyle="text-align: center">
    <div style="width: 1300px">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <label for="companyName">用章公司名称:</label>
        <form:input id="companyName" path="company.companyName"/>
        <label for="area">区域选择:</label>
        <sys:treeselect id="area" name="company.area.id" value="${dto.company.area.id}"
                        labelName="company.area.name"
                        labelValue="${dto.company.area.name}"
                        title="区域" url="/sys/area/custom/currentCompanyAreaALayer"/>
        <label for="startDate">统计开始时间:</label>
        <input id="startDate" name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
               value="<fmt:formatDate value="${dto.startDate}" pattern="yyyy-MM-dd"/>"
               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
        <label for="startDate">统计结束时间:</label>
        <input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
               value="<fmt:formatDate value="${dto.endDate}" pattern="yyyy-MM-dd"/>"
               onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/><%--搜索--%>
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
        <input id="btnSubmit" class="btn btn-primary" type="button" onclick="exportExcel()" value="导出"/>
    </div>
</form:form>
<table class="table table-striped table-bordered" style="width: 93%;margin: 10px auto">
    <tr>
        <%--以下th标签为列表的头--%>
        <th>用章公司名称</th>
        <th>公司类型</th>
        <th>刻章点联系电话</th>
        <th>刻章点区域</th>
        <th>刻章点法人</th>
        <th>刻章点法人联系电话</th>
        <th>社会统一码</th>
        <th>印章备案数量(个)</th>
        <th>物理印章刻制数量(个)</th>
        <th>电子印章刻制数量(个)</th>
        <th>缴销印章数量(个)</th>
        <th>挂失印章数量(个)</th>
        <%--<th width="200px">操作</th>--%>
    </tr>
    <c:forEach items="${page.list}" var="useCompany">
        <tr>
            <td>${useCompany.companyName}</td>
            <td>${useCompany.compType.value}</td>
            <td>${useCompany.compPhone}</td>
            <td>${useCompany.area.name}</td>
            <td>${useCompany.legalName}</td>
            <td>${useCompany.legalPhone}</td>
            <td>${useCompany.soleCode}</td>
            <td>${useCompany.companyCountVO.stampRecordCount}</td>
            <td>${useCompany.companyCountVO.phyStampCount}</td>
            <td>${useCompany.companyCountVO.eleStampCount}</td>
            <td>${useCompany.companyCountVO.logoutStampCount}</td>
            <td>${useCompany.companyCountVO.reportStampCount}</td>
                <%--<td width="200px">--%>
                <%--<div style="width: 200px!important;">--%>
                <%--<a href="#">详情</a>--%>
                <%--</div>--%>
                <%--</td>--%>
        </tr>
    </c:forEach>

</table>
<div class="pagination" style="margin-left: 20%">
    ${page}
</div>
</body>
</html>
