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
</head>
<body style="width: 90%;margin: 10px auto">

<h3>在线帮助</h3><br>
<%--<table class="table table-striped table-bordered" style="width: 80%;margin: 10px auto">--%>
<table class="table table-striped table-bordered" style="width: 40%">
    <thead>
    <tr>
        <th><img src="${ctxHtml}/images/qq.png" alt=""/>QQ在线联系</th>
        <th><img src="${ctxHtml}/images/wechar.png" alt=""/>&nbsp;微信联系</th>
    </tr>
    </thead>
    <tbody>
    <tr style="height:180px">
        <td style="line-height: 180px;vertical-align: middle;">
            <a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=${fns:getConfig("connect.qq")}&site=qq&menu=yes">
            <img border="0" src="http://wpa.qq.com/pa?p=2:${fns:getConfig("connect.qq")}:53" height="170px" alt="点击这里联系我" title="点击这里联系我"/>
            </a>
        </td>
        <td style="line-height: 180px;vertical-align: middle;">
            <img src="${ctxStatic}/contact/润成科技二维码.jpg" height="180px" width="180px" alt=""/>
        </td>
    </tr>
    </tbody>
</table>
<hr>
<h3>联系我们</h3><br>
<p>
<ul>联系电话： 0756-3322881</ul>
<ul>联系地址： 珠海香洲吉大水湾路368号南油大酒店左侧润成科技公司</ul>
<ul>邮　　编： 519000</ul>
</p>
</body>
</html>
