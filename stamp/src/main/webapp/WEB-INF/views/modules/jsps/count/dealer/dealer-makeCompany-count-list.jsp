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
    <title>经销商-刻章点刻制详情统计</title>
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

            $("#searchForm").attr("action", "${ctx}/makeCompany/count/exportExcel");
            $("#searchForm").submit();
            $("#searchForm").attr("action", "${ctx}/makeCompany/count/list");
        }

    </script>
</head>
<body>
<%--输入页面名称--%>
<h3 style="width: 90%;margin: 10px auto">刻章点刻制详情统计</h3>
<form:form id="searchForm" modelAttribute="dto" action="${ctx}/dealer/makeCompany/count/list"
           method="post"
           cssStyle="text-align: center">
    <div style="width: 1300px">
        <%--页码记录--%>
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <label for="companyName">刻章点名称:</label>
        <form:input id="companyName" path="company.companyName"/>
        <label>区域选择:</label>
        <sys:treeselect id="area" name="company.area.id" value="${dto.company.area.id}" labelName="company.area.name" labelValue="${dto.company.area.name}"
                        title="区域" url="/sys/area/treeDataWithAreaId" cssClass="required"
                        allowClear="true" />
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
    </div>
</form:form>
<table class="table table-striped table-bordered" style="width: 93%;margin: 10px auto">
    <tr>
        <%--以下th标签为列表的头--%>
        <th>刻章点名称</th>
        <th>刻章点联系电话</th>
        <th>刻章点法人</th>
        <th>刻章点法人联系电话</th>
        <th>统一社会信用代码</th>
        <th>印章备案数量(个)</th>
        <th>物理印章刻制数量(个)
            <table><tr><th>总数</th><th>启用</th><th>停用</th><th>挂失</th><th>缴销</th></tr></table>
        </th>
        <th>电子印章刻制数量(个)
            <table><tr><th>总数</th><th>启用</th><th>停用</th><th>挂失</th><th>缴销</th></tr></table>
        </th>
        <th>详情</th>
        <%--<th width="200px">操作</th>--%>
    </tr>
    <c:forEach items="${page.list}" var="makeCompany">
        <tr>
            <td>${makeCompany.companyName}</td>
            <td>${makeCompany.compPhone}</td>
            <td>${makeCompany.legalName}</td>
            <td>${makeCompany.legalPhone}</td>
            <td>${makeCompany.soleCode}</td>
            <td>${makeCompany.countUseCompanyStampDTO.allCount}</td>
            <td><table><tr><th width="28px">${makeCompany.countUseCompanyStampDTO.phyStamps.count}</th>
                <th width="28px">${makeCompany.countUseCompanyStampDTO.phyStamps.useCount}</th>
                <th width="28px">${makeCompany.countUseCompanyStampDTO.phyStamps.stopCount}</th>
                <th width="28px">${makeCompany.countUseCompanyStampDTO.phyStamps.logoutCount}</th>
                <th width="28px">${makeCompany.countUseCompanyStampDTO.phyStamps.reportCount}</th></tr></table>
            </td>
            <td><table><tr><th width="28px">${makeCompany.countUseCompanyStampDTO.eleStamps.count}</th>
                <th width="28px">${makeCompany.countUseCompanyStampDTO.eleStamps.useCount}</th>
                <th width="28px">${makeCompany.countUseCompanyStampDTO.eleStamps.stopCount}</th>
                <th width="28px">${makeCompany.countUseCompanyStampDTO.eleStamps.logoutCount}</th>
                <th width="28px">${makeCompany.countUseCompanyStampDTO.eleStamps.reportCount}</th></tr></table>
            </td>
            <td>
                <a href="${ctx}/dealer/makeCompany/count/detail?id=${makeCompany.id}">印章详情</a>
            </td>
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
