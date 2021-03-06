<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/20
  Time: 12:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=8,IE=9" />
    <title>首页</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${ctxHtml}/css/index.css" />
    <link rel="stylesheet" href="${ctxHtml}/css/font-awesome.min.css">
</head>
<body>
<div class="head clearfix">
    <div class="logo">
        <h3>印章备案管理信息系统</h3>
    </div>
    <div class="title">
        <div class="title-text"><a>印章刻制</a></div>
    </div>
    <div class="message clearfix">
        <div class="num">
            <h3>欢迎：<span>端州区刻章公司</span></h3>
        </div>
        <button type="button" class="exit">退出登录</button>
    </div>
</div>
<div class="main clearfix">
    <div class="left">
        <ul>
            <li><a class="active" target="right" href="record.html"><span class="chapter"></span>备案登记</a></li>
            <li><a target="right" href="seal-nofinish.html"><span class="chapter"></span>待刻印章</a></li>
            <li><a target="right" href="seal-finish.html"><span class="chapter"></span>已刻印章</a></li>
            <li><a target="right" href="seal-delivers.html"><span class="chapter"></span>交付印章</a></li>
        </ul>
    </div>
    <div class="right">
        <iframe id="iframe1" src="record.html" name="right" frameborder="1" width="100%" height="100%"></iframe>
    </div>
</div>
<script type="text/javascript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
<script>
    $(function(){
        $(".left ul li a").click(function(){
            $(".left ul li a").removeClass("active");
            $(this).addClass("active");
        });
    });
    /*iframe里更改index页面导航栏状态*/
    function activechange(){
        $(".left ul li a").removeClass("active");
        $(".left ul li a:eq(2)").addClass("active");
    }
</script>
</body>
</html>
