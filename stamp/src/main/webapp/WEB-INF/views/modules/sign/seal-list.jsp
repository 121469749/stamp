<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static/copy"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head lang="zh">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="UTF-8">
    <title>印章列表</title>
    <link href="${ctxStatic}/css/style.css?id=2" rel="stylesheet" />
    <script src="${ctxStatic}/js/jquery.js?id=2" type="text/javascript"></script>
    <script src="${ctxStatic}/js/common.js?id=2" type="text/javascript"></script>
    <script type="text/javascript" src="${ctxStatic}/js/jquery-form.js"></script>
    <script type="text/javascript">
        var name;
        window.onbeforeunload = function(){
            var objSeal = document.getElementById("SignerCtrl");
            try{
                objSeal.SOF_Logout(); //整个制章动作完成之后，需要用这个接口来退出登录，释放资源
            }catch(e){
                alert(e);
            }
        };
        $(function () {
            $("#checkSealForm").ajaxForm({
                dataType: 'json',
                type:"post",
                url: "${ctx}/a/check/checkExist",
                success: function (result) {
                    if(result.code == 200){
                        alert("文件验证成功");
                        $("#btn-check").attr("disabled","disabled");
                        $("#sealtype").show();
                        // 获取控件
                        var objSeal = document.getElementById("SignerCtrl");
                        //在页面加载时管理员登录
                        name = prompt("输入证书名称");
                        <%--name = "${user.loginName}";--%>
                        var pass = prompt("输入UKey密码");
                        var ary2,ary3 = "";
                        try{
                            objSeal.SOF_Login(name,pass); //登录
                            alert("证书登录成功");
                            $("#name").attr("value",name);
                            $("#password").attr("value",pass);
                            //alert("开始:::::"+objSeal.GetSealCount());
                            var seals = objSeal.GetSealList();
                            //alert("进入");
                            var ary  = seals.split(";");
                            for(var i = 0;i < ary.length-1;i++){
                                ary2 = ary[i].split(",");
                                ary3 += ary2[1] + ",";
                            }
                            //alert("seal的id"+ary3);
                            $.ajax({
                                url:"${ctx}/a/seal/list?id=" + ary3,
                                type:"POST",
                                dataType:"json",
                                success:function(result){
                                    for(var i = 0;i < result.length;i++){
                                        var path= $("#checkSealFile").val();
                                        var index = path.replace(/\\/g,',');
                                        var a = "location.href='${ctx}/a/singal/real/singatrue?userName="+ $('#name').val() +"&password="+ $('#password').val() +
                                            "&document.id=${id}&seal.id=" + result[i].id +"&realPath="+ index +"'";
                                        $("#sealtype").append("<li class='clearfix' onclick=\"" + a + "\"><img class='sealImg' src='/img" + result[i].eleModel +
                                            "'><span class='sealname'>" + result[i].stampName + "</span></li>");
                                    }
                                }
                            })
                        }catch(e){
                            // 输出出错信息
                            alert(e + "\n出错了，请刷新页面重试");
                        }
                    }else{
                        alert("请上传原文件");
                    }
                },
                error:function () {
                    alert("error")
                }
            });
            $("#checkSeal").click(function(){
                $("#checkSealForm").submit();
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

    <div style="display: none">
        <OBJECT CLASSID="CLSID:1F4A7405-5BAD-49CF-90B8-9B5A276154B5"
                ID="SignerCtrl" STYLE="LEFT:0;TOP:0" width="0" height="0">
        </OBJECT>
    </div>
    <input type="hidden" id="name" value=""/>
    <input type="hidden" id="password" value=""/>
    <div class="list">
        <div>
            <%--文件上传--%>
                <div class="upload">
                    <h2 class="clearfix">本地文件上传（上传之前选择的要签章的文件）</h2>
                    <hr>
                    <form id="checkSealForm" action="${ctx}/a/check/checkExist">
                        <div style="padding:8px 10px;text-align: left">
                            <button id="btn-check" class="btn-choose" type="button">选择文件</button>
                            <span class="file-name"></span>
                            <input type="file" id="checkSealFile" name="file" style="display: none" onchange="filepath(this)">
                        </div>
                        <input type="hidden" value="${id}" name="id"/>
                        <div style="padding-top: 20px;padding-bottom: 20px;">
                            <button id="checkSeal" class="btn-upload" type="button">确认上传</button>
                        </div>
                    </form>
                </div>
        </div>
        <h2 style="color: white">印模选择</h2>
        <ul id="sealtype" style="display: none"><!-- 动态给列表添加印膜 -->
        </ul>
    </div>
</div>
<div class="footer">
    <ul class="clearfix">
        <li onclick="location.href = '${ctx}/a/document/info/index'">
            <div><span style="background: url('${ctxStatic}/img/list.png') 32px 32px"></span>文件列表</div>
        </li>
        <li class="active" onclick="toFileList('${ctx}/a/document/info/list')">
            <div><span style="background: url('${ctxStatic}/img/file.png') 32px 32px"></span>文档签章</div>
        </li>
        <li>
            <div><span style="background: url('${ctxStatic}/img/stamp.png') 32px 32px"></span>验  章</div>
        </li>
    </ul>
</div>
</body>
</html>