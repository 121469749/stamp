<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static/copy"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head lang="zh">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="UTF-8">
    <title>签章文件</title>
    <link href="${ctxStatic}/css/style.css?id=2" rel="stylesheet" />
    <script src="${ctxStatic}/js/jquery.js?id=2" type="text/javascript"></script>
    <script src="${ctxStatic}/js/common.js?id=2" type="text/javascript"></script>
    <script src="${ctxStatic}/js/json2.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctxStatic}/js/jquery-form.js"></script>
    <script type="text/javascript">
        $(function () {
            /*设置主体高度*/
            var h = $(window).height();
            $(".menu-btn").click(function () {
                $(".menu").slideToggle("slow");
            });
            $("#main").height(h - 144);

            <%--$("#form").ajaxForm({--%>
                <%--dataType: 'json',--%>
                <%--type:"post",--%>
                <%--url: "${ctx}/a/check/document",--%>
                <%--success: function (result) {--%>
                    <%--if(result != null){--%>
<%--//                        var sign = result.singatureMD5;--%>


                    <%--}else{--%>
                        <%--alert("验章失败");--%>
                    <%--}--%>
                <%--},--%>
                <%--error:function () {--%>
                    <%--alert("error")--%>
                <%--}--%>
            <%--});--%>
            $("#upload").click(function () {
                var doc = "${document.hexCodeSignture}";//签署后的base64编码
                var objSeal = document.getElementById("SignerCtrl");
                try{
                    var name = prompt("输入证书名称");
                    var pass = prompt("输入Ukey密码");
                    objSeal.SOF_Login(name,pass); //登录
                    alert("登录成功！");
                    //原文件路径加签章码
                    var bRet = objSeal.VerifySealFile($("#uploadFile").val(), doc);
                    //var bRet = objSeal.VerifySign($("#uploadFile").val(), doc);
                    alert("验章返回："+bRet);
                    alert("验章成功");
                    //alert("验章返回jq："+$.parseJSON(bRet));
                    //alert("验章返回js："+JSON.parse(bRet));
                    //var ob = eval('(' + bRet + ')');
                    //alert("id："+ob.Id+"制章公司");
                    //alert("验章返回evalid："+ob.picWidth);
                    //alert("验章返回evalid："+ob.createDate);
                    //document.getElementById("picType").src = bRet.picWidth;
                    //alert($.parseJSON(bRet).picWidth);
                    //document.write("印章Id:"+ob.Id);
                    console.log(bRet);
                }catch(e){
                    alert(e);
                }
//                if(ob.Id.length > 0){
//                    alert("验章成功");
//                    alert("印章属性："+ob.Id+"；印章版本："+ob.sealVersion+"；制作厂商："+ob.vendorId);
//                    $("#message").show();
//                    //$("#modal-1").show();
//                    $("#up").hide();
//                }else{
//                    alert("验章失败");
//                }
            })
        });
        window.onbeforeunload = function(){
            var objSeal = document.getElementById("SignerCtrl");
            try{
                objSeal.SOF_Logout(); //整个制章动作完成之后，需要用这个接口来退出登录，释放资源
            }catch(e){
                alert(e);
            }
        };
    </script>
</head>
<body>


<div class="header">
    <h2 style="color: white">文档验章</h2>
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
    <img id="picType" src="" alt="">
    <div class="upload" id="up">
        <h2 class="clearfix">验章文件上传</h2>
        <hr>
        <div style="display: none">
            <OBJECT CLASSID="CLSID:1F4A7405-5BAD-49CF-90B8-9B5A276154B5"
                    ID="SignerCtrl" STYLE="LEFT:0;TOP:0" width="0" height="0">
            </OBJECT>
        </div>
            <h3>原文件上传</h3>
            <div style="padding:8px 10px;text-align: left">
                <button id="btn-choose" class="btn-choose" type="button">选择文件</button>
                <span class="file-name"></span>
                <input type="file" id="uploadFile" name="file" style="display:none " onchange="filepath(this)">
            </div>
            <div style="padding-top: 20px;padding-bottom: 20px;">
                <button id="upload" class="btn-upload" type="button">验章</button>
            </div>
    </div>
    <div id="message" style="display: none">
        <div id="UkeyMsg"></div>
        <h2 style="color: white;text-align: center;margin-top: 20px">签署信息</h2>
        <div class="list">
            <h3 style="color: white;text-align: center">所盖印章</h3>
            <ul id="sealtype">
                <li class="clearfix">
                    <img class="sealImg" src="/img/${document.stamp.eleModel}">
                    <span class="sealname">${document.stamp.stampName}</span>
                </li>
            </ul>
            <div class="list" style="margin-top: 15px;">
                <h3 style="color: white;text-align: center">原文件</h3>
                <div id="unfinished">
                    <ul>
                        <li class="clearfix">
                            <a class="clearfix">
                                <img class="ico" src="${ctxStatic}/img/pdf.png">
                                <span class="filename">${document.fileName}</span>
                                <span class="remark">${document.remarks}</span>
                                <span class="date"><fmt:formatDate value="${document.createDate}" pattern="yyyy-MM-dd"/></span>
                            </a>
                            <span><a href="${ctx}/a/download/document/org?id=${document.id}">文件下载</a></span>
                            <button onclick="location.href='${ctx}/a/document/info/orginal/get?id=${document.id}'" class="btn-choose" style="float:right;">查看</button>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="list" style="margin-top: 15px;">
                <h3 style="color: white;text-align: center">已签署文件</h3>
                <div id="unfinished">
                    <ul>
                        <li class="clearfix">
                            <a class="clearfix">
                                <img class="ico" src="${ctxStatic}/img/pdf.png">
                                <span class="filename">${document.fileName}</span>
                                <span class="remark">${document.remarks}</span>
                                <span class="date"><fmt:formatDate value="${document.singalDate}" pattern="yyyy-MM-dd"/></span>
                            </a>
                            <span><a href="${ctx}/a/download/document/sinature?id=${document.id}">文件下载</a></span>
                            <button onclick="location.href='${ctx}/a/document/info/singture/get?id=${document.id}'" class="btn-choose" style="float:right;">查看</button>
                        </li>
                    </ul>
                </div>
            </div>
    </div>
</div>
<div class="footer">
    <ul class="clearfix">
        <li onclick="location.href = '${ctx}/a/document/info/index'">
            <div><span style="background: url('${ctxStatic}/img/list.png') 32px 32px"></span>文件列表</div>
        </li>
        <li>
            <div><span style="background: url('${ctxStatic}/img/file.png') 32px 32px"></span>文档签章</div>
        </li>
        <li class="active">
            <div><span style="background: url('${ctxStatic}/img/stamp.png') 32px 32px"></span>验  章</div>
        </li>
    </ul>
</div>
</body>
</html>