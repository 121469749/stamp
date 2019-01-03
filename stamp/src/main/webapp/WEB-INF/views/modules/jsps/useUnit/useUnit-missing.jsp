<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>用章单位-挂失</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctxHtml}/css/useUnit_style.css"/>
    <link rel="stylesheet" href="${ctxHtml}/css/paging.css"/>
    <script language="JavaScript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="${ctxHtml}/js/jquery-form.js"></script>
    <style>
        table input{
            height: 20px;
            width: 100%;
            border: none;
            background: transparent;
        }
    </style>
    <script type="text/javascript">
        function page(n,s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body style="padding-left: 3%;padding-top: 3%">
<h3>用章单位-挂失</h3>

<div>
    <div class="clearfix">
        <ul class="clearfix">
            <li><a href="${ctx}/useCompanyStampPage/showUsing?stampShape=${stamp.stampShape}">使用中</a></li>
            <li><a href="${ctx}/useCompanyStampPage/showMaking?stampShape=${stamp.stampShape}">制作中</a></li>
            <li><a href="${ctx}/useCompanyStampPage/showMade?stampShape=${stamp.stampShape}">待交付</a></li>

            <c:choose>
                <c:when test="${stamp.stampShape == '2'}">
                    <li><a href="${ctx}/useCompanyStampPage/showStop?stampShape=${stamp.stampShape}">停用</a></li>
                </c:when>
            </c:choose>

            <li><a href="${ctx}/useCompanyStampPage/showDestroy?stampShape=${stamp.stampShape}">缴销</a></li>
            <li><a class="active" href="${ctx}/useCompanyStampPage/showMissing?stampShape=${stamp.stampShape}">挂失</a></li>
            <shiro:lacksPermission name="useCompany:edit"><li><a href="${ctx}/useCompanyStampPage/showUseful">可使用的电子印章</a></li></shiro:lacksPermission>
        </ul>
    </div>
    <div id="using">
        <form:form id="searchForm" modelAttribute="stamp" action="${ctx}/useCompanyStampPage/showMissing?stampShape=${stamp.stampShape}" method="post">
            <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}">
            <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}">
            <table>
                <tr>
                    <td>
                        <label for="name">印章名称：</label>
                        <form:input path="stampName" class="form-control" cssStyle="width: 200px;display: inline" id="name" htmlEscape="false"/>
                    </td>
                    <td>&nbsp;</td>
                    <%--<td>--%>
                        <%--<label for="stampShape">印章章型:</label>--%>
                        <%--<form:select path="stampShape" id="stampShape" class="form-control" cssStyle="width: 120px;display: inline">--%>
                            <%--<form:options items="${fns:getDictList('stampShape')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
                        <%--</form:select>--%>
                    <%--</td>--%>
                    <%--<td>&nbsp;</td>--%>
                    <td>
                        <button type="submit" class="btn btn-default" >搜索</button>
                    </td>
                </tr>
            </table>



        </form:form>
        <br>
        <table class="table-bordered table" style="width: 80%">
            <tbody>
            <tr>
                <th>印章名称</th>
                <th>印章类型</th>
                <th>状态</th>

                <c:choose>
                    <c:when test="${stamp.stampShape == '1'}">
                        <th>使用日志</th>
                    </c:when>
                </c:choose>

                <th>详情</th>
            </tr>
            <c:forEach items="${page.list}" var="stamp">
                <tr>
                    <td><input type="text" value="${stamp.stampName}" readonly></td>

                    <c:choose>
                        <c:when test="${stamp.stampShape == '1'}">
                            <td><input type="text" value="物理印章" readonly></td>
                        </c:when>
                        <c:when test="${stamp.stampShape == '2'}">
                            <td><input type="text" value="电子印章" readonly></td>
                        </c:when>
                        <c:otherwise>
                            <td><input type="text" value="" readonly></td>
                        </c:otherwise>
                    </c:choose>

                    <td><input type="text" value="已挂失" readonly></td>

                    <c:choose>
                        <c:when test="${stamp.stampShape == '1'}">
                            <td><a href="${ctx}/useCompanyLogPage/showStampLog?stamp.id=${stamp.id}&stamp.stampShape=${stamp.stampShape}" class="btn btn-xs btn-default">查看</a></td>
                        </c:when>
                    </c:choose>

                    <td><a href="${ctx}/useCompanyStampPage/showStamp?stampId=${stamp.id}&stampShape=${stamp.stampShape}" class="btn btn-xs btn-default">查看</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="pagination" style="display: block">
            ${page}
        </div>
    </div>
</div>
</body>
</html>