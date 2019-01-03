<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="renderer" content="webkit">
    <title>公安机关-用章单位备案信息统计</title>
    <meta name="decorator" content="default"/>
    <script language="JavaScript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script src="${ctxHtml}/js/bootstrap.min.js"></script>
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
<div class="panel-head clearfix" style="margin: 10px">
<h3 style="width: 50%;padding-left: 2.0%;color:black;font-weight: normal;">
    公安机关-用章单位备案信息统计</h3>
<%--<span style="float: right;">--%>

<%--</span>--%>

</div>
<form:form id="searchForm" modelAttribute="dto" action="${ctx}/police/count/useCompany/list"
           method="post"
           cssStyle="width: 91%;margin: 10px auto;" class="breadcrumb form-search">
        <%--页码记录--%>
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <label for="companyName">用章公司名称:</label>
        <form:input id="companyName" path="company.companyName"/>
        <label>区域选择:</label>
        <sys:treeselect id="area" name="company.area.id" value="${dto.company.area.id}" labelName="company.area.name" labelValue="${dto.company.area.name}"
                        title="区域" url="/sys/area/police/treeDataWithAreaId" cssClass="required"
                        allowClear="true" />
        <input style="margin-left: 10px;" id="btnSubmit" class="btn btn-primary"  type="submit" value="查询"/>
        <button style="margin-left: 10px;" class="btn btn-primary" onclick="exportExcel()">导出到Excel</button>
</form:form>
<table class="table table-striped table-bordered" style="width: 93%;margin: 10px auto">
    <tr>
        <%--以下th标签为列表的头--%>
        <th>企业名称</th>
        <th>企业联系电话</th>
        <th>企业法人</th>
        <th>企业法人联系电话</th>
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
    <c:forEach items="${page.list}" var="useCompany">
        <tr>
            <td>${useCompany.companyName}</td>
            <td>${useCompany.compPhone}</td>
            <td>${useCompany.legalName}</td>
            <td>${useCompany.legalPhone}</td>
            <td>${useCompany.soleCode}</td>
            <td>${useCompany.countUseCompanyStampDTO.allCount}</td>
            <td><table class="table table-striped table-bordered" style="width: 100%;margin: 0px auto"><tr><th width="28px">${useCompany.countUseCompanyStampDTO.phyStamps.count}</th>
                <th width="28px">${useCompany.countUseCompanyStampDTO.phyStamps.useCount}</th>
                <th width="28px">${useCompany.countUseCompanyStampDTO.phyStamps.stopCount}</th>
                <th width="28px">${useCompany.countUseCompanyStampDTO.phyStamps.logoutCount}</th>
                <th width="28px">${useCompany.countUseCompanyStampDTO.phyStamps.reportCount}</th></tr></table>
            </td>
            <td><table  class="table table-striped table-bordered" style="width: 100%;margin: 0px auto"><tr><th width="28px">${useCompany.countUseCompanyStampDTO.eleStamps.count}</th>
                <th width="28px">${useCompany.countUseCompanyStampDTO.eleStamps.useCount}</th>
                <th width="28px">${useCompany.countUseCompanyStampDTO.eleStamps.stopCount}</th>
                <th width="28px">${useCompany.countUseCompanyStampDTO.eleStamps.logoutCount}</th>
                <th width="28px">${useCompany.countUseCompanyStampDTO.eleStamps.reportCount}</th></tr></table>
            </td>
            <td>
                <a href="${ctx}/makeCompany/count/detail/record?id=${useCompany.id}">备案详情</a>
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
