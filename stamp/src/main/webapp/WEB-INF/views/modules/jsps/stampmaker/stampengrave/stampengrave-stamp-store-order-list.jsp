<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>用户管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }

        // 点击接受订单
        function accept_order(id){
            debugger
            $.ajax({
                url: "${ctx}/stamp/exchange/dataExchange/acceptOrder",
                type: 'GET',
                data: {"id":id},
                async: false,
                success:function(data){
                    alert(data);
                },
                error:function(data){
                    alert("error");
                }
            });
        }

    </script>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="#">订单列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="dataExchange"  method="post" class="breadcrumb form-search ">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
    <ul class="ul-form">

        <li><label>公司名称：</label>
            <form:input path="companyName" htmlEscape="false" maxlength="50" class="input-medium"/>
        </li>
        <li><label>统一码：</label>
            <form:input path="soleCode" htmlEscape="false" maxlength="50" class="input-medium"/>
        </li>
        <li><label>法人名字：</label>
            <form:input path="legalName" htmlEscape="false" maxlength="50" class="input-medium"/>
        </li>
            <%--<li><label>姓&nbsp;&nbsp;&nbsp;名：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/></li>--%>
        <li class="btns">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"  onclick="return page();"/>

        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th>企业名称</th>
        <th>企业统一社会信用代码</th>
        <th>公司法人</th>
        <th>印章类型</th>
        <th>印章材料</th>
        <th>刻制数量</th>
        <th>法人电话</th>
        <th>印章属性</th>
        <th>操作</th>
    </tr>
    <tbody>
    <c:forEach items="${page.list}" var="exchange">
        <tr>
            <td>${exchange.companyName}</td>
            <td>${exchange.soleCode}</td>
            <td>${exchange.legalName}</td>
            <td>${fns:getDictLabel(exchange.stampType,"stampType" ,null )}</td>
            <td>${fns:getDictLabel(exchange.stampTexture,"stampTexture" ,null )}</td>
            <td>${exchange.sealCount}</td>
            <td>${exchange.legalPhone}</td>
            <td>
                <c:if test="${exchange.stampShape == 1}">
                    物理印章
                </c:if>
                <c:if test="${exchange.stampShape == 2}">
                    电子印章
                </c:if>
            </td>
            <td> <a onclick="accept_order('${exchange.id}')" class="btn">接受订单</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>



</body>
</html>
