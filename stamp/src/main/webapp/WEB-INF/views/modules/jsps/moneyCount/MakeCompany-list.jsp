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
    <title>刻章点收费统计</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
        <%--导出为excel事件--%>
        function exportExcel() {

            $("#searchForm").attr("action","${ctx}/makeCompany/moneyCount/exportMoneyCount");
            $("#searchForm").submit();
            $("#searchForm").attr("action","${ctx}/makeCompany/moneyCount/list");
        }

    </script>
</head>
<body>
<%--输入页面名称--%>
<h3 style="width: 80%;margin: 10px auto">当前刻章点-${currentMakeCompany.companyName}-刻章收费统计</h3>
<form:form id="searchForm" modelAttribute="makeCompanyMoneyCountDTO" action="${ctx}/makeCompany/moneyCount/list" method="post"
           cssStyle="text-align: center">
    <div style="width: 1200px">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <label for="companyName">用章公司名称:</label>
    <form:input id="companyName" path="company.companyName" />
    <label for="startDate">统计开始时间:</label>
    <input id="startDate" name="startDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
           value="<fmt:formatDate value="${makeCompanyMoneyCountDTO.startDate}" pattern="yyyy-MM-dd"/>"
           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
    <label for="startDate">统计结束时间:</label>
    <input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
           value="<fmt:formatDate value="${makeCompanyMoneyCountDTO.endDate}" pattern="yyyy-MM-dd"/>"
           onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/><%--搜索--%>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
        <input id="btnSubmit" class="btn btn-primary" type="button" onclick="exportExcel()" value="导出"/>
    </div>
</form:form>
<table class="table table-striped table-bordered" style="width: 93%;margin: 10px 20px">
    <tr>
        <%--以下th标签为列表的头--%>
        <th>用章公司名称</th>
        <th>公司类型</th>
        <th>公司联系电话</th>
        <th>公司地址</th>
        <th>管辖区域</th>
        <th>公司法人</th>
        <th>法人联系电话</th>
        <th>社会统一码</th>
        <th style="max-width: 80px!important;white-space: inherit">物理印章数量(个)</th>
        <th style="max-width: 80px!important;white-space: inherit">物理印章收费(元)</th>
        <th style="max-width: 80px!important;white-space: inherit">电子印章数量(个)</th>
        <th style="max-width: 80px!important;white-space: inherit">电子印章收费(元)</th>
        <%--<th style="width: 80px">操作</th>--%>
    </tr>
    <c:forEach items="${page.list}" var="useCompany">
        <tr>
            <td>${useCompany.companyName}</td>
            <td>${useCompany.compType.value}</td>
            <td>${useCompany.compPhone}</td>
            <td>
                <div style="width:150px!important;">${useCompany.compAddress}</div>
            </td>
            <td>${useCompany.area.name}</td>
            <td>${useCompany.legalName}</td>
            <td>${useCompany.legalPhone}</td>
            <td>${useCompany.soleCode}</td>
            <td>${useCompany.companyMoneyCountVO.phyStampCount}</td>
            <td><fmt:formatNumber value="${useCompany.companyMoneyCountVO.phyStampCountMoney*0.01}" type="currency" pattern="#0.00"/></td>
            <td>${useCompany.companyMoneyCountVO.eleStampCount}</td>
            <td><fmt:formatNumber value="${useCompany.companyMoneyCountVO.eleStampCountMoney*0.01}" type="currency" pattern="#0.00"/></td>
            <%--<td width="80px">--%>
                <%--<a href="#">详情</a>--%>
            <%--</td>--%>
        </tr>
    </c:forEach>

</table>
<div class="pagination" style="margin-left: 20%">
    ${page}
</div>
</body>
</html>
