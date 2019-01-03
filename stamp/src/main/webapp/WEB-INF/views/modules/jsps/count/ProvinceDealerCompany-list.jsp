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
    <title>省经销商-印章数量统计</title>
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

            $("#searchForm").attr("action","${ctx}/dealer/count/province/excelExport");
            $("#searchForm").submit();
            $("#searchForm").attr("action","${ctx}/dealer/count/province/list");
        }

    </script>
</head>
<body>
<%--输入页面名称--%>
<h3 style="width: 80%;margin: 10px auto">当前经销商：${currentDealer.companyName}(${currentDealer.area.name})-印章数量统计</h3>
<form:form id="searchForm" modelAttribute="dto" action="${ctx}/dealer/count/province/list"
           method="post"
           cssStyle="text-align: center">
    <div style="width: 1200px">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <label for="area">区域选择:</label>
        <sys:treeselect id="area" name="area.id" value="${dto.area.id}" labelName="area.name"
                        labelValue="${dto.area.name}"
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
        <th>单位名称</th>
        <th>公司类型</th>
        <th>公司联系电话</th>
        <th>管辖区域</th>
        <th>印章备案数量(个)</th>
        <th>物理印章刻制数量(个)</th>
        <th>电子印章刻制数量(个)</th>
        <th>缴销印章数量(个)</th>
        <th>挂失印章数量(个)</th>
        <th>作废印章数量(个)</th>
        <%--<th width="200px">操作</th>--%>
    </tr>
    <c:forEach items="${page.list}" var="agenyCompany">
        <tr>
            <td>${agenyCompany.companyName}</td>
            <td>${agenyCompany.compType.value}</td>
            <td>${agenyCompany.compPhone}</td>
            <td>${agenyCompany.area.name}</td>
            <td>${agenyCompany.companyCountVO.stampRecordCount}</td>
            <td>${agenyCompany.companyCountVO.phyStampCount}</td>
            <td>${agenyCompany.companyCountVO.eleStampCount}</td>
            <td>${agenyCompany.companyCountVO.logoutStampCount}</td>
            <td>${agenyCompany.companyCountVO.reportStampCount}</td>
            <td>${agenyCompany.companyCountVO.stampRecordCount-agenyCompany.companyCountVO.phyStampCount-agenyCompany.companyCountVO.eleStampCount-agenyCompany.companyCountVO.logoutStampCount-agenyCompany.companyCountVO.reportStampCount}</td>
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
