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
    <title>中国标准电子印章服务平台-经销商</title>
    <link rel="stylesheet" href="${ctxHtml}/css/portalWeb_style.css?id=1"/>
    <script src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script src="${ctxHtml}/respond.min.js"></script>
    <style>
        .contact{
            float: right;
            margin-right: 55px;
            color: #6383e6;
        }
        .contact:hover{
            color: blue;
            text-decoration: underline;
        }
        .hide{
            display:none;
        }
    </style>
    <script>
        $(document).ready(function(){
            if (top != self)    {
                top.location=self.location;
            }
            <%--栏目显示对应文本--%>
            $("#for-l1").click(function(){
                $(".list ul li a").removeClass("list-active");
                $(this).addClass("list-active");
                $(".text ul li").hide();
                $("#l1").show();
            });
            $("#for-l2").click(function(){
                $(".list ul li a").removeClass("list-active");
                $(this).addClass("list-active");
                $(".text ul li").hide();
                $("#l2").show();
            });
        })
    </script>
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
            <li><a class="active" href="${ctxOther}/agenylogin">经销商</a></li>
            <li><a href="${ctxOther}/check/page">印章查询</a></li>
            <li><a href="${ctxOther}/contact">联系润成</a></li>
        </ul>
    </div>
</div>
<div class="center clearfix">
    <div style="float: right;margin-top: 6%">
        <div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}"><button data-dismiss="alert" class="close">×</button>
            <label id="loginError" class="error">${message}</label>
        </div>
        <div class="login">
            <form id="loginForm"  action="agenylogin" method="post">
                <div class="login-role">
                    <label>经销商用户登录</label>
                </div>
                <div class="login-user">
                    <label>用户名：<input type="text" name="username" value="${username}" style="width: 150px" maxlength="20"/></label><br>
                    <label>密码：<input type="password" name="password" style="width: 150px;margin-left: 15px" maxlength="20" autocomplete="off" /></label>
                    <c:if test="${isValidateCodeLogin}"><div class="validateCode">
                        <label class="input-label mid" style="margin-top: 5px" for="validateCode">验证码:
                            <sys:validateCode name="validateCode" inputCssStyle="margin-bottom:0;"/></label>
                    </div></c:if>
                    <input class="hide" type="radio" style="display: none" checked="checked" name="chosenrole" value="2"/>
                </div>
                <div class="login-btn" style="margin-left: 150px">
                    <button type="submit">密码登录</button>
                </div>
                <label for="rememberMe" style="margin-left: 100px" title="下次不需要再登录"><input type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''}/> 记住我（公共场所慎用）</label>
            </form>
        </div>
    </div>
    <div class="message">
        <div class="list">
            <ul>
                <li><a class="list-active" href="#" id="for-l1">印章最新管理条例</a></li>
                <li><a href="#" id="for-l2">刻章点列表汇总</a></li>
            </ul>
        </div>
        <div class="text" style="width: 465px">
            <ul>
                <li id="l1"><p>印章最新管理条例</p></li>
                <li id="l2" style="display: none"><p>刻章点列表汇总</p></li>
            </ul>
        </div>
    </div>
</div>
</body>
</html>
