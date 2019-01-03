 <%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="decorator" content="default"/>
    <title>待刻印章</title>

    <%--<link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css"/>--%>
    <%--<link rel="stylesheet" href="${ctxHtml}/css/paging.css"/>--%>
    <%--<script src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>--%>
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
    <style>
        table input {
            height: 20px;
            width: 100%;
            border: none;
            background: transparent;
        }

        .accordion-heading, .table th {
            white-space: nowrap;
            background-color: #E5E5E5;
            background-image: -moz-linear-gradient(top, #EAEAEA, #E5E5E5);
            background-image: -ms-linear-gradient(top,#EAEAEA,#E5E5E5);
            background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#EAEAEA), to(#E5E5E5));
            background-image: -webkit-linear-gradient(top,#EAEAEA,#E5E5E5);
            background-image: -o-linear-gradient(top,#EAEAEA,#E5E5E5);
            background-image: linear-gradient(top,#EAEAEA,#E5E5E5);
            background-repeat: repeat-x;
        }

    </style>
</head>
<body style="width: 80%;margin: 25px auto">
<h4>当前路径：首页 \ 待刻印章</h4><h5 style="margin-bottom: 5px;">（目前剩余可刻制的物理印章<c:if test="${currentAgencyCompany.phyCountSet != null}">
    <font color="#d2691e">${currentAgencyCompany.phyCountSet.count} </font>
</c:if>
    <c:if test="${currentAgencyCompany.phyCountSet == null}">
        <font color="#d2691e">
              0
        </font>
    </c:if>个，电子印章
    <c:if test="${currentAgencyCompany.eleCountSet != null}">
        <font color="#d2691e"> ${currentAgencyCompany.eleCountSet.count} </font>
    </c:if>
    <c:if test="${currentAgencyCompany.eleCountSet == null}">
        <font color="#d2691e"> 0 </font>
    </c:if>个）</h5>

<form:form id="searchForm" modelAttribute="stamp" action="${ctx}/stampMakePage/toMakeList" method="post" class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <label for="stampShape">印章章型:</label>
    <form:select path="stampShape" class="input-medium">
        <form:option value="">全部</form:option>
        <form:option value="1">物理印章</form:option>
        <form:option value="2">电子印章</form:option>
    </form:select>
    <label>企业名称:</label>
    <form:input path="useComp.companyName"></form:input>
    <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
</form:form>
<br>
<center>
<table class="table-bordered table" style="width: 98%;">
    <tbody>
    <tr style="font-size: large;">
        <th>印章编码</th>
        <th>企业名称</th>
        <%--<th>印章名称</th>--%>
        <th>印章类型</th>
        <th>物理/电子印章</th>
        <th>申请日期</th>
        <th>操作</th>
    </tr>
    <c:forEach items="${page.list}" var="stamp">
        <tr style="font-size: medium;">
            <%--<td><input type="text" value="${stamp.stampCode}" style="width: 9em;border: none" readonly></td>--%>
            <%--<td><input type="text" value="${stamp.useComp.companyName}" style="width: 15em;border: none" readonly></td>--%>
                <%--<td><input type="text" value="${stamp.stampName}" style="width: 100%;border: none" readonly></td>--%>
            <td>${stamp.stampCode}</td>
            <td>${stamp.useComp.companyName}</td>
            <td>${fns:getDictLabel(stamp.stampType,"stampType" ,null )}</td>
            <td>
                    ${fns:getDictLabel(stamp.stampShape,"stampShape" ,null )}
            </td>
            <td><fmt:formatDate value="${stamp.createDate}" pattern="yyyy-MM-dd"/></td>
            <td>
                <a href="${ctx}/stampMakePage/oneStampMake?id=${stamp.id}&stampShape=${stamp.stampShape}">详情</a>
            </td>
        </tr>
    </c:forEach>

    </tbody>
</table>
</center>
<div class="pagination" style="display: block;margin-left: 27%">
    ${page}
</div>
</div>
</body>
</html>