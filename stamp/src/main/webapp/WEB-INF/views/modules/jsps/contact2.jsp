<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/7
  Time: 12:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="renderer" content="webkit">
    <title>联系我们</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css">
    <script language="JavaScript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script src="${ctxHtml}/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${ctxHtml}/css/portalWeb_style.css?id=1"/>
    <script src="${ctxHtml}/respond.min.js"></script>
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
            <li><a href="${ctxOther}/check/page">印章查询</a></li>
            <li><a class="active" href="${ctxOther}/contact">联系润成</a></li>
        </ul>
    </div>
</div>
<table class="table table-striped table-bordered" style="width: 1036px;margin: 10px auto">
    <thead>
    <tr>
        <th><img src="${ctxHtml}/images/qq.png" alt=""/>QQ在线联系</th>
        <th><img src="${ctxHtml}/images/wechar.png" alt=""/>&nbsp;微信联系</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td><a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=${fns:getConfig("connect.qq")}&site=qq&menu=yes"><img border="0" src="http://wpa.qq.com/pa?p=2:${fns:getConfig("connect.qq")}:53" alt="点击这里给我发消息" title="点击这里给我发消息"/></a></td>
        <td><img src="${ctxStatic}/contact/润成科技二维码.jpg" alt=""/></td>
    </tr>
    </tbody>
</table>
</body>
</html>
