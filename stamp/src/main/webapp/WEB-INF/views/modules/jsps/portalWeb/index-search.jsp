<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/20
  Time: 12:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh">
    <meta charset="UTF-8">
    <!-- ie渲染-->
    <meta http-equiv="X-UA-Compatible" content="IE=Edge，chrome=1">
    <title>中国标准电子印章服务平台-印模查询</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxHtml}/css/portalWeb_style.css?id=1"/>
    <script src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script src="${ctxHtml}/js/bootstrap.min.js"></script>
    <script src="${ctxHtml}/respond.min.js"></script>
    <script>
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
    <style>
        .pages{
            display: block;
            text-align: center;
        }
        .pages input{
            width: 55px;
            border: 0;
        }
    </style>
</head>
<body style="height: 100%">
<div style="background: url('${ctxHtml}/images/background.jpg') no-repeat;background-size: cover;height: 100%;width: 100%;position: absolute;z-index: -1"></div>
<div class="header">
    <div style="height: 95px"><img src="${ctxHtml}/images/bg.png" alt=""/></div>
    <div class="menu">
        <ul class="sNav">
            <li><a href="${ctxOther}/usecomlogin">用章单位</a></li>
            <li><a href="${ctxOther}/makecomlogin">刻章点</a></li>
            <li><a href="${ctxOther}/policelogin">公安部门</a></li>
            <li><a href="${ctxOther}/agenylogin">经销商</a></li>
            <li><a class="active">印章查询</a></li>
            <li><a href="${ctxOther}/contact">联系润成</a></li>
        </ul>
    </div>
</div>
<div class="center clearfix" style="padding-top: 2%">
    <form:form id="searchForm" action="${ctxOther}/check/page" method="post" modelAttribute="dto">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <div class="form-group" style="text-align: center">
            <label style="color: black">公司名称：</label>
            <form:input path="companyName" type="text" class="form-control searchtext" />
            <label style="color: black">印章名称：</label>
            <form:input path="stampName" type="text" class="form-control searchtext" />
            <label style="color: black">印章编码：</label>
            <form:input path="stampCode" type="text" class="form-control searchtext" />
            <label style="color: black">印模类型：</label>
            <form:select path="stampShape" cssStyle="padding: 3px 5px">
                <form:option value=""></form:option>
                <form:option value="1">物理印章</form:option>
                <form:option value="2">电子印章</form:option>
            </form:select>
            <button type="submit" class="search-btn btn-info " style="margin-bottom: 4px">搜索</button>
        </div>
    </form:form>
    <table class="table table-hover table-striped table-bordered">
        <thead>
        <tr>
            <th>印模图片</th>
            <th>印章名称</th>
            <th>印章编码</th>
            <th>印模类型</th>
            <th>公司名称</th>
            <th>法人姓名</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.list}" var="stamp">
            <tr>
                <td>
                    <c:if test="${stamp.stampShape == '1'}">
                        <img src="/img${stamp.phyModel}" width="140px" alt="">
                    </c:if>
                    <c:if test="${stamp.stampShape == '2'}">
                        <img src="/img${stamp.eleModel}" width="140px" alt="">
                    </c:if>
                </td>
                <td>${stamp.stampName}</td>
                <td>${stamp.stampCode}</td>
                <td>
                    <c:if test="${stamp.stampShape == '1'}">
                        物理印模
                    </c:if>
                    <c:if test="${stamp.stampShape == '2'}">
                        电子印模
                    </c:if>
                </td>
                <td>${stamp.useComp.companyName}</td>
                <td>${stamp.useComp.legalName}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="pages" style="margin: 0 auto">
        ${page}
    </div>
</div>
</body>
</html>
