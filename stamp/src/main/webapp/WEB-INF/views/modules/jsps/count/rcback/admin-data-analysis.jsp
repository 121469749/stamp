<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018\11\19 0019
  Time: 15:51
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
    <title>管理员-数据分析图形</title>
    <meta name="decorator" content="default"/>
    <link href="${pageContext.request.contextPath}/static/stampHtml/dataAnalysis/css/data-analysis.css?id=11"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/stampHtml/dataAnalysis/css/font-awesome.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/stampHtml/dataAnalysis/css/main.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/static/stampHtml/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/stampHtml/js/countUp.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/static/stampHtml/js/jquery.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/stampHtml/dataAnalysis/js/echarts.min.js"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/static/stampHtml/dataAnalysis/js/rcback/china.js"></script>

    <script charset="utf-8" type="text/javascript" language="javascript"
            src="${pageContext.request.contextPath}/static/stampHtml/dataAnalysis/js/rcback/admin-data-analysis.js"></script>

    <script type="text/javascript">
        var ctx = "${ctx}";
    </script>


</head>
<body>

<div id="container" style="height: 800px;width:1200px;background:white;margin:5px 0 0;"></div>
</body>
</html>
