<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/26
  Time: 15:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh">
    <meta charset="UTF-8">
    <title></title>
    <style>
        * {
            margin: 0;
            padding: 0;
        }

        .site_under {
            margin: 0 auto;
            text-align: center;
        }
        button{
            color: #fff;
            background-color: #5cb85c;
            border-color: #4cae4c;
            display: inline-block;
            padding: 6px 12px;
            margin-bottom: 0;
            font-size: 14px;
            font-weight: 400;
            line-height: 1.42857143;
            text-align: center;
            white-space: nowrap;
            vertical-align: middle;
            -ms-touch-action: manipulation;
            touch-action: manipulation;
            cursor: pointer;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            user-select: none;
            background-image: none;
            border: 1px solid transparent;
            border-radius: 4px;
        }
        button:hover{
            color: #fff;
            background-color: #449d44;
            border-color: #398439;
        }
    </style>
</head>
<body>
<div class="site_under">
    <img width="475" height="500" src="${ctxHtml}/images/search.jpg" alt="无法查到此信息"><br>
    <h2 style="color: #272a27">无法查到此信息</h2><br>
    <button onclick="window.history.go(-1)">返回上一页</button>
</div>
</body>
</html>