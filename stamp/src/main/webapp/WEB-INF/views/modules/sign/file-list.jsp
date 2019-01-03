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
    <title>文件列表</title>
    <link href="${ctxStatic}/css/style.css?id=2" rel="stylesheet" />
    <link href="${ctxStatic}/css/page.css?id=2" rel="stylesheet" />
    <script src="${ctxStatic}/js/jquery.js?id=2" type="text/javascript"></script>
    <script src="${ctxStatic}/js/common.js?id=2" type="text/javascript"></script>
    <script src="${ctxStatic}/js/page.js"></script>
    <script>
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
//                $(this).attr('href', add);
            });
        });
    </script>
</head>
<body>

<div class="header">
    <h2 style="color: white">test文档</h2>
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
    <form action="${ctx}/a/document/info/index" id="typeForm" class="conditions clearfix">
        <select name="status" id="status">
            <option value="" >全部文件</option>
            <option value="0" <c:if test="${document.status == '0'}">selected</c:if>>未签署</option>
            <option value="1" <c:if test="${document.status == '1'}">selected</c:if>>已签署</option>
        </select>
        <div class="clearfix" style="float: right">
            <input name="fileName" type="text" class="search" value="${document.fileName}" placeholder="输入文件名">
            <button type="submit" class="btn-search">搜索</button>
        </div>
        <input id="pageNo" type="hidden" name="pageNo" value=""/>
    </form>

    <div class="list" style="margin-top: 15px;">
        <div id="unfinished">
            <ul>
                <c:forEach items="${page.list}" var="item">
                    <c:if test="${item.status == 0}">
                        <li class="clearfix">
                            <a class="clearfix" href="${ctx}/a/document/info/orginal/get?id=${item.id}">
                                <img class="ico" src="${ctxStatic}/img/pdf.png">
                                <span class="filename">${item.fileName}</span>
                                <span class="remark">${item.remarks}</span>
                                <span class="type-undone">未签署</span>
                                <span class="date"><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd"/></span>
                            </a>
                            <span><a href="${ctx}/a/download/document/org?id=${item.id}">文件下载</a></span>
                            <button onclick="location.href='${ctx}/a/singal/ready/singatrue?id=${item.id}'" class="btn-choose" style="float:right;">签章</button>
                        </li>
                    </c:if>
                    <c:if test="${item.status == 1}">
                        <li class="clearfix">
                            <a class="clearfix" href="###">
                                <img class="ico" src="${ctxStatic}/img/pdf.png">
                                <span class="filename">${item.fileName}</span>
                                <span class="remark">${item.remarks}</span>
                                <span class="type-done">已签署</span>
                                <span class="date"><fmt:formatDate value="${item.createDate}" pattern="yyyy-MM-dd"/></span>
                            </a>
                            <button onclick="location.href='${ctx}/a/check/index?id=${item.id}'" class="btn-choose" style="float:right;margin-top: 30px">验章</button>
                            <button onclick="location.href='${ctx}/a/document/info/singture/get?id=${item.id}'" class="btn-choose" style="float:right;">查看</button>
                        </li>
                    </c:if>
                </c:forEach>
            </ul>
        </div>
    </div>
    <div style="text-align: center;margin:20px auto;">
        <div id="pager" class="pager clearfix"></div>
    </div>
</div>
<div class="footer">
    <ul class="clearfix">
        <li class="active">
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