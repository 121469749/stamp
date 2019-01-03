<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static/copy"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head lang="zh">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="UTF-8">
    <title>审计</title>
    <link href="${ctxStatic}/css/style.css?id=2" rel="stylesheet" />
    <link href="${ctxStatic}/css/page.css?id=2" rel="stylesheet" />
    <script src="${ctxStatic}/js/jquery.js?id=2" type="text/javascript"></script>
    <script src="${ctxStatic}/js/common.js?id=2" type="text/javascript"></script>
    <script src="${ctxStatic}/js/page.js"></script>
    <style>
        tr{
            padding: 8px;
        }
        th,td{
            padding: 5px;
            border: 1px solid white;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            var pageNo = "${page.pageNo}";
            var count = "${page.count}";
            var pagedata = "${page.pageSize}";
            $("#pager").zPager({
                current: pageNo,
                totalData: count,
                pageData: pagedata
            });
            $("#pager a").click(function(){
                pageNo = $(this).attr("page-id");
                $("#pageNo").attr("value",pageNo);
                $("#typeForm").submit();
            });
        });
    </script>
</head>
<body>

<div class="header">
    <h2 style="color: white">审计</h2>
    <span class="menu-btn"></span>
    <div class="menu">
        <ul>
            <li><a href="${ctx}/a/document/info/uploadPage">上传文件</a></li>
            <li><a href="${ctx}/a/audit/index">审计</a></li>
            <li><a href="${ctx}/a/logout">退出</a></li>
        </ul>
    </div>
</div>
<div class="file" id="main">
    <form action="${ctx}/a/audit/index" id="typeForm" class="conditions clearfix">
        <select name="operation" id="operation">
            <option value="" >全部</option>
            <option value="1" <c:if test="${audit.operation == '1'}">selected</c:if>>验章</option>
            <option value="2" <c:if test="${audit.operation == '2'}">selected</c:if>>签章</option>
        </select>
            <button type="submit" class="btn-search" style="margin-left: 20px">搜索</button>
        <input id="pageNo" type="hidden" name="pageNo" value=""/>
    </form>
    <div class="list">
        <table style="border: 1px solid #ddd;width: 100%;max-width: 100%;color: white;" cellspacing="0">
            <tr>
                <th>印章</th>
                <th>审计时间</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${page.list}" var="item">
            <tr>
                <td>${item.seal.sealName}</td>
                <td><fmt:formatDate value="${item.date}" pattern="yyyy-MM-dd"/></td>
                <td>
                    <c:if test="${item.operation == '1'}">
                        验章
                    </c:if>
                    <c:if test="${item.operation == '2'}">
                        签章
                    </c:if>
                </td>
            </tr>
            </c:forEach>
        </table>
    </div>
    <div style="text-align: center;margin:20px auto;">
        <div id="pager" class="pager clearfix"></div>
    </div>
</div>
    <div class="footer">
        <ul class="clearfix">
            <li onclick="location.href = '${ctx}/a/document/info/index'">
                <div><span style="background: url('${ctxStatic}/img/list.png') 32px 32px"></span>文件列表</div>
            </li>
            <li>
                <div><span style="background: url('${ctxStatic}/img/file.png') 32px 32px"></span>文档签章</div>
            </li>
            <li>
                <div><span style="background: url('${ctxStatic}/img/stamp.png') 32px 32px"></span>验  章</div>
            </li>
        </ul>
    </div>
</body>
</html>