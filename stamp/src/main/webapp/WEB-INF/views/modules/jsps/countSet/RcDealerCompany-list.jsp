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
    <title>润城科技-经销商-收费规则设定</title>
    <meta name="decorator" content="default"/>
    <style>
        table input {
            width: 100%;
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
<body>
<%--输入页面名称--%>
<h3 style="width: 80%;margin: 10px auto">润成科技-经销商-印章生产控制</h3>
<form:form id="searchForm" modelAttribute="company" action="${ctx}/dealer/countSet/rc/list" method="post"
           cssStyle="text-align: center">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <%--搜索--%>
    <label for="area">单位名称:</label>
    <form:input path="companyName"  type="text" cssClass="form-control" cssStyle="width: 200px;display: inline"/>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <label for="area">区域选择:</label>
    <sys:treeselect id="area" name="area.id" value="${company.area.id}" labelName="area.name"
                    labelValue="${company.area.name}"
                    title="区域" url="/sys/area/custom/onlyProvince" cssClass="required"/>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
<sys:message content="${message}"/>
<table class="table table-striped table-bordered" style="width: 93%;margin: 10px auto">
    <tr>
        <%--以下th标签为列表的头--%>
        <th>单位名称</th>
        <th>公司类型</th>
        <th>公司地址</th>
        <th>管辖区域</th>
        <th>公司法人</th>
        <th>法人联系电话</th>
        <th>社会统一码</th>

        <th>物理印章可分配数量</th>
        <th>电子印章可分配数量</th>

        <th width="200px">操作</th>
    </tr>
    <c:forEach items="${page.list}" var="agency">
        <tr>
            <td>${agency.companyName}</td>
            <td>${agency.compType.value}</td>
            <td>${agency.compAddress}</td>
            <td>${agency.area.name}</td>
            <td>${agency.legalName}</td>
            <td>${agency.legalPhone}</td>
            <td>${agency.soleCode}</td>
            <td>
                <c:if test="${not empty agency.phyCountSet.id }">
                        ${agency.phyCountSet.count}
                </c:if>
                <c:if test="${ empty agency.phyCountSet.id }">
                    还未设置,请前去设置
                </c:if>
            </td>
            <td>
                <c:if test="${not empty agency.eleCountSet.id }">
                    ${agency.eleCountSet.count}
                </c:if>
                <c:if test="${ empty agency.eleCountSet.id }">
                    还未设置,请前去设置
                </c:if>
            </td>
                <%--按钮--%>
            <td width="200px">
                <div style="width: 200px!important;">
                    <a href="${ctx}/dealer/countSet/rc/form?companyId=${agency.id}">印章生产数量设定</a>&nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="${ctx}/countsetlog/tCountSetLog/list1?companyId=${agency.id}">查看记录</a>
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
