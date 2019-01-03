<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/26
  Time: 15:28
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
        html, body {
            height: 100%;
        }
        body {
            font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
            font-size: 13px;
            color: #676a6c;
            overflow-x: hidden;
        }
        .middle-box {
            max-width: 400px;
            z-index: 100;
            margin: 0 auto;
            padding-top: 40px;
        }
        .fadeInDown {
            -webkit-animation-name: fadeInDown;
            animation-name: fadeInDown;
        }
        .animated {
            -webkit-animation-duration: 1s;
            animation-duration: 1s;
            -webkit-animation-fill-mode: both;
            animation-fill-mode: both;
            z-index: 100;
        }
        .text-center {
            text-align: center;
        }
        .gray-bg {
            background-color: #f3f3f4;
        }
        .middle-box h1 {
            font-size: 170px;
        }
        h1, h2, h3, h4, h5, h6 {
            font-weight: 100;
        }
        .h1, .h2, .h3, h1, h2, h3 {
            margin-top: 20px;
            margin-bottom: 10px;
        }
        .h1, .h2, .h3, .h4, .h5, .h6, h1, h2, h3, h4, h5, h6 {
            font-family: inherit;
            line-height: 1.1;
            color: inherit;
        }
        .font-bold {
            font-size: 16px;
            margin-top: 5px;
            font-weight: 600;
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
<body class="gray-bg">


<div class="middle-box text-center animated fadeInDown">
    <h1>500</h1>
    <h3 class="font-bold">出错了!</h3>

    <div class="error-desc">
        ${errorMessage}
        <div class="form-group">
            <button style="margin-top: 20px" onclick="history.go(-1)">返回上一页</button>
        </div>
    </div>
</div>

</body>
</html>
