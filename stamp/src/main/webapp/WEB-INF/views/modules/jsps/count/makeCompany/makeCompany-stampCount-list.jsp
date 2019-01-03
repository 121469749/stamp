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
    <title>刻章点-企业信息统计</title>
    <meta name="decorator" content="default"/>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxHtml}/css/paging.css?id=${fns:getConfig('css.version')}">
    <style>
        table input {
            width: 100%;
            border: none;
            background: transparent;
        }
        input{
            height: 30px!important;
            margin-bottom: 0!important;
        }
        .search-btn-div{
            width:100%;
            text-align: center;
        }
    </style>
    <script src="${ctxHtml}/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {

        });
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }


    </script>
</head>
<body>
<%--输入页面名称--%>
<h3 style="width: 80%;margin-left: 40px;">
     当前刻章点：${currentMakeCompany.companyName}（${currentMakeCompany.area.name}）-企业信息统计
</h3>
<form:form id="searchForm" modelAttribute="dto" action="${ctx}/makeCompany/count/list"  method="post"  cssStyle="width: 93%;margin: 10px auto;" class="breadcrumb form-search">

    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

    <table class="table table-bordered">
        <tr>
            <td class="col-md-1 column">
                <label>用章公司名称：</label>
            </td>
            <td class="col-md-2 column">
                <form:input id="companyName" path="company.companyName" cssStyle="margin-bottom: 0; width:255px;"/>
            </td>
            <td class="col-md-1 column">
                <label>企业法人</label>
            </td>
            <td class="col-md-2 column">
                <form:input id="legalName" path="company.legalName" cssStyle="margin-bottom: 0;width:255px;"/>
            </td>
            <td class="col-md-1 column">
                <label>企业法人联系电话 </label>
            </td>
            <td class="col-md-2 column">
                <form:input id="legalPhone" path="company.legalPhone" cssStyle="margin-bottom: 0;width:255px;"/>
            </td>
        </tr>
    </table>
    <div class="search-btn-div">
        <button type="submit" class="btn btn-info" style="background-color:#5bc0de!important; border-color:#46b8da!important;color:#fff;">搜索</button>
    </div>
</form:form>
<table class="table table-striped table-bordered" style="width: 93%;margin: 10px auto">
    <tr style="font-size: medium;">
        <%--以下th标签为列表的头--%>
        <th>企业名称</th>
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
            <td>${useCompany.legalName}</td>
            <td>${useCompany.legalPhone}</td>
            <td>${useCompany.soleCode}</td>
            <td>${useCompany.countUseCompanyStampDTO.allCount}</td>
            <td><table class="table table-striped table-bordered" style="margin: 0px auto"><tr><th width="28px">${useCompany.countUseCompanyStampDTO.phyStamps.count}</th>
                <th width="28px">${useCompany.countUseCompanyStampDTO.phyStamps.useCount}</th>
                <th width="28px">${useCompany.countUseCompanyStampDTO.phyStamps.stopCount}</th>
                <th width="28px">${useCompany.countUseCompanyStampDTO.phyStamps.logoutCount}</th>
                <th width="28px">${useCompany.countUseCompanyStampDTO.phyStamps.reportCount}</th></tr></table>
            </td>
            <td><table class="table table-striped table-bordered " style="margin: 0px auto"><tr><th width="28px">${useCompany.countUseCompanyStampDTO.eleStamps.count}</th>
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
